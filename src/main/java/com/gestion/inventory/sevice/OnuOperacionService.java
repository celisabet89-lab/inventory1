package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.OnuMovimiento;
import com.gestion.inventory.domain.dtorecust.OnuOperacionRequest;
import com.gestion.inventory.repository.OnuMovimientoRepository;
import com.gestion.inventory.repository.OnuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Operaciones por lote sobre ONUs. Todo se valida antes de cambiar nada y se guarda
 * en una sola transacción: si una ONU no cumple, no se modifica ninguna.
 */
@Service
public class OnuOperacionService {

    private static final Set<String> ESTADOS_DIAGNOSTICO = Set.of("Disponible", "Defectuoso", "Observado", "Mantenimiento");

    private final OnuRepository onuRepository;
    private final OnuMovimientoRepository movimientoRepository;

    public OnuOperacionService(OnuRepository onuRepository, OnuMovimientoRepository movimientoRepository) {
        this.onuRepository = onuRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public List<OnuMovimiento> asignar(OnuOperacionRequest req) {
        String usuario = exigirTexto(req.getUsuario(), "Selecciona el usuario al que se asigna");
        List<OnuOperacionRequest.Item> items = exigirItems(req);

        List<Onu> onus = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        for (OnuOperacionRequest.Item item : items) {
            Onu onu = buscar(item.getOnuId());
            if (!"Disponible".equalsIgnoreCase(texto(onu.getEstado()))) {
                errores.add(etiqueta(onu) + " no está disponible (estado: " + texto(onu.getEstado()) + ")");
            }
            onus.add(onu);
        }
        fallarSiHayErrores(errores);

        List<OnuMovimiento> resultado = new ArrayList<>();
        for (int i = 0; i < onus.size(); i++) {
            Onu onu = onus.get(i);
            String anterior = onu.getEstado();
            String almacenOrigen = onu.getAlmacen();

            onu.setEstado("Asignada");
            onu.setTipoPortador("Usuario");
            onu.setPortador(usuario);
            onu.setAlmacen(null);
            onuRepository.save(onu);

            resultado.add(movimientoRepository.save(
                    movimiento(req, onu, "ASIGNACION", anterior, "Asignada", usuario, almacenOrigen,
                            unir(req.getObservaciones(), items.get(i).getObservaciones()))));
        }
        return resultado;
    }

    @Transactional
    public List<OnuMovimiento> recojo(OnuOperacionRequest req) {
        String usuario = exigirTexto(req.getUsuario(), "Selecciona el usuario que recoge");
        List<OnuOperacionRequest.Item> items = exigirItems(req);

        List<Onu> onus = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        for (OnuOperacionRequest.Item item : items) {
            Onu onu = buscar(item.getOnuId());
            if (onu.getContrato() == null) {
                errores.add(etiqueta(onu) + " no tiene contrato; no corresponde un recojo");
            } else if ("Recogido".equalsIgnoreCase(texto(onu.getEstado()))) {
                errores.add(etiqueta(onu) + " ya fue recogida");
            }
            onus.add(onu);
        }
        fallarSiHayErrores(errores);

        List<OnuMovimiento> resultado = new ArrayList<>();
        for (int i = 0; i < onus.size(); i++) {
            Onu onu = onus.get(i);
            String anterior = onu.getEstado();
            Long contrato = onu.getContrato();

            onu.setEstado("Recogido");
            onu.setTipoPortador("Usuario");
            onu.setPortador(usuario);
            onu.setContrato(null);
            onuRepository.save(onu);

            resultado.add(movimientoRepository.save(
                    movimiento(req, onu, "RECOJO", anterior, "Recogido", usuario, null,
                            unir("Contrato N° " + contrato, unir(req.getObservaciones(), items.get(i).getObservaciones())))));
        }
        return resultado;
    }

    @Transactional
    public OnuMovimiento diagnosticar(OnuOperacionRequest req) {
        Onu onu = buscar(req.getOnuId());
        String estadoNuevo = exigirTexto(req.getEstadoNuevo(), "Selecciona el estado de la ONU");
        String diagnostico = exigirTexto(req.getDiagnostico(), "Escribe el diagnóstico");

        if (!ESTADOS_DIAGNOSTICO.contains(estadoNuevo)) {
            throw new IllegalArgumentException("Estado no válido para un diagnóstico: " + estadoNuevo);
        }
        String actual = texto(onu.getEstado());
        if (!(actual.equalsIgnoreCase("Mantenimiento") || actual.equalsIgnoreCase("En Reparación")
                || actual.equalsIgnoreCase("Recogido"))) {
            throw new IllegalArgumentException(etiqueta(onu) + " no está en mantenimiento (estado: " + actual + ")");
        }
        String almacen = req.getAlmacen() == null ? "" : req.getAlmacen().trim();
        if (estadoNuevo.equals("Disponible") && almacen.isEmpty()) {
            throw new IllegalArgumentException("Indica el almacén destino para dejar la ONU disponible");
        }

        String anterior = onu.getEstado();
        onu.setEstado(estadoNuevo);
        onu.setObservaciones(diagnostico.length() > 250 ? diagnostico.substring(0, 250) : diagnostico);
        if (!almacen.isEmpty()) {
            onu.setAlmacen(almacen);
            onu.setTipoPortador("Almacén");
            onu.setPortador(almacen);
        }
        onuRepository.save(onu);

        return movimientoRepository.save(
                movimiento(req, onu, "DIAGNOSTICO", anterior, estadoNuevo, null, almacen.isEmpty() ? null : almacen, diagnostico));
    }

    public List<OnuMovimiento> movimientos() {
        return movimientoRepository.findAllByOrderByFechaDesc();
    }

    public List<OnuMovimiento> movimientosDe(Long onuId) {
        return movimientoRepository.findByOnu_IdOrderByFechaDesc(onuId);
    }

    /* ── utilidades ─────────────────────────────────────────── */

    private Onu buscar(Long id) {
        if (id == null) throw new IllegalArgumentException("Falta el código de la ONU");
        return onuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la ONU con código " + id));
    }

    private List<OnuOperacionRequest.Item> exigirItems(OnuOperacionRequest req) {
        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new IllegalArgumentException("Agrega al menos una ONU al detalle");
        }
        java.util.Set<Long> vistos = new java.util.HashSet<>();
        for (OnuOperacionRequest.Item item : req.getItems()) {
            if (item.getOnuId() != null && !vistos.add(item.getOnuId())) {
                throw new IllegalArgumentException("La ONU " + item.getOnuId() + " está repetida en el detalle");
            }
        }
        return req.getItems();
    }

    private String exigirTexto(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException(mensaje);
        return valor.trim();
    }

    private void fallarSiHayErrores(List<String> errores) {
        if (!errores.isEmpty()) throw new IllegalArgumentException(String.join("; ", errores));
    }

    private String texto(String valor) {
        return valor == null ? "" : valor;
    }

    private String etiqueta(Onu onu) {
        return "ONU " + onu.getId() + " (" + texto(onu.getSerial()) + ")";
    }

    private String unir(String a, String b) {
        boolean sinA = a == null || a.trim().isEmpty();
        boolean sinB = b == null || b.trim().isEmpty();
        if (sinA && sinB) return null;
        if (sinA) return b.trim();
        if (sinB) return a.trim();
        return a.trim() + ". " + b.trim();
    }

    private OnuMovimiento movimiento(OnuOperacionRequest req, Onu onu, String tipo, String anterior, String nuevo,
                                     String usuario, String almacen, String detalle) {
        OnuMovimiento m = new OnuMovimiento();
        m.setOnu(onu);
        m.setFecha(req.getFecha() == null ? LocalDateTime.now() : req.getFecha().atTime(LocalTime.now()));
        m.setTipo(tipo);
        m.setEstadoAnterior(anterior);
        m.setEstadoNuevo(nuevo);
        m.setUsuario(usuario);
        m.setAlmacen(almacen);
        m.setDetalle(detalle);
        return m;
    }
}
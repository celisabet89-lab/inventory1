
package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.CobroRequest;
import com.gestion.inventory.repository.CobroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CobroServiceImp implements ICobroService {

    CobroRepository cobroRepository;
    IMovementsService movementsService;
    public CobroServiceImp(
            CobroRepository cobroRepository,
            IMovementsService movementsService) {
        this.cobroRepository = cobroRepository;
        this.movementsService = movementsService;
    }
    @Override
    public List<Cobro> getAll() {
        return cobroRepository.findAllByOrderByFechaDesc();
    }
    @Override
    public Cobro getById(Long id) {
        return cobroRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("No se encontró el cobro con ID: " + id));
    }
    @Override
    public List<Cobro> getByCodigoVenta(String codigoVenta) {
        return cobroRepository
                .findByCodigoVentaOrderByFechaDesc(codigoVenta);
    }
    @Override
    @Transactional
    public Cobro registrarCobro(CobroRequest request) {
        if (request == null
                || request.getCodigoVenta() == null
                || request.getCodigoVenta().trim().isEmpty()) {
            throw new RuntimeException("El código de venta es obligatorio");
        }
        if (request.getMonto() == null
                || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor que cero");
        }
        if (request.getFormaPago() == null
                || request.getFormaPago().trim().isEmpty()) {
            throw new RuntimeException("La forma de pago es obligatoria");
        }
        String codigo = request.getCodigoVenta().trim();
        List<Movements> movimientos = movementsService.getAll()
                .stream()
                .filter(m -> m.getCodigo() != null
                        && m.getCodigo().trim().equalsIgnoreCase(codigo))
                .filter(m -> m.getTipo() != null
                        && m.getTipo().trim().equalsIgnoreCase("SALIDA"))
                .filter(m -> m.getEstadoMovimiento() == null
                        || m.getEstadoMovimiento().trim().isEmpty()
                        || m.getEstadoMovimiento().equalsIgnoreCase("ACTIVO"))
                .toList();
        if (movimientos.isEmpty()) {
            throw new RuntimeException(
                    "No se encontró una venta activa con el código: " + codigo);
        }
        BigDecimal totalVenta = movimientos.stream()
                .map(Movements::getTotal)
                .filter(total -> total != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalVenta.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "La venta no tiene un total válido para cobrar");
        }
        BigDecimal totalCobrado = cobroRepository
                .findByCodigoVentaOrderByFechaDesc(codigo)
                .stream()
                .map(Cobro::getMonto)
                .filter(monto -> monto != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal saldoPendiente = totalVenta.subtract(totalCobrado);
        if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Esta venta ya está pagada");
        }
        if (request.getMonto().compareTo(saldoPendiente) > 0) {
            throw new RuntimeException(
                    "El monto supera el saldo pendiente de Bs "
                            + saldoPendiente);
        }
        Cobro cobro = new Cobro();
        cobro.setCodigoVenta(codigo);
        cobro.setMonto(request.getMonto());
        cobro.setFormaPago(request.getFormaPago().trim());
        cobro.setObservaciones(request.getObservaciones());
        Cobro cobroGuardado = cobroRepository.save(cobro);
        BigDecimal nuevoTotalCobrado = totalCobrado.add(request.getMonto());
        String estadoPago = nuevoTotalCobrado.compareTo(totalVenta) >= 0
                ? "PAGADO"
                : "PARCIAL";
        for (Movements movimiento : movimientos) {
            movimiento.setEstadoPago(estadoPago);
            movementsService.save(movimiento);
        }
        return cobroGuardado;
    }
}
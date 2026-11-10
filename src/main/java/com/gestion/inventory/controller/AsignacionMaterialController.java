package com.gestion.inventory.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.domain.AsignacionMaterial;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.repository.AsignacionMaterialRepository;
import com.gestion.inventory.repository.ProductRepository;
import com.gestion.inventory.sevice.StockSyncService;

/**
 * Materiales entregados a los técnicos.
 *  - Al asignar se descuenta del stock del producto.
 *  - Al devolver se regresa al stock.
 *  - Lo usado en una instalación queda consumido (no vuelve al stock).
 */
@RestController
@RequestMapping("/api/materiales-tecnico")
@CrossOrigin(origins = "*")
public class AsignacionMaterialController {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired AsignacionMaterialRepository repo;
    @Autowired ProductRepository productos;
    @Autowired StockSyncService stockSync;

    @GetMapping
    public List<AsignacionMaterial> getAll(@RequestParam(required = false) Long tecnicoId) {
        return tecnicoId == null ? repo.findAllByOrderByFechaDesc() : repo.findByTecnicoIdOrderByFechaDesc(tecnicoId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> asignar(@RequestBody Map<String, Object> body) {
        Long tecnicoId = numero(body.get("tecnicoId"));
        Long productoId = numero(body.get("productoId"));
        Long cant = numero(body.get("cantidad"));
        String tecnicoNombre = texto(body.get("tecnicoNombre"));
        if (tecnicoId == null || tecnicoNombre.isEmpty()) return error("Seleccione el técnico");
        if (productoId == null) return error("Seleccione el material");
        if (cant == null || cant <= 0) return error("La cantidad debe ser mayor a 0");

        Product p = productos.findById(productoId).orElse(null);
        if (p == null) return error("Material no encontrado");
        int disponible = p.getUnidad() == null ? 0 : p.getUnidad();
        if (cant > disponible)
            return error("Stock insuficiente de " + p.getArticulo() + ": hay " + disponible + " y se piden " + cant);

        p.setUnidad(disponible - cant.intValue());
        productos.save(p);
        stockSync.desdeProducto(p);

        AsignacionMaterial a = new AsignacionMaterial();
        a.setTecnicoId(tecnicoId);
        a.setTecnicoNombre(tecnicoNombre);
        a.setProducto(p);
        a.setCantidad(cant.intValue());
        a.setDestino(texto(body.get("destino")));
        a.setAsignadoPor(texto(body.get("asignadoPor")));
        a.setCargoAsignador(texto(body.get("cargoAsignador")));
        a.setEstado("ASIGNADO");
        a.setBitacora(linea("Entrega de " + cant + " a " + tecnicoNombre));
        return ResponseEntity.ok(repo.save(a));
    }

    /** El técnico gastó material en una instalación. */
    /** El técnico confirma que recibió físicamente el material (queda quién y cuándo). */
    @PutMapping("/{id}/confirmar")
    @Transactional
    public ResponseEntity<?> confirmar(@PathVariable Long id, @RequestBody(required = false) Map<String, Object> body) {
        AsignacionMaterial a = repo.findById(id).orElse(null);
        if (a == null) return error("Asignación no encontrada");
        if (a.getFechaConfirmacion() != null) return error("La recepción ya fue confirmada");
        String quien = body == null ? "" : texto(body.get("confirmadoPor"));
        if (quien.isEmpty()) quien = a.getTecnicoNombre();
        a.setFechaConfirmacion(LocalDateTime.now());
        a.setConfirmadoPor(quien);
        agregar(a, "Recepción confirmada por " + quien);
        return ResponseEntity.ok(repo.save(a));
    }

    @PutMapping("/{id}/uso")
    @Transactional
    public ResponseEntity<?> uso(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        AsignacionMaterial a = repo.findById(id).orElse(null);
        if (a == null) return error("Asignación no encontrada");
        Long cant = numero(body.get("cantidad"));
        if (cant == null || cant <= 0) return error("La cantidad debe ser mayor a 0");
        if (cant > a.getPendiente()) return error("El técnico solo tiene " + a.getPendiente() + " pendiente(s)");

        a.setCantidadUsada(a.getCantidadUsada() + cant.intValue());
        String detalle = texto(body.get("detalle"));
        agregar(a, "Usado " + cant + (detalle.isEmpty() ? "" : " · " + detalle));
        return ResponseEntity.ok(repo.save(cerrarSiCorresponde(a)));
    }

    /** El técnico devuelve material al almacén: vuelve al stock. */
    @PutMapping("/{id}/devolver")
    @Transactional
    public ResponseEntity<?> devolver(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        AsignacionMaterial a = repo.findById(id).orElse(null);
        if (a == null) return error("Asignación no encontrada");
        Long cant = numero(body.get("cantidad"));
        if (cant == null || cant <= 0) return error("La cantidad debe ser mayor a 0");
        if (cant > a.getPendiente()) return error("El técnico solo tiene " + a.getPendiente() + " pendiente(s)");

        Product p = a.getProducto();
        if (p != null) {
            p.setUnidad((p.getUnidad() == null ? 0 : p.getUnidad()) + cant.intValue());
            productos.save(p);
            stockSync.desdeProducto(p);
        }
        a.setCantidadDevuelta(a.getCantidadDevuelta() + cant.intValue());
        String detalle = texto(body.get("detalle"));
        agregar(a, "Devuelto " + cant + (detalle.isEmpty() ? "" : " · " + detalle));
        return ResponseEntity.ok(repo.save(cerrarSiCorresponde(a)));
    }

    /** Solo se puede anular una entrega sin movimientos: regresa todo al stock. */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> anular(@PathVariable Long id) {
        AsignacionMaterial a = repo.findById(id).orElse(null);
        if (a == null) return error("Asignación no encontrada");
        if (a.getCantidadUsada() > 0 || a.getCantidadDevuelta() > 0)
            return error("No se puede anular: ya tiene usos o devoluciones registradas");
        Product p = a.getProducto();
        if (p != null) {
            p.setUnidad((p.getUnidad() == null ? 0 : p.getUnidad()) + a.getCantidad());
            productos.save(p);
            stockSync.desdeProducto(p);
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private AsignacionMaterial cerrarSiCorresponde(AsignacionMaterial a) {
        if (a.getPendiente() <= 0) {
            a.setEstado("CERRADO");
            a.setFechaCierre(LocalDateTime.now());
        } else {
            a.setEstado("PARCIAL");
        }
        return a;
    }

    private void agregar(AsignacionMaterial a, String texto) {
        String previo = a.getBitacora() == null ? "" : a.getBitacora() + "\n";
        a.setBitacora(previo + linea(texto));
    }

    private String linea(String t) { return LocalDateTime.now().format(FMT) + " — " + t; }

    private Long numero(Object o) {
        if (o == null) return null;
        try { return Long.valueOf(String.valueOf(o).trim().split("\\.")[0]); } catch (Exception e) { return null; }
    }

    private String texto(Object o) { return o == null ? "" : String.valueOf(o).trim(); }

    private ResponseEntity<?> error(String msg) {
        return ResponseEntity.badRequest().body(Map.of("message", msg));
    }
}
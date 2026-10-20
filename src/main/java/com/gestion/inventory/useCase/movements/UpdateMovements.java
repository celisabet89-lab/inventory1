package com.gestion.inventory.useCase.movements;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.User;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.repository.ExistingRepository;
import com.gestion.inventory.repository.UserRepository;
import com.gestion.inventory.sevice.IMovementsService;
import com.gestion.inventory.sevice.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import com.gestion.inventory.sevice.StockSyncService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMovements {
    @Autowired
    StockSyncService stockSync;

    @Autowired
    IMovementsService movementsService;
    @Autowired
    IProductService productService;
    @Autowired
    ExistingRepository existingRepository;
    @Autowired
    UserRepository userRepository;
    @Transactional
    public Movements execute(
            Long id,
            MovementsRequest r) {
        Movements mov =
                movementsService.getById(id);
        if (mov == null) {
            throw new RuntimeException(
                    "Movimiento no encontrado: " + id);
        }
        Existing stock =
                existingRepository
                        .findByProduct(mov.getProduct())
                        .orElse(null);
        if (stock != null) {
            int stockAnterior =
                    stock.getStockACtual() != null
                            ? stock.getStockACtual()
                            : 0;
            String tipoAnterior =
                    mov.getTipo() != null
                            ? mov.getTipo().toUpperCase()
                            : "ENTRADA";
            int cantidadAnterior =
                    mov.getCantidad() != null
                            ? mov.getCantidad()
                            : 0;
            int revertido =
                    tipoAnterior.equals("ENTRADA")
                            ? stockAnterior - cantidadAnterior
                            : stockAnterior + cantidadAnterior;
            String nuevoTipo =
                    r.getTipo() != null
                            ? r.getTipo().toUpperCase()
                            : tipoAnterior;
            int nuevaCantidad =
                    r.getCantidad() != null
                            ? r.getCantidad()
                            : cantidadAnterior;
            int nuevoStock =
                    nuevoTipo.equals("ENTRADA")
                            ? revertido + nuevaCantidad
                            : revertido - nuevaCantidad;
            if (nuevoStock < 0) {
                nuevoStock = 0;
            }
            stock.setStockACtual(nuevoStock);
            existingRepository.save(stock);
            stockSync.resolverAlertas(stock);
            Product producto =
                    mov.getProduct();
            producto.setUnidad(nuevoStock);
            productService.save(producto);
        }
        if (r.getCantidad() != null) {
            mov.setCantidad(
                    r.getCantidad());
        }
        if (r.getTipo() != null) {
            mov.setTipo(
                    r.getTipo().trim().toUpperCase());
        }
        if (r.getResponsable() != null) {
            mov.setResponsable(
                    limpiar(r.getResponsable()));
        }
        if (r.getObservaciones() != null) {
            mov.setObservaciones(
                    limpiar(r.getObservaciones()));
        }
        if (r.getCodigo() != null) {
            mov.setCodigo(
                    limpiar(r.getCodigo()));
        }
        if (r.getFormaPago() != null) {
            mov.setFormaPago(
                    limpiar(r.getFormaPago()));
        }
        if (r.getEstadoPago() != null) {
            mov.setEstadoPago(
                    limpiar(r.getEstadoPago()));
        }
        if (r.getTotal() != null) {
            mov.setTotal(
                    r.getTotal());
        }
        if (r.getFecha() != null) {
            mov.setFecha(
                    r.getFecha());
        }
        Long idUsuario =
                r.getUserId();
        if (idUsuario == null) {
            idUsuario = r.getUsuario();
        }
        if (idUsuario != null) {
            User usuario =
                    userRepository
                            .findById(idUsuario)
                            .orElse(null);
            if (usuario != null) {
                mov.setUser(usuario);
            }
        }
        return movementsService.save(mov);
    }
    String limpiar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio =
                valor.trim();
        return limpio.isEmpty()
                ? null
                : limpio;
    }
}
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class CreateMovements {
    @Autowired
    IMovementsService movementsService;
    @Autowired
    IProductService productService;
    @Autowired
    ExistingRepository existingRepository;
    @Autowired
    UserRepository userRepository;
    @Transactional
    public Movements execute(MovementsRequest r) {
        if (r.getIdProducto() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El producto es obligatorio");
        }
        Product producto = productService.getById(r.getIdProducto());
        if (producto == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Producto no encontrado: " + r.getIdProducto());
        }
        String tipo = r.getTipo() != null
                ? r.getTipo().trim().toUpperCase()
                : "ENTRADA";
        if (!tipo.equals("ENTRADA") && !tipo.equals("SALIDA")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo inválido. Usar: ENTRADA o SALIDA");
        }
        int cantidad = r.getCantidad() != null
                ? r.getCantidad()
                : 0;
        if (cantidad <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor a 0");
        }
        Existing stock = existingRepository
                .findByProduct(producto)
                .orElseGet(() -> {
                    Existing nuevo = new Existing();
                    nuevo.setProduct(producto);
                    nuevo.setStockACtual(0);
                    nuevo.setStockMinimo(5);
                    return existingRepository.save(nuevo);});
        int stockActual = stock.getStockACtual() != null
                ? stock.getStockACtual()
                : 0;
        if (tipo.equals("SALIDA") && stockActual < cantidad) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Stock insuficiente. Disponible: "
                            + stockActual
                            + ", Solicitado: "
                            + cantidad);
        }
        int nuevoStock = tipo.equals("ENTRADA")
                ? stockActual + cantidad
                : stockActual - cantidad;
        stock.setStockACtual(nuevoStock);
        existingRepository.save(stock);
        producto.setUnidad(nuevoStock);
        productService.save(producto);
        Movements mov = new Movements();
        mov.setProduct(producto);
        mov.setCantidad(cantidad);
        mov.setTipo(tipo);
        mov.setResponsable(
                limpiar(r.getResponsable()));
        mov.setObservaciones(
                limpiar(r.getObservaciones()));
        mov.setCodigo(
                limpiar(r.getCodigo()));
        mov.setFormaPago(
                limpiar(r.getFormaPago()));
        mov.setEstadoPago(
                limpiar(r.getEstadoPago()));
        BigDecimal total = r.getTotal();
        if (total == null && producto.getPrecio() != null) {
            total = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(cantidad));
        }
        mov.setTotal(total);
        Long idUsuario = r.getUserId();
        if (idUsuario == null) {
            idUsuario = r.getUsuario();
        }
        if (idUsuario != null) {
            User usuario = userRepository
                    .findById(idUsuario)
                    .orElse(null);
            if (usuario != null) {
                mov.setUser(usuario);
            }
        }
        if (r.getFecha() != null) {
            mov.setFecha(r.getFecha());
        }
        return movementsService.save(mov);
    }
    String limpiar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }
}
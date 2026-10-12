package com.gestion.inventory.useCase.movements;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.repository.ExistingRepository;
import com.gestion.inventory.sevice.IMovementsService;
import com.gestion.inventory.sevice.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AnularMovements {

    @Autowired
    IMovementsService movementsService;
    @Autowired
    ExistingRepository existingRepository;
    @Autowired
    IProductService productService;
    @Transactional
    public Movements execute(Long id) {
        Movements movimiento =
                movementsService.getById(id);
        if (movimiento == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Movimiento no encontrado: " + id);
        }
        if (
                movimiento.getEstadoMovimiento() != null &&
                        movimiento
                                .getEstadoMovimiento()
                                .equalsIgnoreCase("ANULADO")
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El movimiento ya está anulado");
        }
        Product producto =
                movimiento.getProduct();
        if (producto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El movimiento no tiene producto asociado");
        }
        int cantidad =
                movimiento.getCantidad() != null
                        ? movimiento.getCantidad()
                        : 0;
        if (cantidad <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad del movimiento no es válida");
        }
        Existing stock =
                existingRepository
                        .findByProduct(producto)
                        .orElse(null);
        if (stock == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No existe stock asociado al producto");
        }
        int stockActual =
                stock.getStockACtual() != null
                        ? stock.getStockACtual()
                        : 0;
        String tipo =
                movimiento.getTipo() != null
                        ? movimiento
                        .getTipo()
                        .trim()
                        .toUpperCase()
                        : "";
        int nuevoStock;
        if (tipo.equals("ENTRADA")) {
            nuevoStock =
                    stockActual - cantidad;
            if (nuevoStock < 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se puede anular la compra porque el stock actual es insuficiente");
            }
        } else if (tipo.equals("SALIDA")) {
            nuevoStock =
                    stockActual + cantidad;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de movimiento inválido: " + tipo);
        }
        stock.setStockACtual(nuevoStock);
        existingRepository.save(stock);
        producto.setUnidad(nuevoStock);
        productService.save(producto);
        movimiento.setEstadoMovimiento("ANULADO");
        return movementsService.save(movimiento);
    }
}
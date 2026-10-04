package com.gestion.inventory.useCase.movements;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.repository.ExistingRepository;
import com.gestion.inventory.sevice.IMovementsService;
import com.gestion.inventory.sevice.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;

@Service
public class CreateMovements {

    @Autowired private IMovementsService  movementsService;
    @Autowired private IProductService    productService;
    @Autowired private ExistingRepository existingRepository;

    @Transactional
    public Movements execute(MovementsRequest r) {
        if (r.getIdProducto() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto es obligatorio");

        Product producto = productService.getById(r.getIdProducto());
        if (producto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + r.getIdProducto());

        // Aceptar tipo en mayúsculas o minúsculas
        String tipo = r.getTipo() != null ? r.getTipo().toUpperCase() : "ENTRADA";
        if (!tipo.equals("ENTRADA") && !tipo.equals("SALIDA"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo inválido. Usar: ENTRADA o SALIDA");

        int cantidad = r.getCantidad() != null ? r.getCantidad() : 0;
        if (cantidad <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a 0");

        // Obtener o crear registro de stock (Existing)
        Existing stock = existingRepository.findByProduct(producto)
                .orElseGet(() -> {
                    Existing nuevo = new Existing();
                    nuevo.setProduct(producto);
                    nuevo.setStockACtual(0);
                    nuevo.setStockMinimo(5);
                    return existingRepository.save(nuevo);
                });

        int stockActual = stock.getStockACtual() != null ? stock.getStockACtual() : 0;

        // Validar stock suficiente para SALIDAS
        if (tipo.equals("SALIDA") && stockActual < cantidad)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock insuficiente. Disponible: " + stockActual + ", Solicitado: " + cantidad);

        // Calcular nuevo stock
        int nuevoStock = tipo.equals("ENTRADA")
                ? stockActual + cantidad
                : stockActual - cantidad;   // FIX: operador resta correcto

        // Actualizar Existing
        stock.setStockACtual(nuevoStock);
        existingRepository.save(stock);

        // Sincronizar product.unidad
        producto.setUnidad(nuevoStock);
        productService.save(producto);

        // Crear movimiento
        Movements mov = new Movements();
        mov.setCantidad(cantidad);
        mov.setTipo(tipo);
        mov.setResponsable(r.getResponsable());
        mov.setObservaciones(r.getObservaciones());
        mov.setProduct(producto);
        mov.setFecha(java.sql.Date.valueOf(LocalDate.now()));

        return movementsService.save(mov);
    }
}
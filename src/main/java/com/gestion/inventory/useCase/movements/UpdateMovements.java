package com.gestion.inventory.useCase.movements;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.repository.ExistingRepository;
import com.gestion.inventory.sevice.IMovementsService;
import com.gestion.inventory.sevice.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMovements {

    @Autowired private IMovementsService  movementsService;
    @Autowired private IProductService    productService;
    @Autowired private ExistingRepository existingRepository;

    @Transactional
    public Movements execute(Long id, MovementsRequest r) {
        Movements mov = movementsService.getById(id);

        // Revertir el efecto del movimiento anterior en el stock
        Existing stock = existingRepository.findByProduct(mov.getProduct()).orElse(null);
        if (stock != null) {
            int stockAnterior = stock.getStockACtual();
            String tipoAnterior = mov.getTipo().toUpperCase();
            int cantAnterior = mov.getCantidad() != null ? mov.getCantidad() : 0;
            int revertido = tipoAnterior.equals("ENTRADA")
                    ? stockAnterior - cantAnterior
                    : stockAnterior + cantAnterior;

            String nuevoTipo = r.getTipo() != null ? r.getTipo().toUpperCase() : tipoAnterior;
            int nuevaCant = r.getCantidad() != null ? r.getCantidad() : cantAnterior;
            int nuevoStock = nuevoTipo.equals("ENTRADA")
                    ? revertido + nuevaCant
                    : revertido - nuevaCant;

            if (nuevoStock < 0) nuevoStock = 0;
            stock.setStockACtual(nuevoStock);
            existingRepository.save(stock);

            mov.getProduct().setUnidad(nuevoStock);
            productService.save(mov.getProduct());
        }

        if (r.getCantidad() != null)     mov.setCantidad(r.getCantidad());
        if (r.getTipo() != null)         mov.setTipo(r.getTipo().toUpperCase());
        if (r.getResponsable() != null)  mov.setResponsable(r.getResponsable());
        if (r.getObservaciones() != null) mov.setObservaciones(r.getObservaciones());

        return movementsService.save(mov);
    }
}
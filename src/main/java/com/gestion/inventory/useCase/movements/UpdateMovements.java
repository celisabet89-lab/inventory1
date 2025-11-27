package com.gestion.inventory.useCase.movements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.sevice.IMovementsService;

@Service
public class UpdateMovements {
    @Autowired 
    IMovementsService movementsService;
    public Movements execute(Long id, MovementsRequest movementsRequest){
        Movements movementsUpdate= movementsService.getById(id);
        movementsUpdate.setCantidad(movementsRequest.getCantidad());
        movementsUpdate.setTipo(movementsRequest.getTipo());
        movementsUpdate.setResponsable(movementsRequest.getResponsable());
        movementsUpdate.setObservaciones(movementsRequest.getObservaciones());
        return movementsService.save(movementsUpdate);
    }
}

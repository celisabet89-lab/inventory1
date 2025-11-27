package com.gestion.inventory.useCase.movements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.sevice.IMovementsService;

@Service
public class CreateMovements {
    @Autowired
    IMovementsService movementsService;
    public Movements execute(MovementsRequest movementsRequest){
        Movements movements=new Movements();
        movements.setCantidad(movementsRequest.getCantidad());
        movements.setTipo(movementsRequest.getTipo());
        movements.setResponsable(movementsRequest.getResponsable());
        movements.setObservaciones(movementsRequest.getObservaciones());
        return movementsService.save(movements);
    }
}

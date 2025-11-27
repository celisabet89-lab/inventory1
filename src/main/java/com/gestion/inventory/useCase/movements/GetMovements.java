package com.gestion.inventory.useCase.movements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.sevice.IMovementsService;

@Service
public class GetMovements {
    @Autowired
    IMovementsService movementsService;
    public Movements execute(Long id){
        return movementsService.getById(id);
    }
}

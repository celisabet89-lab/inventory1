package com.gestion.inventory.useCase.movements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IMovementsService;

@Service
public class DeleteMovements {
    @Autowired
    IMovementsService movementsService;
    public void execute(Long id){
        movementsService.deleteById(id);
    }
}

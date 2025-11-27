package com.gestion.inventory.useCase.movements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.sevice.IMovementsService;

@Service
public class GetAllMovements {
    @Autowired
    IMovementsService movementsService;
    public List<Movements> execute(){
        return movementsService.getAll();
    }
}

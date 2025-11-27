package com.gestion.inventory.useCase.existing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.sevice.IExistingService;

@Service
public class GetExisting {
    @Autowired
    IExistingService existingService;
    public Existing execute(Long id){
        return existingService.getById(id);
    }
}

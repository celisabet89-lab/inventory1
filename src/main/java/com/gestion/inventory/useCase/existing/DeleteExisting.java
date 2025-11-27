package com.gestion.inventory.useCase.existing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IExistingService;

@Service
public class DeleteExisting {
    @Autowired
    IExistingService existingService;
    public void execute(Long idExisting){
        existingService.deleteById(idExisting);
    }
}

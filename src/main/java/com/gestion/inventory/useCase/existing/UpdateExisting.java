package com.gestion.inventory.useCase.existing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.dtorecust.ExistingRequest;
import com.gestion.inventory.sevice.IExistingService;

@Service
public class UpdateExisting {
    @Autowired
    IExistingService existingService;
    public Existing execute(Long id, ExistingRequest existingRequest){
        Existing existingUpdate= existingService.getById(id);
        existingUpdate.setStockACtual(existingRequest.getStockACtual());
        existingUpdate.setStockMinimo(existingRequest.getStockMinimo());
        return existingService.save(existingUpdate);
    }
}

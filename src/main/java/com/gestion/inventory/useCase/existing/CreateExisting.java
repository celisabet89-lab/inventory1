package com.gestion.inventory.useCase.existing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.dtorecust.ExistingRequest;
import com.gestion.inventory.sevice.IExistingService;

@Service
public class CreateExisting {
    @Autowired
    IExistingService existingService;
    public Existing execute(ExistingRequest existingRequest){
        Existing existing=new Existing();
        existing.setStockACtual(existingRequest.getStockACtual());
        existing.setStockMinimo(existingRequest.getStockMinimo());
        return existingService.save(existing);
    }
}

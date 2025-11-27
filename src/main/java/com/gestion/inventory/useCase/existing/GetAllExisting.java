package com.gestion.inventory.useCase.existing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.sevice.IExistingService;

@Service
public class GetAllExisting {
    @Autowired
    IExistingService existingService;
    public List<Existing> execute(){
        return existingService.getAll();
    }
}

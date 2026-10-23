package com.gestion.inventory.useCase.onu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.inventory.sevice.IOnuservice;
import com.gestion.inventory.sevice.OnuStockService;

@Service
public class DeleteOnu {
    @Autowired
    IOnuservice onuService;
    @Autowired
    OnuStockService stockService;
    @Transactional
    public void execute(Long idOnu){
        com.gestion.inventory.domain.Onu onu = onuService.getById(idOnu);
        if (onu != null && OnuStockService.disponible(onu.getEstado())) stockService.restar(onu.getMarca());
        onuService.deleteById(idOnu);
    }
}
package com.gestion.inventory.useCase.lugarVenta;

import com.gestion.inventory.sevice.ILugarVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteLugarVenta {
    @Autowired private ILugarVentaService lugarVentaService;
    public void execute(Long id) { lugarVentaService.delete(id); }
}
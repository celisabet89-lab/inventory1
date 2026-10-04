package com.gestion.inventory.useCase.lugarVenta;

import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.sevice.ILugarVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetLugarVenta {
    @Autowired private ILugarVentaService lugarVentaService;
    public LugarVenta execute(Long id) { return lugarVentaService.getById(id); }
}
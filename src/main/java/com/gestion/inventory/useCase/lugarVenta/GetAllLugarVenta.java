package com.gestion.inventory.useCase.lugarVenta;

import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.sevice.ILugarVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllLugarVenta {
    @Autowired private ILugarVentaService lugarVentaService;
    public List<LugarVenta> execute() { return lugarVentaService.getAll(); }
}
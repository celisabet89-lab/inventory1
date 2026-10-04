package com.gestion.inventory.useCase.lugarVenta;

import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.domain.dtorecust.LugarventaRequest;
import com.gestion.inventory.repository.ClienteRepository;
import com.gestion.inventory.sevice.ILugarVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateLugarVenta {
    @Autowired
    private ILugarVentaService lugarVentaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public LugarVenta execute(LugarventaRequest req) {
        LugarVenta lugarVenta = new LugarVenta();
        lugarVenta.setNombre(req.getNombre());
              return lugarVentaService.save(lugarVenta);
    }
}
package com.gestion.inventory.useCase.lugarVenta;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.domain.dtorecust.LugarventaRequest;
import com.gestion.inventory.repository.ClienteRepository;
import com.gestion.inventory.sevice.ILugarVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateLugarVenta {
    @Autowired
    private ILugarVentaService lugarVentaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public LugarVenta execute(Long id, LugarventaRequest req) {
        LugarVenta lv = lugarVentaService.getById(id);
        lv.setNombre(req.getNombre());
        return lugarVentaService.save(lv);
    }
}
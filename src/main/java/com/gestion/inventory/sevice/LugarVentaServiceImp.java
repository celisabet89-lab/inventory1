package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.repository.Lugarventarepository;
import com.gestion.inventory.sevice.ILugarVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LugarVentaServiceImp implements ILugarVentaService {
    Lugarventarepository lugarventarepository;
    public LugarVentaServiceImp(Lugarventarepository lugarventarepository) {
       this.lugarventarepository = lugarventarepository;
    }
    @Override
    public List<LugarVenta> getAll() {
        return lugarventarepository.findAll();
    }

    @Override
    public LugarVenta getById(Long id) {
        return lugarventarepository.findById(id).orElse(null);
    }

    @Override
    public LugarVenta save(LugarVenta lugarVenta) {
        return lugarventarepository.save(lugarVenta);
    }

    @Override
    public void delete(Long id) {
        lugarventarepository.deleteById(id);
    }

}
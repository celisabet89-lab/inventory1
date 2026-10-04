package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.LugarVenta;
import java.util.List;

public interface ILugarVentaService {
    List<LugarVenta> getAll();
    LugarVenta getById(Long id);
    LugarVenta save(LugarVenta lugarVenta);
    void delete(Long id);
}
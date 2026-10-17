package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.domain.dtorecust.CobroRequest;
import java.util.List;

public interface ICobroService {
    List<Cobro> getAll();
    Cobro getById(Long id);
    Cobro registrarCobro(CobroRequest request);
    List<Cobro> getByCodigoVenta(String codigoVenta);
    Cobro anularCobro(Long id);
}

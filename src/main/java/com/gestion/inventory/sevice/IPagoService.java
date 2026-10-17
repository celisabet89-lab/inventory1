package com.gestion.inventory.sevice;


import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.domain.dtorecust.PagoRequest;

import java.util.List;

public interface IPagoService {
    List<Pago> getAll();
    Pago getById(Long id);
    Pago registrarPago(PagoRequest request);
    List<Pago> getByCodigoCompra(String codigoCompra);
    Pago anularPago(Long idPago, String motivo);
}

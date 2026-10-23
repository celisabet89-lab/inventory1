package com.gestion.inventory.useCase.onu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.OnuMovimiento;
import com.gestion.inventory.domain.dtorecust.OnuRequest;
import com.gestion.inventory.repository.OnuMovimientoRepository;
import com.gestion.inventory.sevice.IOnuservice;
import com.gestion.inventory.sevice.OnuStockService;

@Service
public class CreateOnu {
    @Autowired
    IOnuservice onuservice;
    @Autowired
    OnuStockService stockService;
    @Autowired
    OnuMovimientoRepository movimientoRepository;

    @Transactional
    public Onu execute(OnuRequest onuRequest){
        Onu onu = new Onu();
        onu.setMarca(onuRequest.getMarca());
        onu.setModelo(onuRequest.getModelo());
        onu.setSerial(onuRequest.getSerial());
        onu.setMac(onuRequest.getMac());
        onu.setObservaciones(onuRequest.getObservaciones());
        onu.setEstado(onuRequest.getEstado());
        onu.setContrato(onuRequest.getContrato());
        onu.setCodigoQR(onuRequest.getCodigoQR());
        onu.setTipoPortador(onuRequest.getTipoPortador());
        onu.setPortador(onuRequest.getPortador());
        onu.setAlmacen(onuRequest.getAlmacen());
        if (onu.getAlmacen() != null && !onu.getAlmacen().isBlank()
                && (onu.getTipoPortador() == null || onu.getTipoPortador().isBlank())) {
            onu.setTipoPortador("Almacén");
            onu.setPortador(onu.getAlmacen());
        }
        stockService.vincular(onu, OnuStockService.disponible(onu.getEstado())
                ? stockService.sumar(onu.getMarca())
                : stockService.asegurar(onu.getMarca()));
        Onu guardada = onuservice.save(onu);

        OnuMovimiento m = new OnuMovimiento();
        m.setOnu(guardada);
        m.setTipo("REGISTRO");
        m.setEstadoNuevo(guardada.getEstado());
        m.setUsuario(onuRequest.getRegistradoPor());
        m.setCargo(onuRequest.getCargoRegistro());
        m.setAlmacen(guardada.getAlmacen());
        m.setDetalle("ONU registrada en el sistema");
        movimientoRepository.save(m);
        return guardada;
    }
}
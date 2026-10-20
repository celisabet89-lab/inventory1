package com.gestion.inventory.useCase.onu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.dtorecust.OnuRequest;
import com.gestion.inventory.sevice.IOnuservice;
import com.gestion.inventory.sevice.OnuStockService;

@Service
public class CreateOnu {
    @Autowired
    IOnuservice onuservice;
    @Autowired
    OnuStockService stockService;
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
        stockService.vincular(onu, stockService.sumar(onu.getMarca()));
        return onuservice.save(onu);
    }
}
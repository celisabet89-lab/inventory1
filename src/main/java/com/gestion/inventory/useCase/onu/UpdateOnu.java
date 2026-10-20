package com.gestion.inventory.useCase.onu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.dtorecust.OnuRequest;
import com.gestion.inventory.sevice.IOnuservice;
import com.gestion.inventory.sevice.OnuStockService;

@Service
public class UpdateOnu {
    @Autowired
    IOnuservice onuservice;
    @Autowired
    OnuStockService stockService;
    @Transactional
    public Onu execute(Long id, OnuRequest onuRequest){
        Onu onuUpdate=onuservice.getById(id);
        String marcaAnterior = onuUpdate.getMarca();
        onuUpdate.setMarca(onuRequest.getMarca());
        onuUpdate.setModelo(onuRequest.getModelo());
        onuUpdate.setSerial(onuRequest.getSerial());
        onuUpdate.setMac(onuRequest.getMac());
        onuUpdate.setObservaciones(onuRequest.getObservaciones());
        onuUpdate.setEstado(onuRequest.getEstado());
        onuUpdate.setContrato(onuRequest.getContrato());
        onuUpdate.setCodigoQR(onuRequest.getCodigoQR());
        onuUpdate.setTipoPortador(onuRequest.getTipoPortador());
        onuUpdate.setPortador(onuRequest.getPortador());
        onuUpdate.setAlmacen(onuRequest.getAlmacen());
        stockService.vincular(onuUpdate, stockService.cambioMarca(marcaAnterior, onuRequest.getMarca()));
        return onuservice.save(onuUpdate);
    }
}
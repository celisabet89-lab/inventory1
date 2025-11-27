package com.gestion.inventory.useCase.onu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.dtorecust.OnuRequest;
import com.gestion.inventory.sevice.IOnuservice;

@Service
public class CreateOnu {
    @Autowired
    IOnuservice onuservice;
    public Onu execute(OnuRequest onuRequest){
        Onu onu = new Onu();
        onu.setMarca(onuRequest.getMarca());
        onu.setModelo(onuRequest.getModelo());
        onu.setSerial(onuRequest.getSerial());
        onu.setMac(onuRequest.getMac());
        onu.setObservaciones(onuRequest.getObservaciones());
        onu.setEstado(onuRequest.getEstado());
        onu.setContrato(onuRequest.getContrato());
        return onuservice.save(onu);
    }
}

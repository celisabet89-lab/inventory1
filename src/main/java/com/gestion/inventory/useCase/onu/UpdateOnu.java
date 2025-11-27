package com.gestion.inventory.useCase.onu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.dtorecust.OnuRequest;
import com.gestion.inventory.sevice.IOnuservice;

@Service
public class UpdateOnu {
    @Autowired
    IOnuservice onuservice;
    public Onu execute(Long id, OnuRequest onuRequest){
        Onu onuUpdate=onuservice.getById(id);
        onuUpdate.setMarca(onuRequest.getMarca());
        onuUpdate.setModelo(onuRequest.getModelo());
        onuUpdate.setSerial(onuRequest.getSerial());
        onuUpdate.setMac(onuRequest.getMac());
        onuUpdate.setObservaciones(onuRequest.getObservaciones());
        onuUpdate.setEstado(onuRequest.getEstado());
        onuUpdate.setContrato(onuRequest.getContrato());
        return onuservice.save(onuUpdate);
    }
}

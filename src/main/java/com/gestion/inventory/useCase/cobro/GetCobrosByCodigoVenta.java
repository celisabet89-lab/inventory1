
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetCobrosByCodigoVenta {
    ICobroService cobroService;
    public GetCobrosByCodigoVenta(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public List<Cobro> execute(String codigoVenta) {
        return cobroService.getByCodigoVenta(codigoVenta);
    }
}

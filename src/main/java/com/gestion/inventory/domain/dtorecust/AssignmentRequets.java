package com.gestion.inventory.domain.dtorecust;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignmentRequets {

    Long idOnu;
    Long idCliente;
    Long idUser;

    Date fecha;
    String datos;
    String fotosUrls;
    String itemsChecklist;
    Boolean checklistCompletado;
}
package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCliente;
    String name;
    String apellido;
    Integer telefono;
    String direccion;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


}

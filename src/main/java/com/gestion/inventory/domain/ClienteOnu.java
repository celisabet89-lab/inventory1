package com.gestion.inventory.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cliente_onu")
public class ClienteOnu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nombre;
    String ci;
    String celular;
    String direccion;
    @Column(length = 500)
    String ubicacion;
    LocalDate fechaNacimiento;
    Long numeroContrato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_cliente_id")
    TipoClienteOnu tipoCliente;
}
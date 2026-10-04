package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lugar_venta")
public class LugarVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLugarVenta;
    private String nombre;
    @OneToMany(mappedBy = "lugarVenta", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("lugarVenta")
    List<Cliente> clientes = new ArrayList<>();
}
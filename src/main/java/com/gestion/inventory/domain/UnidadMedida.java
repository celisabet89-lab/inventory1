package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unidades_medida")
@Getter
@Setter
public class UnidadMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUnidad;
    String nombre;
    String abreviatura;


    @OneToMany(mappedBy = "unidadMedida", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("unidadMedida")
    List<Product> productos = new ArrayList<>();


}
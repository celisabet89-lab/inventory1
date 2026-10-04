package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    Long idProveedor;
    String empresa;
    String pais;
    String contacto;
    String email;
    String ciNit;
    String direccion;
    @Column(name = "estado")
    String estado = "ACTIVO";
    String observaciones;
    @Column(name = "fecha_registro")
    LocalDate fechaRegistro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    Profile profile;
    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDate.now();
        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
    }
}
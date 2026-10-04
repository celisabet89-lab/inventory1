package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMovements;

    Integer cantidad;
    Date    fecha;
    String  tipo;
    String  responsable;
    String  observaciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","password"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Product product;

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) this.fecha = Date.valueOf(LocalDate.now());
    }
}
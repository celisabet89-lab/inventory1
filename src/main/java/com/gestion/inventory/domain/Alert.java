package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alert")
    private Long idAlert;
    String resultado;
    String descripcion;
    LocalDate fecha;
    @Enumerated(EnumType.STRING)
    AlertStatus estado = AlertStatus.PENDIENTE;
    Integer stockActual;
    Integer stockMinimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onu_id")
    private Onu onu;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDate.now();
    }

    public enum AlertStatus {
        PENDIENTE,
        GESTIONADA
    }
}

package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onu_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Onu onu;

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) this.fecha = LocalDate.now();
    }

    public enum AlertStatus { PENDIENTE, GESTIONADA }
}
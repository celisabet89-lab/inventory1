package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDelivery;

    private String codigo;          // Ej: E-00001
    private String estado;          // PENDIENTE / EN_CAMINO / ENTREGADO / CANCELADO
    private LocalDate fecha;
    private String observacion;
    private String direccionEntrega;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tecnico_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private User tecnico;           // técnico que realiza la entrega

    @OneToMany(mappedBy = "delivery", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","delivery"})
    private List<DeliveryDetails> detalles;

    @PrePersist
    protected void onCreate() {
        if (fecha  == null) fecha  = LocalDate.now();
        if (estado == null) estado = "PENDIENTE";
    }
}
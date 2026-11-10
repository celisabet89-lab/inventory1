package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;

    String email;
    /** Se guarda cifrada (BCrypt) y nunca se envía al navegador. */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profileId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rolId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Rol rol;

    /** Token de sesión: solo se envía al iniciar sesión, no se guarda en la base de datos. */
    @Transient
    private String token;
}
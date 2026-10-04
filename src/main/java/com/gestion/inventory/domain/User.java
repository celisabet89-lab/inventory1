package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profileId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rolId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Rol rol;
}
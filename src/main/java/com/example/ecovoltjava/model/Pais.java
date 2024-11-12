package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_PAIS")
@Data
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_PAIS")
    private Long codPais;

    @Column(name = "NOM_PAIS", nullable = false, length = 50)
    private String nome;

}

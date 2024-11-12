package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_ESTADO")
@Data
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_ESTADO")
    private Long codEstado;

    @Column(name = "NOM_ESTADO", nullable = false, length = 50)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "COD_PAIS", nullable = false)
    private Pais pais;

}

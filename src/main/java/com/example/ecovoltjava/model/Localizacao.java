package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_LOCALIZACAO")
@Data
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOCALIZACAO")
    private Long idLocalizacao;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "ENDERECO_COMPLETO", nullable = false, length = 255)
    private String enderecoCompleto;

    @ManyToOne
    @JoinColumn(name = "COD_BAIRRO", nullable = false)
    private Bairro bairro;
}

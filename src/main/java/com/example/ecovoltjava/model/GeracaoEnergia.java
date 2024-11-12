package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "GS_GERACAO_ENERGIA")
@Data
public class GeracaoEnergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GERACAO")
    private Long idGeracao;

    @Column(name = "ENERGIA_GERADA_KWH", nullable = false)
    private Double energiaGeradaKwh;

    @Column(name = "DATA", nullable = false)
    private Date data;

    @ManyToOne
    @JoinColumn(name = "ID_FONTE", nullable = false)
    private FonteEnergia fonteEnergia;
}

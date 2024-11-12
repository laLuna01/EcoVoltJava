package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "GS_CONSUMO_ENERGIA")
@Data
public class ConsumoEnergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSUMO")
    private Long idConsumo;

    @Column(name = "ENERGIA_CONSUMIDA_KWH", nullable = false)
    private Double energiaConsumidaKwh;

    @Column(name = "DATA", nullable = false)
    private Date data;

    @ManyToOne
    @JoinColumn(name = "ID_CONSUMIDOR", nullable = false)
    private Consumidor consumidor;
}

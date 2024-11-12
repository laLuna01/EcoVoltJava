package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_DISPOSITIVO")
@Data
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISPOSITIVO")
    private Long idDispositivo;

    @Column(name = "CONSUMO_ENERGIA_KWH", nullable = false)
    private Double consumoEnergiaKwh;

    @ManyToOne
    @JoinColumn(name = "TIPO_DISPOSITIVO", nullable = false)
    private TipoDispositivo tipoDispositivo;

    @ManyToOne
    @JoinColumn(name = "ID_CONSUMIDOR", nullable = false)
    private Consumidor consumidor;
}

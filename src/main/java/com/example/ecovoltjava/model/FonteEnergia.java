package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_FONTE_ENERGIA")
@Data
public class FonteEnergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FONTE")
    private Long idFonte;

    @Column(name = "GERACAO_ENERGIA_KWH", nullable = false)
    private Double geracaoEnergiaKwh;

    @Column(name = "CAPACIDADE_BATERIA_KWH", nullable = false)
    private Double capacidadeBateriaKwh;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_FONTE", nullable = false)
    private TipoFonte tipoFonte;

    @ManyToOne
    @JoinColumn(name = "ID_LOCALIZACAO", nullable = false)
    private Localizacao localizacao;
}

package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_TIPO_FONTE")
@Data
public class TipoFonte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_FONTE")
    private Long idTipoFonte;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;
}

package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_TIPO_CONSUMIDOR")
@Data
public class TipoConsumidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_CONSUMIDOR")
    private Long idTipoConsumidor;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;
}

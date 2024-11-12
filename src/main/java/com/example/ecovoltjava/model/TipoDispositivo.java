package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_TIPO_DISPOSITIVO")
@Data
public class TipoDispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_DISPOSITIVO")
    private Long idTipoDispositivo;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;
}

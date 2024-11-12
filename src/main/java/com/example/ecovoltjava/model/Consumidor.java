package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_CONSUMIDOR")
@Data
public class Consumidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSUMIDOR")
    private Long idConsumidor;

    @Column(name = "ENERGIA_ATRIBUIDA_KWH", nullable = false)
    private Double energiaAtribuidaKwh;

    @Column(name = "NIVEL_PRIORIDADE", nullable = false, length = 20)
    private String nivelPrioridade;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_CONSUMIDOR", nullable = false)
    private TipoConsumidor tipoConsumidor;

    @ManyToOne
    @JoinColumn(name = "ID_LOCALIZACAO", nullable = false)
    private Localizacao localizacao;
}

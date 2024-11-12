package com.example.ecovoltjava.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GS_BAIRRO")
@Data
public class Bairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_BAIRRO")
    private Long codBairro;

    @Column(name = "NOME", nullable = false, length = 30)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "COD_CIDADE", nullable = false)
    private Cidade cidade;

}

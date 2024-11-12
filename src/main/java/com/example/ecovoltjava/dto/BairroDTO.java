package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BairroDTO {

    @NotNull(message = "O nome é obrigatório")
    @Size(max = 30, message = "O nome não pode ter mais que 30 caracteres")
    private String nome;

    @NotNull(message = "O id da cidade é obrigatório")
    private Long cidade;

}

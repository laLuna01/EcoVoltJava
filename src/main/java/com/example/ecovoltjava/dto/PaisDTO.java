package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaisDTO {
    @NotNull(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome não pode ter mais que 50 caracteres")
    private String nome;
}

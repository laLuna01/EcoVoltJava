package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoDispositivoDTO {
    @NotNull(message = "A descrição é obrigatória")
    @Size(max = 50, message = "A descrição não pode ter mais que 50 caracteres")
    private String descricao;
}

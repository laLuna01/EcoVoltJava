package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConsumidorDTO {
    @NotNull(message = "A energia atribuída em KWH é obrigatória")
    private Double energiaAtribuidaKwh;

    @NotNull(message = "O nível de prioridade é obrigatório")
    @Size(max = 20, message = "O nível de prioridade não pode ter mais que 20 caracteres")
    private String nivelPrioridade;

    @NotNull(message = "O id do tipo de consumidor é obrigatório")
    private Long tipoConsumidor;

    @NotNull(message = "O id da localização é obrigatório")
    private Long localizacao;
}

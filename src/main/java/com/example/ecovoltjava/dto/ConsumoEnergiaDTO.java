package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConsumoEnergiaDTO {
    @NotNull(message = "A energia consumida em KWH é obrigatória")
    private Double energiaConsumidaKwh;

    @NotNull(message = "A data do consumo é obrigatória")
    @Size(max = 20, message = "A data do consumo não pode ter mais que 20 caracteres")
    private String data;

    @NotNull(message = "O id do consumidor é obrigatório")
    private Long consumidor;
}

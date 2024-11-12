package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GeracaoEnergiaDTO {
    @NotNull(message = "A energia gerada em KWH é obrigatória")
    private Double energiaGeradaKwh;

    @NotNull(message = "A data da geração é obrigatória")
    @Size(max = 20, message = "A data da geração não pode ter mais que 20 caracteres")
    private String data;

    @NotNull(message = "O id da fonte de energia é obrigatório")
    private Long fonteEnergia;
}

package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FonteEnergiaDTO {
    @NotNull(message = "A geração de energia em KWH é obrigatória")
    private Double geracaoEnergiaKwh;

    @NotNull(message = "A capacidade da bateria em KWH é obrigatória")
    private Double capacidadeBateriaKwh;

    @NotNull(message = "O id do tipo de fonte é obrigatório")
    private Long tipoFonte;

    @NotNull(message = "O id da localização é obrigatório")
    private Long localizacao;
}

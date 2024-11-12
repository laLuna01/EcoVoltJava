package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DispositivoDTO {
    @NotNull(message = "O consumo de energia em KWH é obrigatório")
    private Double consumoEnergiaKwh;

    @NotNull(message = "O id do tipo de dispositivo é obrigatório")
    private Long tipoDispositivo;

    @NotNull(message = "O id do consumidor é obrigatório")
    private Long consumidor;
}

package com.example.ecovoltjava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocalizacaoDTO {
    private Double latitude;

    private Double longitude;

    @Size(max = 255, message = "O endereço não pode ter mais que 255 caracteres")
    private String enderecoCompleto;

    @NotNull(message = "O id do bairro é obrigatório")
    private Long bairro;
}

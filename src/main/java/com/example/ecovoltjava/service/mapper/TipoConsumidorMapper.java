package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.TipoConsumidorDTO;
import com.example.ecovoltjava.model.TipoConsumidor;
import org.springframework.stereotype.Component;

@Component
public class TipoConsumidorMapper {
    public TipoConsumidor dtoToEntity(TipoConsumidorDTO tipoConsumidorDTO) {
        TipoConsumidor tipoConsumidor = new TipoConsumidor();
        tipoConsumidor.setDescricao(tipoConsumidorDTO.getDescricao());
        return tipoConsumidor;
    }

    public TipoConsumidorDTO entityToDTO(TipoConsumidor tipoConsumidor) {
        TipoConsumidorDTO tipoConsumidorDTO = new TipoConsumidorDTO();
        tipoConsumidorDTO.setDescricao(tipoConsumidor.getDescricao());
        return tipoConsumidorDTO;
    }
}

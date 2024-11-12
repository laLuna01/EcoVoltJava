package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.TipoFonteDTO;
import com.example.ecovoltjava.model.TipoFonte;
import org.springframework.stereotype.Component;

@Component
public class TipoFonteMapper {
    public TipoFonte dtoToEntity(TipoFonteDTO tipoFonteDTO) {
        TipoFonte tipoFonte = new TipoFonte();
        tipoFonte.setDescricao(tipoFonteDTO.getDescricao());
        return tipoFonte;
    }

    public TipoFonteDTO entityToDTO(TipoFonte tipoFonte) {
        TipoFonteDTO tipoFonteDTO = new TipoFonteDTO();
        tipoFonteDTO.setDescricao(tipoFonte.getDescricao());
        return tipoFonteDTO;
    }
}

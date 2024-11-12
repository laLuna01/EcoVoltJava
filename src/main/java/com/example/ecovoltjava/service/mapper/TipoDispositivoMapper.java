package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.TipoDispositivoDTO;
import com.example.ecovoltjava.model.TipoDispositivo;
import org.springframework.stereotype.Component;

@Component
public class TipoDispositivoMapper {
    public TipoDispositivo dtoToEntity(TipoDispositivoDTO tipoDispositivoDTO) {
        TipoDispositivo tipoDispositivo = new TipoDispositivo();
        tipoDispositivo.setDescricao(tipoDispositivoDTO.getDescricao());
        return tipoDispositivo;
    }

    public TipoDispositivoDTO entityToDTO(TipoDispositivo tipoDispositivo) {
        TipoDispositivoDTO tipoDispositivoDTO = new TipoDispositivoDTO();
        tipoDispositivoDTO.setDescricao(tipoDispositivo.getDescricao());
        return tipoDispositivoDTO;
    }
}

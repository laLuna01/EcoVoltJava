package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.PaisDTO;
import com.example.ecovoltjava.model.Pais;
import org.springframework.stereotype.Component;

@Component
public class PaisMapper {
    public Pais dtoToEntity(PaisDTO paisDTO) {
        Pais pais = new Pais();
        pais.setNome(paisDTO.getNome());
        return pais;
    }

    public PaisDTO entityToDTO(Pais pais) {
        PaisDTO paisDTO = new PaisDTO();
        paisDTO.setNome(pais.getNome());
        return paisDTO;
    }
}

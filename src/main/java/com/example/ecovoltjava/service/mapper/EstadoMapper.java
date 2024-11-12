package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.EstadoDTO;
import com.example.ecovoltjava.model.Estado;
import com.example.ecovoltjava.model.Pais;
import com.example.ecovoltjava.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {

    @Autowired
    private PaisRepository paisRepository;

    public Estado dtoToEntity(EstadoDTO estadoDTO) {
        Estado estado = new Estado();
        estado.setNome(estadoDTO.getNome());
        Pais pais = paisRepository.findById(estadoDTO.getPais())
                .orElseThrow(() -> new RuntimeException("Pais não encontrado"));
        estado.setPais(pais);
        return estado;
    }

    public EstadoDTO entityToDTO(Estado estado) {
        EstadoDTO estadoDTO = new EstadoDTO();
        estadoDTO.setNome(estado.getNome());
        estadoDTO.setPais(estado.getPais().getCodPais());
        return estadoDTO;
    }
}

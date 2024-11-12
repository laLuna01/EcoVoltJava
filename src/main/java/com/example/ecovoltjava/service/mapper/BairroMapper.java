package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.BairroDTO;
import com.example.ecovoltjava.model.Bairro;
import com.example.ecovoltjava.model.Cidade;
import com.example.ecovoltjava.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BairroMapper {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Bairro dtoToEntity(BairroDTO bairroDTO) {
        Bairro bairro = new Bairro();
        bairro.setNome(bairroDTO.getNome());
        Cidade cidade = cidadeRepository.findById(bairroDTO.getCidade())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
        bairro.setCidade(cidade);
        return bairro;
    }

    public BairroDTO entityToDTO(Bairro bairro) {
        BairroDTO bairroDTO = new BairroDTO();
        bairroDTO.setNome(bairro.getNome());
        bairroDTO.setCidade(bairro.getCidade().getCodCidade());
        return bairroDTO;
    }
}

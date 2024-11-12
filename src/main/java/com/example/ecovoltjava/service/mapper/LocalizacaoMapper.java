package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.LocalizacaoDTO;
import com.example.ecovoltjava.model.Bairro;
import com.example.ecovoltjava.model.Localizacao;
import com.example.ecovoltjava.repository.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalizacaoMapper {

    @Autowired
    private BairroRepository bairroRepository;

    public Localizacao dtoToEntity(LocalizacaoDTO localizacaoDTO) {
        Localizacao localizacao = new Localizacao();
        localizacao.setLatitude(localizacaoDTO.getLatitude());
        localizacao.setLongitude(localizacaoDTO.getLongitude());
        localizacao.setEnderecoCompleto(localizacaoDTO.getEnderecoCompleto());
        Bairro bairro = bairroRepository.findById(localizacaoDTO.getBairro())
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado"));
        localizacao.setBairro(bairro);
        return localizacao;
    }

    public LocalizacaoDTO entityToDTO(Localizacao localizacao) {
        LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO();
        localizacaoDTO.setLatitude(localizacao.getLatitude());
        localizacaoDTO.setLongitude(localizacao.getLongitude());
        localizacaoDTO.setEnderecoCompleto(localizacao.getEnderecoCompleto());
        localizacaoDTO.setBairro(localizacao.getBairro().getCodBairro());
        return localizacaoDTO;
    }
}

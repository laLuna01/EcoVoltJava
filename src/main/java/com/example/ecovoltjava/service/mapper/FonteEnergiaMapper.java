package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.FonteEnergiaDTO;
import com.example.ecovoltjava.model.FonteEnergia;
import com.example.ecovoltjava.model.Localizacao;
import com.example.ecovoltjava.model.TipoFonte;
import com.example.ecovoltjava.repository.LocalizacaoRepository;
import com.example.ecovoltjava.repository.TipoFonteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FonteEnergiaMapper {

    @Autowired
    private TipoFonteRepository tipoFonteRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public FonteEnergiaDTO entityToDTO(FonteEnergia fonteEnergia) {
        FonteEnergiaDTO fonteEnergiaDTO = new FonteEnergiaDTO();
        fonteEnergiaDTO.setGeracaoEnergiaKwh(fonteEnergia.getGeracaoEnergiaKwh());
        fonteEnergiaDTO.setCapacidadeBateriaKwh(fonteEnergia.getCapacidadeBateriaKwh());
        fonteEnergiaDTO.setTipoFonte(fonteEnergia.getTipoFonte().getIdTipoFonte());
        fonteEnergiaDTO.setLocalizacao(fonteEnergia.getLocalizacao().getIdLocalizacao());
        return fonteEnergiaDTO;
    }

    public FonteEnergia dtoToEntity(FonteEnergiaDTO fonteEnergiaDTO) {
        FonteEnergia fonteEnergia = new FonteEnergia();
        fonteEnergia.setGeracaoEnergiaKwh(fonteEnergiaDTO.getGeracaoEnergiaKwh());
        fonteEnergia.setCapacidadeBateriaKwh(fonteEnergiaDTO.getCapacidadeBateriaKwh());
        TipoFonte tipoFonte = tipoFonteRepository.findById(fonteEnergiaDTO.getTipoFonte())
                .orElseThrow(() -> new RuntimeException("Tipo de Fonte não encontrado"));
        fonteEnergia.setTipoFonte(tipoFonte);
        Localizacao localizacao = localizacaoRepository.findById(fonteEnergiaDTO.getLocalizacao())
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
        fonteEnergia.setLocalizacao(localizacao);
        return fonteEnergia;
    }
}

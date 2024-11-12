package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.ConsumidorDTO;
import com.example.ecovoltjava.model.Consumidor;
import com.example.ecovoltjava.model.Localizacao;
import com.example.ecovoltjava.model.TipoConsumidor;
import com.example.ecovoltjava.repository.LocalizacaoRepository;
import com.example.ecovoltjava.repository.TipoConsumidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorMapper {

    @Autowired
    private TipoConsumidorRepository tipoConsumidorRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public ConsumidorDTO entityToDTO(Consumidor consumidor) {
        ConsumidorDTO consumidorDTO = new ConsumidorDTO();
        consumidorDTO.setEnergiaAtribuidaKwh(consumidor.getEnergiaAtribuidaKwh());
        consumidorDTO.setNivelPrioridade(consumidor.getNivelPrioridade());
        consumidorDTO.setTipoConsumidor(consumidor.getTipoConsumidor().getIdTipoConsumidor());
        consumidorDTO.setLocalizacao(consumidor.getLocalizacao().getIdLocalizacao());
        return consumidorDTO;
    }

    public Consumidor dtoToEntity(ConsumidorDTO consumidorDTO) {
        Consumidor consumidor = new Consumidor();
        consumidor.setEnergiaAtribuidaKwh(consumidorDTO.getEnergiaAtribuidaKwh());
        consumidor.setNivelPrioridade(consumidorDTO.getNivelPrioridade());
        TipoConsumidor tipoConsumidor = tipoConsumidorRepository.findById(consumidorDTO.getTipoConsumidor())
                .orElseThrow(() -> new RuntimeException("Tipo de Consumidor não encontrado"));
        consumidor.setTipoConsumidor(tipoConsumidor);
        Localizacao localizacao = localizacaoRepository.findById(consumidorDTO.getLocalizacao())
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
        consumidor.setLocalizacao(localizacao);
        return consumidor;
    }
}

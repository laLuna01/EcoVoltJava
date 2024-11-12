package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.ConsumoEnergiaDTO;
import com.example.ecovoltjava.model.Consumidor;
import com.example.ecovoltjava.model.ConsumoEnergia;
import com.example.ecovoltjava.repository.ConsumidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ConsumoEnergiaMapper {

    @Autowired
    private ConsumidorRepository consumidorRepository;

    public ConsumoEnergiaDTO entityToDTO(ConsumoEnergia consumoEnergia) {
        ConsumoEnergiaDTO consumoEnergiaDTO = new ConsumoEnergiaDTO();
        consumoEnergiaDTO.setEnergiaConsumidaKwh(consumoEnergia.getEnergiaConsumidaKwh());
        LocalDate localDate = consumoEnergia.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        consumoEnergiaDTO.setData(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        consumoEnergiaDTO.setConsumidor(consumoEnergia.getConsumidor().getIdConsumidor());
        return consumoEnergiaDTO;
    }

    public ConsumoEnergia dtoToEntity(ConsumoEnergiaDTO consumoEnergiaDTO) {
        ConsumoEnergia consumoEnergia = new ConsumoEnergia();
        consumoEnergia.setEnergiaConsumidaKwh(consumoEnergiaDTO.getEnergiaConsumidaKwh());
        LocalDate localDate = LocalDate.parse(consumoEnergiaDTO.getData(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        consumoEnergia.setData(java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Consumidor consumidor = consumidorRepository.findById(consumoEnergiaDTO.getConsumidor())
                .orElseThrow(() -> new RuntimeException("Consumidor não encontrado"));
        consumoEnergia.setConsumidor(consumidor);
        return consumoEnergia;
    }
}

package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.GeracaoEnergiaDTO;
import com.example.ecovoltjava.model.GeracaoEnergia;
import com.example.ecovoltjava.model.FonteEnergia;
import com.example.ecovoltjava.repository.FonteEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class GeracaoEnergiaMapper {

    @Autowired
    private FonteEnergiaRepository fonteEnergiaRepository;

    public GeracaoEnergiaDTO entityToDTO(GeracaoEnergia geracaoEnergia) {
        GeracaoEnergiaDTO geracaoEnergiaDTO = new GeracaoEnergiaDTO();
        geracaoEnergiaDTO.setEnergiaGeradaKwh(geracaoEnergia.getEnergiaGeradaKwh());
        LocalDate localDate = geracaoEnergia.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        geracaoEnergiaDTO.setData(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        geracaoEnergiaDTO.setFonteEnergia(geracaoEnergia.getFonteEnergia().getIdFonte());
        return geracaoEnergiaDTO;
    }

    public GeracaoEnergia dtoToEntity(GeracaoEnergiaDTO geracaoEnergiaDTO) {
        GeracaoEnergia geracaoEnergia = new GeracaoEnergia();
        geracaoEnergia.setEnergiaGeradaKwh(geracaoEnergiaDTO.getEnergiaGeradaKwh());
        LocalDate localDate = LocalDate.parse(geracaoEnergiaDTO.getData(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        geracaoEnergia.setData(java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        FonteEnergia fonteEnergia = fonteEnergiaRepository.findById(geracaoEnergiaDTO.getFonteEnergia())
                .orElseThrow(() -> new RuntimeException("Fonte de Energia não encontrada"));
        geracaoEnergia.setFonteEnergia(fonteEnergia);
        return geracaoEnergia;
    }
}

package com.example.ecovoltjava.service.mapper;

import com.example.ecovoltjava.dto.DispositivoDTO;
import com.example.ecovoltjava.model.*;
import com.example.ecovoltjava.repository.ConsumidorRepository;
import com.example.ecovoltjava.repository.TipoDispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DispositivoMapper {

    @Autowired
    private TipoDispositivoRepository tipoDispositivoRepository;

    @Autowired
    private ConsumidorRepository consumidorRepository;

    public DispositivoDTO entityToDTO(Dispositivo dispositivo) {
        DispositivoDTO dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setConsumoEnergiaKwh(dispositivo.getConsumoEnergiaKwh());
        dispositivoDTO.setTipoDispositivo(dispositivo.getTipoDispositivo().getIdTipoDispositivo());
        dispositivoDTO.setConsumidor(dispositivo.getConsumidor().getIdConsumidor());
        return dispositivoDTO;
    }

    public Dispositivo dtoToEntity(DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setConsumoEnergiaKwh(dispositivoDTO.getConsumoEnergiaKwh());
        TipoDispositivo tipoDispositivo = tipoDispositivoRepository.findById(dispositivoDTO.getTipoDispositivo())
                .orElseThrow(() -> new RuntimeException("Tipo de Dispositivo não encontrado"));
        dispositivo.setTipoDispositivo(tipoDispositivo);
        Consumidor consumidor = consumidorRepository.findById(dispositivoDTO.getConsumidor())
                .orElseThrow(() -> new RuntimeException("Consumidor não encontrado"));
        dispositivo.setConsumidor(consumidor);
        return dispositivo;
    }
}

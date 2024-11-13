package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.ConsumoEnergiaDTO;
import com.example.ecovoltjava.model.ConsumoEnergia;
import com.example.ecovoltjava.repository.ConsumoEnergiaRepository;
import com.example.ecovoltjava.service.mapper.ConsumoEnergiaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/consumoenergia", produces = "application/json")
@Tag(name = "api-consumo-energia")
public class ConsumoEnergiaController {

    @Autowired
    private ConsumoEnergiaRepository consumoEnergiaRepository;

    @Autowired
    private ConsumoEnergiaMapper consumoEnergiaMapper;

    @PostMapping
    @Operation(summary = "Cria um consumo de energia e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo de Energia cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<ConsumoEnergia>> criarConsumoEnergia(@Valid @RequestBody ConsumoEnergiaDTO consumoEnergiaDTO) {
        ConsumoEnergia consumoEnergiaConvertido = consumoEnergiaMapper.dtoToEntity(consumoEnergiaDTO);
        ConsumoEnergia consumoEnergiaCriado = consumoEnergiaRepository.save(consumoEnergiaConvertido);
        EntityModel<ConsumoEnergia> resource = EntityModel.of(consumoEnergiaCriado,
                linkTo(methodOn(ConsumoEnergiaController.class).getConsumoEnergiaById(consumoEnergiaCriado.getIdConsumo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os consumos de energia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum consumo de energia encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumos de Energia retornados com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<ConsumoEnergia>>> getAllConsumosEnergia(@PageableDefault(size = 10) Pageable pageable) {
        Page<ConsumoEnergia> paginaConsumosEnergia = consumoEnergiaRepository.findAll(pageable);
        if (paginaConsumosEnergia.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<ConsumoEnergia>> consumosEnergiaComLinks = paginaConsumosEnergia.stream()
                .map(consumoEnergia -> EntityModel.of(consumoEnergia,
                        linkTo(methodOn(ConsumoEnergiaController.class).getConsumoEnergiaById(consumoEnergia.getIdConsumo())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<ConsumoEnergia>> collectionModel = CollectionModel.of(consumosEnergiaComLinks,
                linkTo(methodOn(ConsumoEnergiaController.class).getAllConsumosEnergia(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um consumo de energia pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consumo de Energia não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumo de Energia encontrado com sucesso")
    })
    public ResponseEntity<EntityModel<ConsumoEnergia>> getConsumoEnergiaById(@PathVariable Long id) {
        Optional<ConsumoEnergia> consumoEnergiaSalvo = consumoEnergiaRepository.findById(id);
        if (consumoEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ConsumoEnergia consumoEnergia = consumoEnergiaSalvo.get();
        EntityModel<ConsumoEnergia> resource = EntityModel.of(consumoEnergia,
                linkTo(methodOn(ConsumoEnergiaController.class).getConsumoEnergiaById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um consumo de energia existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Consumo de Energia não encontrado ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumo de Energia atualizado com sucesso")
    })
    public ResponseEntity<EntityModel<ConsumoEnergia>> updateConsumoEnergia(@PathVariable Long id, @Valid @RequestBody ConsumoEnergiaDTO consumoEnergiaDTO) {
        Optional<ConsumoEnergia> consumoEnergiaSalvo = consumoEnergiaRepository.findById(id);
        if (consumoEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ConsumoEnergia consumoEnergiaAtualizado = consumoEnergiaMapper.dtoToEntity(consumoEnergiaDTO);
        consumoEnergiaAtualizado.setIdConsumo(id);
        ConsumoEnergia consumoEnergiaSalvoAtualizado = consumoEnergiaRepository.save(consumoEnergiaAtualizado);
        EntityModel<ConsumoEnergia> resource = EntityModel.of(consumoEnergiaSalvoAtualizado,
                linkTo(methodOn(ConsumoEnergiaController.class).getConsumoEnergiaById(consumoEnergiaSalvoAtualizado.getIdConsumo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um consumo de energia do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Consumo de Energia não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumo de Energia deletado com sucesso")
    })
    public ResponseEntity<Void> deleteConsumoEnergia(@PathVariable Long id) {
        Optional<ConsumoEnergia> consumoEnergiaSalvo = consumoEnergiaRepository.findById(id);
        if (consumoEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        consumoEnergiaRepository.delete(consumoEnergiaSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

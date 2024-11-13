package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.GeracaoEnergiaDTO;
import com.example.ecovoltjava.model.GeracaoEnergia;
import com.example.ecovoltjava.repository.GeracaoEnergiaRepository;
import com.example.ecovoltjava.service.mapper.GeracaoEnergiaMapper;
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
@RequestMapping(value = "/geracaoenergia", produces = "application/json")
@Tag(name = "api-geracao-energia")
public class GeracaoEnergiaController {

    @Autowired
    private GeracaoEnergiaRepository geracaoEnergiaRepository;

    @Autowired
    private GeracaoEnergiaMapper geracaoEnergiaMapper;

    @PostMapping
    @Operation(summary = "Cria uma geração de energia e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Geração de Energia cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<GeracaoEnergia>> criarGeracaoEnergia(@Valid @RequestBody GeracaoEnergiaDTO geracaoEnergiaDTO) {
        GeracaoEnergia geracaoEnergiaConvertido = geracaoEnergiaMapper.dtoToEntity(geracaoEnergiaDTO);
        GeracaoEnergia geracaoEnergiaCriado = geracaoEnergiaRepository.save(geracaoEnergiaConvertido);
        EntityModel<GeracaoEnergia> resource = EntityModel.of(geracaoEnergiaCriado,
                linkTo(methodOn(GeracaoEnergiaController.class).getGeracaoEnergiaById(geracaoEnergiaCriado.getIdGeracao())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todas as gerações de energia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma geração de energia encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Gerações de Energia retornadas com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<GeracaoEnergia>>> getAllGeracoesEnergia(@PageableDefault(size = 10) Pageable pageable) {
        Page<GeracaoEnergia> paginaGeracoesEnergia = geracaoEnergiaRepository.findAll(pageable);
        if (paginaGeracoesEnergia.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<GeracaoEnergia>> geracoesEnergiaComLinks = paginaGeracoesEnergia.stream()
                .map(geracaoEnergia -> EntityModel.of(geracaoEnergia,
                        linkTo(methodOn(GeracaoEnergiaController.class).getGeracaoEnergiaById(geracaoEnergia.getIdGeracao())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<GeracaoEnergia>> collectionModel = CollectionModel.of(geracoesEnergiaComLinks,
                linkTo(methodOn(GeracaoEnergiaController.class).getAllGeracoesEnergia(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma geração de energia pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Geração de Energia não encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Geração de Energia encontrada com sucesso")
    })
    public ResponseEntity<EntityModel<GeracaoEnergia>> getGeracaoEnergiaById(@PathVariable Long id) {
        Optional<GeracaoEnergia> geracaoEnergiaSalvo = geracaoEnergiaRepository.findById(id);
        if (geracaoEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        GeracaoEnergia geracaoEnergia = geracaoEnergiaSalvo.get();
        EntityModel<GeracaoEnergia> resource = EntityModel.of(geracaoEnergia,
                linkTo(methodOn(GeracaoEnergiaController.class).getGeracaoEnergiaById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma geração de energia existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Geração de Energia não encontrada ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Geração de Energia atualizada com sucesso")
    })
    public ResponseEntity<EntityModel<GeracaoEnergia>> updateGeracaoEnergia(@PathVariable Long id, @Valid @RequestBody GeracaoEnergiaDTO geracaoEnergiaDTO) {
        Optional<GeracaoEnergia> geracaoEnergiaSalvo = geracaoEnergiaRepository.findById(id);
        if (geracaoEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        GeracaoEnergia geracaoEnergiaAtualizado = geracaoEnergiaMapper.dtoToEntity(geracaoEnergiaDTO);
        geracaoEnergiaAtualizado.setIdGeracao(id);
        GeracaoEnergia geracaoEnergiaSalvoAtualizado = geracaoEnergiaRepository.save(geracaoEnergiaAtualizado);
        EntityModel<GeracaoEnergia> resource = EntityModel.of(geracaoEnergiaSalvoAtualizado,
                linkTo(methodOn(GeracaoEnergiaController.class).getGeracaoEnergiaById(geracaoEnergiaSalvoAtualizado.getIdGeracao())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma geração de energia do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Geração de Energia não encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Geração de Energia deletada com sucesso")
    })
    public ResponseEntity<Void> deleteGeracaoEnergia(@PathVariable Long id) {
        Optional<GeracaoEnergia> geracaoEnergiaSalvo = geracaoEnergiaRepository.findById(id);
        if (geracaoEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        geracaoEnergiaRepository.delete(geracaoEnergiaSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

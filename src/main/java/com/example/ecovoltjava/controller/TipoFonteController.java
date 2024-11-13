package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.TipoFonteDTO;
import com.example.ecovoltjava.model.TipoFonte;
import com.example.ecovoltjava.repository.TipoFonteRepository;
import com.example.ecovoltjava.service.mapper.TipoFonteMapper;
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
@RequestMapping(value = "/tipofonte", produces = "application/json")
@Tag(name = "api-tipo-fonte")
public class TipoFonteController {

    @Autowired
    private TipoFonteRepository tipoFonteRepository;

    @Autowired
    private TipoFonteMapper tipoFonteMapper;

    @PostMapping
    @Operation(summary = "Cria um tipo de fonte e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de fonte cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<TipoFonte>> criarTipoFonte(@Valid @RequestBody TipoFonteDTO tipoFonteDTO) {
        TipoFonte tipoFonteConvertido = tipoFonteMapper.dtoToEntity(tipoFonteDTO);
        TipoFonte tipoFonteCriado = tipoFonteRepository.save(tipoFonteConvertido);
        EntityModel<TipoFonte> resource = EntityModel.of(tipoFonteCriado,
                linkTo(methodOn(TipoFonteController.class).getTipoFonteById(tipoFonteCriado.getIdTipoFonte())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os tipos de fonte cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum tipo de fonte encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipos de fonte retornados com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<TipoFonte>>> getAllTiposFonte(@PageableDefault(size = 10) Pageable pageable) {
        Page<TipoFonte> paginaTiposFonte = tipoFonteRepository.findAll(pageable);
        if (paginaTiposFonte.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<TipoFonte>> tiposFonteComLinks = paginaTiposFonte.stream()
                .map(tipoFonte -> EntityModel.of(tipoFonte,
                        linkTo(methodOn(TipoFonteController.class).getTipoFonteById(tipoFonte.getIdTipoFonte())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<TipoFonte>> collectionModel = CollectionModel.of(tiposFonteComLinks,
                linkTo(methodOn(TipoFonteController.class).getAllTiposFonte(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um tipo de fonte pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de fonte não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de fonte encontrado com sucesso")
    })
    public ResponseEntity<EntityModel<TipoFonte>> getTipoFonteById(@PathVariable Long id) {
        Optional<TipoFonte> tipoFonteSalvo = tipoFonteRepository.findById(id);
        if (tipoFonteSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        TipoFonte tipoFonte = tipoFonteSalvo.get();
        EntityModel<TipoFonte> resource = EntityModel.of(tipoFonte,
                linkTo(methodOn(TipoFonteController.class).getTipoFonteById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um tipo de fonte existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Tipo de fonte não encontrado ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de fonte atualizado com sucesso")
    })
    public ResponseEntity<EntityModel<TipoFonte>> updateTipoFonte(@PathVariable Long id, @Valid @RequestBody TipoFonteDTO tipoFonteDTO) {
        Optional<TipoFonte> tipoFonteSalvo = tipoFonteRepository.findById(id);
        if (tipoFonteSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TipoFonte tipoFonteAtualizado = tipoFonteMapper.dtoToEntity(tipoFonteDTO);
        tipoFonteAtualizado.setIdTipoFonte(id);
        TipoFonte tipoFonteSalvoAtualizado = tipoFonteRepository.save(tipoFonteAtualizado);
        EntityModel<TipoFonte> resource = EntityModel.of(tipoFonteSalvoAtualizado,
                linkTo(methodOn(TipoFonteController.class).getTipoFonteById(tipoFonteSalvoAtualizado.getIdTipoFonte())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um tipo de fonte do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Tipo de fonte não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de fonte deletado com sucesso")
    })
    public ResponseEntity<Void> deleteTipoFonte(@PathVariable Long id) {
        Optional<TipoFonte> tipoFonteSalvo = tipoFonteRepository.findById(id);
        if (tipoFonteSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        tipoFonteRepository.delete(tipoFonteSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

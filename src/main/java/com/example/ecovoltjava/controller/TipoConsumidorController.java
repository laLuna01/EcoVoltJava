package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.TipoConsumidorDTO;
import com.example.ecovoltjava.model.TipoConsumidor;
import com.example.ecovoltjava.repository.TipoConsumidorRepository;
import com.example.ecovoltjava.service.mapper.TipoConsumidorMapper;
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
@RequestMapping(value = "/tipoconsumidor", produces = "application/json")
@Tag(name = "api-tipo-consumidor")
public class TipoConsumidorController {

    @Autowired
    private TipoConsumidorRepository tipoConsumidorRepository;

    @Autowired
    private TipoConsumidorMapper tipoConsumidorMapper;

    @PostMapping
    @Operation(summary = "Cria um tipo de consumidor e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de consumidor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<TipoConsumidor>> criarTipoConsumidor(@Valid @RequestBody TipoConsumidorDTO tipoConsumidorDTO) {
        TipoConsumidor tipoConsumidorConvertido = tipoConsumidorMapper.dtoToEntity(tipoConsumidorDTO);
        TipoConsumidor tipoConsumidorCriado = tipoConsumidorRepository.save(tipoConsumidorConvertido);
        EntityModel<TipoConsumidor> resource = EntityModel.of(tipoConsumidorCriado,
                linkTo(methodOn(TipoConsumidorController.class).getTipoConsumidorById(tipoConsumidorCriado.getIdTipoConsumidor())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os tipos de consumidor cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum tipo de consumidor encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipos de consumidor retornados com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<TipoConsumidor>>> getAllTiposConsumidor(@PageableDefault(size = 10) Pageable pageable) {
        Page<TipoConsumidor> paginaTiposConsumidor = tipoConsumidorRepository.findAll(pageable);
        if (paginaTiposConsumidor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<TipoConsumidor>> tiposConsumidorComLinks = paginaTiposConsumidor.stream()
                .map(tipoConsumidor -> EntityModel.of(tipoConsumidor,
                        linkTo(methodOn(TipoConsumidorController.class).getTipoConsumidorById(tipoConsumidor.getIdTipoConsumidor())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<TipoConsumidor>> collectionModel = CollectionModel.of(tiposConsumidorComLinks,
                linkTo(methodOn(TipoConsumidorController.class).getAllTiposConsumidor(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um tipo de consumidor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de consumidor não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de consumidor encontrado com sucesso")
    })
    public ResponseEntity<EntityModel<TipoConsumidor>> getTipoConsumidorById(@PathVariable Long id) {
        Optional<TipoConsumidor> tipoConsumidorSalvo = tipoConsumidorRepository.findById(id);
        if (tipoConsumidorSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        TipoConsumidor tipoConsumidor = tipoConsumidorSalvo.get();
        EntityModel<TipoConsumidor> resource = EntityModel.of(tipoConsumidor,
                linkTo(methodOn(TipoConsumidorController.class).getTipoConsumidorById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um tipo de consumidor existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Tipo de consumidor não encontrado ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de consumidor atualizado com sucesso")
    })
    public ResponseEntity<EntityModel<TipoConsumidor>> updateTipoConsumidor(@PathVariable Long id, @Valid @RequestBody TipoConsumidorDTO tipoConsumidorDTO) {
        Optional<TipoConsumidor> tipoConsumidorSalvo = tipoConsumidorRepository.findById(id);
        if (tipoConsumidorSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TipoConsumidor tipoConsumidorAtualizado = tipoConsumidorMapper.dtoToEntity(tipoConsumidorDTO);
        tipoConsumidorAtualizado.setIdTipoConsumidor(id);
        TipoConsumidor tipoConsumidorSalvoAtualizado = tipoConsumidorRepository.save(tipoConsumidorAtualizado);
        EntityModel<TipoConsumidor> resource = EntityModel.of(tipoConsumidorSalvoAtualizado,
                linkTo(methodOn(TipoConsumidorController.class).getTipoConsumidorById(tipoConsumidorSalvoAtualizado.getIdTipoConsumidor())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um tipo de consumidor do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Tipo de consumidor não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de consumidor deletado com sucesso")
    })
    public ResponseEntity<Void> deleteTipoConsumidor(@PathVariable Long id) {
        Optional<TipoConsumidor> tipoConsumidorSalvo = tipoConsumidorRepository.findById(id);
        if (tipoConsumidorSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        tipoConsumidorRepository.delete(tipoConsumidorSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

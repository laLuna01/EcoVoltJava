package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.TipoDispositivoDTO;
import com.example.ecovoltjava.model.TipoDispositivo;
import com.example.ecovoltjava.repository.TipoDispositivoRepository;
import com.example.ecovoltjava.service.mapper.TipoDispositivoMapper;
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
@RequestMapping(value = "/tipodispositivo", produces = "application/json")
@Tag(name = "api-tipo-dispositivo")
public class TipoDispositivoController {

    @Autowired
    private TipoDispositivoRepository tipoDispositivoRepository;

    @Autowired
    private TipoDispositivoMapper tipoDispositivoMapper;

    @PostMapping
    @Operation(summary = "Cria um tipo de dispositivo e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de dispositivo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<TipoDispositivo>> criarTipoDispositivo(@Valid @RequestBody TipoDispositivoDTO tipoDispositivoDTO) {
        TipoDispositivo tipoDispositivoConvertido = tipoDispositivoMapper.dtoToEntity(tipoDispositivoDTO);
        TipoDispositivo tipoDispositivoCriado = tipoDispositivoRepository.save(tipoDispositivoConvertido);
        EntityModel<TipoDispositivo> resource = EntityModel.of(tipoDispositivoCriado,
                linkTo(methodOn(TipoDispositivoController.class).getTipoDispositivoById(tipoDispositivoCriado.getIdTipoDispositivo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os tipos de dispositivo cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum tipo de dispositivo encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipos de dispositivo retornados com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<TipoDispositivo>>> getAllTiposDispositivo(@PageableDefault(size = 10) Pageable pageable) {
        Page<TipoDispositivo> paginaTiposDispositivo = tipoDispositivoRepository.findAll(pageable);
        if (paginaTiposDispositivo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<TipoDispositivo>> tiposDispositivoComLinks = paginaTiposDispositivo.stream()
                .map(tipoDispositivo -> EntityModel.of(tipoDispositivo,
                        linkTo(methodOn(TipoDispositivoController.class).getTipoDispositivoById(tipoDispositivo.getIdTipoDispositivo())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<TipoDispositivo>> collectionModel = CollectionModel.of(tiposDispositivoComLinks,
                linkTo(methodOn(TipoDispositivoController.class).getAllTiposDispositivo(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um tipo de dispositivo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de dispositivo não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de dispositivo encontrado com sucesso")
    })
    public ResponseEntity<EntityModel<TipoDispositivo>> getTipoDispositivoById(@PathVariable Long id) {
        Optional<TipoDispositivo> tipoDispositivoSalvo = tipoDispositivoRepository.findById(id);
        if (tipoDispositivoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        TipoDispositivo tipoDispositivo = tipoDispositivoSalvo.get();
        EntityModel<TipoDispositivo> resource = EntityModel.of(tipoDispositivo,
                linkTo(methodOn(TipoDispositivoController.class).getTipoDispositivoById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um tipo de dispositivo existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Tipo de dispositivo não encontrado ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de dispositivo atualizado com sucesso")
    })
    public ResponseEntity<EntityModel<TipoDispositivo>> updateTipoDispositivo(@PathVariable Long id, @Valid @RequestBody TipoDispositivoDTO tipoDispositivoDTO) {
        Optional<TipoDispositivo> tipoDispositivoSalvo = tipoDispositivoRepository.findById(id);
        if (tipoDispositivoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TipoDispositivo tipoDispositivoAtualizado = tipoDispositivoMapper.dtoToEntity(tipoDispositivoDTO);
        tipoDispositivoAtualizado.setIdTipoDispositivo(id);
        TipoDispositivo tipoDispositivoSalvoAtualizado = tipoDispositivoRepository.save(tipoDispositivoAtualizado);
        EntityModel<TipoDispositivo> resource = EntityModel.of(tipoDispositivoSalvoAtualizado,
                linkTo(methodOn(TipoDispositivoController.class).getTipoDispositivoById(tipoDispositivoSalvoAtualizado.getIdTipoDispositivo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um tipo de dispositivo do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Tipo de dispositivo não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Tipo de dispositivo deletado com sucesso")
    })
    public ResponseEntity<Void> deleteTipoDispositivo(@PathVariable Long id) {
        Optional<TipoDispositivo> tipoDispositivoSalvo = tipoDispositivoRepository.findById(id);
        if (tipoDispositivoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        tipoDispositivoRepository.delete(tipoDispositivoSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

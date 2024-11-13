package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.DispositivoDTO;
import com.example.ecovoltjava.model.Dispositivo;
import com.example.ecovoltjava.repository.DispositivoRepository;
import com.example.ecovoltjava.service.mapper.DispositivoMapper;
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
@RequestMapping(value = "/dispositivo", produces = "application/json")
@Tag(name = "api-dispositivo")
public class DispositivoController {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    @PostMapping
    @Operation(summary = "Cria um dispositivo e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispositivo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<Dispositivo>> criarDispositivo(@Valid @RequestBody DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivoConvertido = dispositivoMapper.dtoToEntity(dispositivoDTO);
        Dispositivo dispositivoCriado = dispositivoRepository.save(dispositivoConvertido);
        EntityModel<Dispositivo> resource = EntityModel.of(dispositivoCriado,
                linkTo(methodOn(DispositivoController.class).getDispositivoById(dispositivoCriado.getIdDispositivo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os dispositivos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum dispositivo encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Dispositivos retornados com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<Dispositivo>>> getAllDispositivos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Dispositivo> paginaDispositivos = dispositivoRepository.findAll(pageable);
        if (paginaDispositivos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<Dispositivo>> dispositivosComLinks = paginaDispositivos.stream()
                .map(dispositivo -> EntityModel.of(dispositivo,
                        linkTo(methodOn(DispositivoController.class).getDispositivoById(dispositivo.getIdDispositivo())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Dispositivo>> collectionModel = CollectionModel.of(dispositivosComLinks,
                linkTo(methodOn(DispositivoController.class).getAllDispositivos(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um dispositivo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dispositivo não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Dispositivo encontrado com sucesso")
    })
    public ResponseEntity<EntityModel<Dispositivo>> getDispositivoById(@PathVariable Long id) {
        Optional<Dispositivo> dispositivoSalvo = dispositivoRepository.findById(id);
        if (dispositivoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Dispositivo dispositivo = dispositivoSalvo.get();
        EntityModel<Dispositivo> resource = EntityModel.of(dispositivo,
                linkTo(methodOn(DispositivoController.class).getDispositivoById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um dispositivo existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Dispositivo não encontrado ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso")
    })
    public ResponseEntity<EntityModel<Dispositivo>> updateDispositivo(@PathVariable Long id, @Valid @RequestBody DispositivoDTO dispositivoDTO) {
        Optional<Dispositivo> dispositivoSalvo = dispositivoRepository.findById(id);
        if (dispositivoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Dispositivo dispositivoAtualizado = dispositivoMapper.dtoToEntity(dispositivoDTO);
        dispositivoAtualizado.setIdDispositivo(id);
        Dispositivo dispositivoSalvoAtualizado = dispositivoRepository.save(dispositivoAtualizado);
        EntityModel<Dispositivo> resource = EntityModel.of(dispositivoSalvoAtualizado,
                linkTo(methodOn(DispositivoController.class).getDispositivoById(dispositivoSalvoAtualizado.getIdDispositivo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um dispositivo do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Dispositivo não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Dispositivo deletado com sucesso")
    })
    public ResponseEntity<Void> deleteDispositivo(@PathVariable Long id) {
        Optional<Dispositivo> dispositivoSalvo = dispositivoRepository.findById(id);
        if (dispositivoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        dispositivoRepository.delete(dispositivoSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
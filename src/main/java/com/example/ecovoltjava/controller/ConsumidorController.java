package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.ConsumidorDTO;
import com.example.ecovoltjava.model.Consumidor;
import com.example.ecovoltjava.repository.ConsumidorRepository;
import com.example.ecovoltjava.service.mapper.ConsumidorMapper;
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
@RequestMapping(value = "/consumidor", produces = "application/json")
@Tag(name = "api-consumidor")
public class ConsumidorController {

    @Autowired
    private ConsumidorRepository consumidorRepository;

    @Autowired
    private ConsumidorMapper consumidorMapper;

    @PostMapping
    @Operation(summary = "Cria um consumidor e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumidor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<Consumidor>> criarConsumidor(@Valid @RequestBody ConsumidorDTO consumidorDTO) {
        Consumidor consumidorConvertido = consumidorMapper.dtoToEntity(consumidorDTO);
        Consumidor consumidorCriado = consumidorRepository.save(consumidorConvertido);
        EntityModel<Consumidor> resource = EntityModel.of(consumidorCriado,
                linkTo(methodOn(ConsumidorController.class).getConsumidorById(consumidorCriado.getIdConsumidor())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os consumidores cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum consumidor encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumidores retornados com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<Consumidor>>> getAllConsumidores(@PageableDefault(size = 10) Pageable pageable) {
        Page<Consumidor> paginaConsumidores = consumidorRepository.findAll(pageable);
        if (paginaConsumidores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<Consumidor>> consumidoresComLinks = paginaConsumidores.stream()
                .map(consumidor -> EntityModel.of(consumidor,
                        linkTo(methodOn(ConsumidorController.class).getConsumidorById(consumidor.getIdConsumidor())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Consumidor>> collectionModel = CollectionModel.of(consumidoresComLinks,
                linkTo(methodOn(ConsumidorController.class).getAllConsumidores(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um consumidor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consumidor não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumidor encontrado com sucesso")
    })
    public ResponseEntity<EntityModel<Consumidor>> getConsumidorById(@PathVariable Long id) {
        Optional<Consumidor> consumidorSalvo = consumidorRepository.findById(id);
        if (consumidorSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Consumidor consumidor = consumidorSalvo.get();
        EntityModel<Consumidor> resource = EntityModel.of(consumidor,
                linkTo(methodOn(ConsumidorController.class).getConsumidorById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um consumidor existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Consumidor não encontrado ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumidor atualizado com sucesso")
    })
    public ResponseEntity<EntityModel<Consumidor>> updateConsumidor(@PathVariable Long id, @Valid @RequestBody ConsumidorDTO consumidorDTO) {
        Optional<Consumidor> consumidorSalvo = consumidorRepository.findById(id);
        if (consumidorSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Consumidor consumidorAtualizado = consumidorMapper.dtoToEntity(consumidorDTO);
        consumidorAtualizado.setIdConsumidor(id);
        Consumidor consumidorSalvoAtualizado = consumidorRepository.save(consumidorAtualizado);
        EntityModel<Consumidor> resource = EntityModel.of(consumidorSalvoAtualizado,
                linkTo(methodOn(ConsumidorController.class).getConsumidorById(consumidorSalvoAtualizado.getIdConsumidor())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um consumidor do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Consumidor não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Consumidor deletado com sucesso")
    })
    public ResponseEntity<Void> deleteConsumidor(@PathVariable Long id) {
        Optional<Consumidor> consumidorSalvo = consumidorRepository.findById(id);
        if (consumidorSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        consumidorRepository.delete(consumidorSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

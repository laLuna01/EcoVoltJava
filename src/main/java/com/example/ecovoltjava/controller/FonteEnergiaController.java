package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.FonteEnergiaDTO;
import com.example.ecovoltjava.model.FonteEnergia;
import com.example.ecovoltjava.repository.FonteEnergiaRepository;
import com.example.ecovoltjava.service.mapper.FonteEnergiaMapper;
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
@RequestMapping(value = "/fonteenergia", produces = "application/json")
@Tag(name = "api-fonte-energia")
public class FonteEnergiaController {

    @Autowired
    private FonteEnergiaRepository fonteEnergiaRepository;

    @Autowired
    private FonteEnergiaMapper fonteEnergiaMapper;

    @PostMapping
    @Operation(summary = "Cria uma fonte de energia e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fonte de energia cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<FonteEnergia>> criarFonteEnergia(@Valid @RequestBody FonteEnergiaDTO fonteEnergiaDTO) {
        FonteEnergia fonteEnergiaConvertido = fonteEnergiaMapper.dtoToEntity(fonteEnergiaDTO);
        FonteEnergia fonteEnergiaCriado = fonteEnergiaRepository.save(fonteEnergiaConvertido);
        EntityModel<FonteEnergia> resource = EntityModel.of(fonteEnergiaCriado,
                linkTo(methodOn(FonteEnergiaController.class).getFonteEnergiaById(fonteEnergiaCriado.getIdFonte())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todas as fontes de energia cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma fonte de energia encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Fontes de energia retornadas com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<FonteEnergia>>> getAllFontesEnergia(@PageableDefault(size = 10) Pageable pageable) {
        Page<FonteEnergia> paginaFontesEnergia = fonteEnergiaRepository.findAll(pageable);
        if (paginaFontesEnergia.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<FonteEnergia>> fontesEnergiaComLinks = paginaFontesEnergia.stream()
                .map(fonteEnergia -> EntityModel.of(fonteEnergia,
                        linkTo(methodOn(FonteEnergiaController.class).getFonteEnergiaById(fonteEnergia.getIdFonte())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<FonteEnergia>> collectionModel = CollectionModel.of(fontesEnergiaComLinks,
                linkTo(methodOn(FonteEnergiaController.class).getAllFontesEnergia(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma fonte de energia pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fonte de energia não encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Fonte de energia encontrada com sucesso")
    })
    public ResponseEntity<EntityModel<FonteEnergia>> getFonteEnergiaById(@PathVariable Long id) {
        Optional<FonteEnergia> fonteEnergiaSalvo = fonteEnergiaRepository.findById(id);
        if (fonteEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        FonteEnergia fonteEnergia = fonteEnergiaSalvo.get();
        EntityModel<FonteEnergia> resource = EntityModel.of(fonteEnergia,
                linkTo(methodOn(FonteEnergiaController.class).getFonteEnergiaById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma fonte de energia existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Fonte de energia não encontrada ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Fonte de energia atualizada com sucesso")
    })
    public ResponseEntity<EntityModel<FonteEnergia>> updateFonteEnergia(@PathVariable Long id, @Valid @RequestBody FonteEnergiaDTO fonteEnergiaDTO) {
        Optional<FonteEnergia> fonteEnergiaSalvo = fonteEnergiaRepository.findById(id);
        if (fonteEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FonteEnergia fonteEnergiaAtualizado = fonteEnergiaMapper.dtoToEntity(fonteEnergiaDTO);
        fonteEnergiaAtualizado.setIdFonte(id);
        FonteEnergia fonteEnergiaSalvoAtualizado = fonteEnergiaRepository.save(fonteEnergiaAtualizado);
        EntityModel<FonteEnergia> resource = EntityModel.of(fonteEnergiaSalvoAtualizado,
                linkTo(methodOn(FonteEnergiaController.class).getFonteEnergiaById(fonteEnergiaSalvoAtualizado.getIdFonte())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma fonte de energia do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Fonte de energia não encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Fonte de energia deletada com sucesso")
    })
    public ResponseEntity<Void> deleteFonteEnergia(@PathVariable Long id) {
        Optional<FonteEnergia> fonteEnergiaSalvo = fonteEnergiaRepository.findById(id);
        if (fonteEnergiaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        fonteEnergiaRepository.delete(fonteEnergiaSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

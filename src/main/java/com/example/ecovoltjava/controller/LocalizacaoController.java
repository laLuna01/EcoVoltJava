package com.example.ecovoltjava.controller;

import com.example.ecovoltjava.dto.LocalizacaoDTO;
import com.example.ecovoltjava.model.Localizacao;
import com.example.ecovoltjava.repository.LocalizacaoRepository;
import com.example.ecovoltjava.service.mapper.LocalizacaoMapper;
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
@RequestMapping(value = "/localizacao", produces = "application/json")
@Tag(name = "api-localizacao")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private LocalizacaoMapper localizacaoMapper;

    @PostMapping
    @Operation(summary = "Cria uma localização e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Localização cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<EntityModel<Localizacao>> criarLocalizacao(@Valid @RequestBody LocalizacaoDTO localizacaoDTO) {
        Localizacao localizacaoConvertida = localizacaoMapper.dtoToEntity(localizacaoDTO);
        Localizacao localizacaoCriada = localizacaoRepository.save(localizacaoConvertida);
        EntityModel<Localizacao> resource = EntityModel.of(localizacaoCriada,
                linkTo(methodOn(LocalizacaoController.class).getLocalizacaoById(localizacaoCriada.getIdLocalizacao())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retorna todas as localizações cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhuma localização encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Localizações retornadas com sucesso")
    })
    public ResponseEntity<CollectionModel<EntityModel<Localizacao>>> getAllLocalizacoes(@PageableDefault(size = 10) Pageable pageable) {
        Page<Localizacao> paginaLocalizacoes = localizacaoRepository.findAll(pageable);
        if (paginaLocalizacoes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<Localizacao>> localizacoesComLinks = paginaLocalizacoes.stream()
                .map(localizacao -> EntityModel.of(localizacao,
                        linkTo(methodOn(LocalizacaoController.class).getLocalizacaoById(localizacao.getIdLocalizacao())).withSelfRel())
                )
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Localizacao>> collectionModel = CollectionModel.of(localizacoesComLinks,
                linkTo(methodOn(LocalizacaoController.class).getAllLocalizacoes(pageable)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma localização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Localização não encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Localização encontrada com sucesso")
    })
    public ResponseEntity<EntityModel<Localizacao>> getLocalizacaoById(@PathVariable Long id) {
        Optional<Localizacao> localizacaoSalva = localizacaoRepository.findById(id);
        if (localizacaoSalva.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Localizacao localizacao = localizacaoSalva.get();
        EntityModel<Localizacao> resource = EntityModel.of(localizacao,
                linkTo(methodOn(LocalizacaoController.class).getLocalizacaoById(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma localização existente no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Localização não encontrada ou atributos inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso")
    })
    public ResponseEntity<EntityModel<Localizacao>> updateLocalizacao(@PathVariable Long id, @Valid @RequestBody LocalizacaoDTO localizacaoDTO) {
        Optional<Localizacao> localizacaoSalva = localizacaoRepository.findById(id);
        if (localizacaoSalva.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Localizacao localizacaoAtualizada = localizacaoMapper.dtoToEntity(localizacaoDTO);
        localizacaoAtualizada.setIdLocalizacao(id);
        Localizacao localizacaoSalvaAtualizada = localizacaoRepository.save(localizacaoAtualizada);
        EntityModel<Localizacao> resource = EntityModel.of(localizacaoSalvaAtualizada,
                linkTo(methodOn(LocalizacaoController.class).getLocalizacaoById(localizacaoSalvaAtualizada.getIdLocalizacao())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma localização do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Localização não encontrada",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Localização deletada com sucesso")
    })
    public ResponseEntity<Void> deleteLocalizacao(@PathVariable Long id) {
        Optional<Localizacao> localizacaoSalva = localizacaoRepository.findById(id);
        if (localizacaoSalva.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        localizacaoRepository.delete(localizacaoSalva.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
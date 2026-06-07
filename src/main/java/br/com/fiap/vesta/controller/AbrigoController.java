package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.dto.request.AbrigoRequest;
import br.com.fiap.vesta.dto.response.AbrigoResponse;
import br.com.fiap.vesta.service.AbrigoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/abrigos")
@Tag(name = "Abrigos")
public class AbrigoController {

    private final AbrigoService abrigoService;

    public AbrigoController(AbrigoService abrigoService) {
        this.abrigoService = abrigoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os abrigos")
    public ResponseEntity<CollectionModel<EntityModel<AbrigoResponse>>> listar(
            @RequestParam(required = false) Long idRegiao) {
        List<AbrigoResponse> dados = idRegiao != null
            ? abrigoService.listarPorRegiao(idRegiao)
            : abrigoService.listarTodos();
        List<EntityModel<AbrigoResponse>> modelos = dados.stream()
            .map(a -> EntityModel.of(a,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AbrigoController.class).buscar(a.idAbrigo())).withSelfRel()))
            .toList();
        return ResponseEntity.ok(CollectionModel.of(modelos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AbrigoController.class).listar(null)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar abrigo por ID")
    public ResponseEntity<EntityModel<AbrigoResponse>> buscar(@PathVariable Long id) {
        AbrigoResponse resp = abrigoService.buscarResponsePorId(id);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AbrigoController.class).buscar(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AbrigoController.class).listar(null)).withRel("abrigos")));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
    @Operation(summary = "Criar abrigo")
    public ResponseEntity<AbrigoResponse> criar(@Valid @RequestBody AbrigoRequest request) {
        AbrigoResponse resp = abrigoService.criar(request);
        URI location = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(AbrigoController.class).buscar(resp.idAbrigo())).toUri();
        return ResponseEntity.created(location).body(resp);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
    @Operation(summary = "Atualizar abrigo")
    public ResponseEntity<AbrigoResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody AbrigoRequest request) {
        return ResponseEntity.ok(abrigoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
    @Operation(summary = "Atualizar status do abrigo")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id,
                                                 @RequestParam StatusAbrigo status) {
        abrigoService.atualizarStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}

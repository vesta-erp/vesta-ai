package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import br.com.fiap.vesta.dto.request.OcorrenciaRequest;
import br.com.fiap.vesta.dto.response.OcorrenciaResponse;
import br.com.fiap.vesta.repository.UsuarioRepository;
import br.com.fiap.vesta.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/abrigos/{idAbrigo}/ocorrencias")
@Tag(name = "Ocorrências")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;
    private final UsuarioRepository usuarioRepository;

    public OcorrenciaController(OcorrenciaService ocorrenciaService,
                                 UsuarioRepository usuarioRepository) {
        this.ocorrenciaService = ocorrenciaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar ocorrências do abrigo")
    public ResponseEntity<CollectionModel<EntityModel<OcorrenciaResponse>>> listar(@PathVariable Long idAbrigo) {
        List<EntityModel<OcorrenciaResponse>> modelos = ocorrenciaService.listarPorAbrigo(idAbrigo).stream()
            .map(o -> EntityModel.of(o,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class)
                    .buscar(idAbrigo, o.idOcorrencia())).withSelfRel()))
            .toList();
        return ResponseEntity.ok(CollectionModel.of(modelos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class)
                .listar(idAbrigo)).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OcorrenciaResponse>> buscar(@PathVariable Long idAbrigo, @PathVariable Long id) {
        OcorrenciaResponse resp = ocorrenciaService.buscarPorId(id);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class)
                .buscar(idAbrigo, id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class)
                .listar(idAbrigo)).withRel("ocorrencias")));
    }

    @PostMapping
    @Operation(summary = "Registrar ocorrência")
    public ResponseEntity<EntityModel<OcorrenciaResponse>> criar(@PathVariable Long idAbrigo,
                                                                  @Valid @RequestBody OcorrenciaRequest request,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Long idUsuario = usuarioRepository.findByDsEmail(userDetails.getUsername())
            .map(u -> u.getIdUsuario()).orElseThrow();
        OcorrenciaResponse resp = ocorrenciaService.criar(idAbrigo, idUsuario, request);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class)
                .buscar(idAbrigo, resp.idOcorrencia())).withSelfRel()));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status de ocorrência")
    public ResponseEntity<EntityModel<OcorrenciaResponse>> atualizarStatus(@PathVariable Long idAbrigo,
                                                                             @PathVariable Long id,
                                                                             @RequestParam StatusOcorrencia status) {
        OcorrenciaResponse resp = ocorrenciaService.atualizarStatus(id, status);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class)
                .buscar(idAbrigo, id)).withSelfRel()));
    }
}

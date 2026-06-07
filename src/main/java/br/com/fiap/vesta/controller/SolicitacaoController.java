package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.request.AtualizacaoSolicitacaoRequest;
import br.com.fiap.vesta.dto.request.SolicitacaoRequest;
import br.com.fiap.vesta.dto.response.SolicitacaoResponse;
import br.com.fiap.vesta.repository.UsuarioRepository;
import br.com.fiap.vesta.service.SolicitacaoService;
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
@Tag(name = "Solicitações de Recursos")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoController(SolicitacaoService solicitacaoService,
                                  UsuarioRepository usuarioRepository) {
        this.solicitacaoService = solicitacaoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/api/abrigos/{idAbrigo}/solicitacoes")
    @Operation(summary = "Listar solicitações do abrigo")
    public ResponseEntity<CollectionModel<EntityModel<SolicitacaoResponse>>> listarPorAbrigo(@PathVariable Long idAbrigo) {
        List<EntityModel<SolicitacaoResponse>> modelos = solicitacaoService.listarPorAbrigo(idAbrigo).stream()
            .map(s -> EntityModel.of(s,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                    .buscar(s.idSolicitacao())).withSelfRel()))
            .toList();
        return ResponseEntity.ok(CollectionModel.of(modelos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                .listarPorAbrigo(idAbrigo)).withSelfRel()));
    }

    @GetMapping("/api/solicitacoes")
    @Operation(summary = "Listar todas as solicitações abertas")
    public ResponseEntity<CollectionModel<EntityModel<SolicitacaoResponse>>> listarAbertas() {
        List<EntityModel<SolicitacaoResponse>> modelos = solicitacaoService.listarAbertas().stream()
            .map(s -> EntityModel.of(s,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                    .buscar(s.idSolicitacao())).withSelfRel()))
            .toList();
        return ResponseEntity.ok(CollectionModel.of(modelos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                .listarAbertas()).withSelfRel()));
    }

    @GetMapping("/api/solicitacoes/{id}")
    public ResponseEntity<EntityModel<SolicitacaoResponse>> buscar(@PathVariable Long id) {
        SolicitacaoResponse resp = solicitacaoService.buscarPorId(id);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                .buscar(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                .listarAbertas()).withRel("solicitacoes")));
    }

    @PostMapping("/api/abrigos/{idAbrigo}/solicitacoes")
    @Operation(summary = "Abrir solicitação de recurso")
    public ResponseEntity<EntityModel<SolicitacaoResponse>> abrir(@PathVariable Long idAbrigo,
                                                                    @Valid @RequestBody SolicitacaoRequest request,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        Long idUsuario = usuarioRepository.findByDsEmail(userDetails.getUsername())
            .map(u -> u.getIdUsuario()).orElseThrow();
        SolicitacaoResponse resp = solicitacaoService.abrir(idAbrigo, idUsuario, request);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                .buscar(resp.idSolicitacao())).withSelfRel()));
    }

    @PatchMapping("/api/solicitacoes/{id}/status")
    @Operation(summary = "Avançar status da solicitação (workflow controlado)")
    public ResponseEntity<EntityModel<SolicitacaoResponse>> atualizarStatus(@PathVariable Long id,
                                                                              @Valid @RequestBody AtualizacaoSolicitacaoRequest req) {
        SolicitacaoResponse resp = solicitacaoService.atualizarStatus(id, req);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SolicitacaoController.class)
                .buscar(id)).withSelfRel()));
    }
}

package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.request.AcolhimentoRequest;
import br.com.fiap.vesta.dto.response.FamiliaResponse;
import br.com.fiap.vesta.dto.response.PessoaAbrigadaResponse;
import br.com.fiap.vesta.service.FamiliaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/abrigos/{idAbrigo}/familias")
@Tag(name = "Famílias e Acolhimento")
public class FamiliaController {

    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping
    @Operation(summary = "Listar famílias presentes no abrigo")
    public ResponseEntity<CollectionModel<EntityModel<FamiliaResponse>>> listar(@PathVariable Long idAbrigo) {
        List<EntityModel<FamiliaResponse>> modelos = familiaService.listarPorAbrigo(idAbrigo).stream()
            .map(f -> EntityModel.of(f,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                    .buscar(idAbrigo, f.idFamilia())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                    .listarPessoas(idAbrigo, f.idFamilia())).withRel("pessoas")))
            .toList();
        return ResponseEntity.ok(CollectionModel.of(modelos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                .listar(idAbrigo)).withSelfRel()));
    }

    @GetMapping("/{idFamilia}")
    @Operation(summary = "Buscar família por ID")
    public ResponseEntity<EntityModel<FamiliaResponse>> buscar(@PathVariable Long idAbrigo,
                                                                @PathVariable Long idFamilia) {
        FamiliaResponse resp = familiaService.buscarPorId(idFamilia);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                .buscar(idAbrigo, idFamilia)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                .listar(idAbrigo)).withRel("familias"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                .listarPessoas(idAbrigo, idFamilia)).withRel("pessoas")));
    }

    @GetMapping("/{idFamilia}/pessoas")
    @Operation(summary = "Listar pessoas de uma família")
    public ResponseEntity<List<PessoaAbrigadaResponse>> listarPessoas(@PathVariable Long idAbrigo,
                                                                        @PathVariable Long idFamilia) {
        return ResponseEntity.ok(familiaService.listarPessoasDaFamilia(idFamilia));
    }

    @PostMapping("/acolhimento")
    @Operation(summary = "Registrar acolhimento de família")
    public ResponseEntity<EntityModel<FamiliaResponse>> acolher(@PathVariable Long idAbrigo,
                                                                  @Valid @RequestBody AcolhimentoRequest request) {
        FamiliaResponse resp = familiaService.registrarAcolhimento(idAbrigo, request);
        return ResponseEntity.ok(EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FamiliaController.class)
                .buscar(idAbrigo, resp.idFamilia())).withSelfRel()));
    }

    @PostMapping("/{idFamilia}/saida")
    @Operation(summary = "Registrar saída de família")
    public ResponseEntity<Void> saida(@PathVariable Long idAbrigo,
                                       @PathVariable Long idFamilia) {
        familiaService.registrarSaida(idFamilia);
        return ResponseEntity.noContent().build();
    }
}

package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.response.IndicadorAbrigoResponse;
import br.com.fiap.vesta.service.IndicadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/indicadores")
@Tag(name = "Indicadores e Criticidade")
public class IndicadorController {

    private final IndicadorService indicadorService;

    public IndicadorController(IndicadorService indicadorService) {
        this.indicadorService = indicadorService;
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
    @Operation(summary = "Ranking de abrigos por criticidade (maior primeiro)")
    public ResponseEntity<List<IndicadorAbrigoResponse>> ranking() {
        return ResponseEntity.ok(indicadorService.rankingCriticidade());
    }

    @GetMapping("/abrigo/{id}")
    @Operation(summary = "Indicador de criticidade de um abrigo específico")
    public ResponseEntity<IndicadorAbrigoResponse> porAbrigo(@PathVariable Long id) {
        return ResponseEntity.ok(indicadorService.indicadorPorAbrigo(id));
    }
}

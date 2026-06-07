package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.response.AlertaResponse;
import br.com.fiap.vesta.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alertas ativos")
    public ResponseEntity<List<AlertaResponse>> listarAtivos() {
        return ResponseEntity.ok(alertaService.listarAtivos());
    }

    @GetMapping("/abrigo/{idAbrigo}")
    @Operation(summary = "Listar alertas ativos de um abrigo")
    public ResponseEntity<List<AlertaResponse>> listarPorAbrigo(@PathVariable Long idAbrigo) {
        return ResponseEntity.ok(alertaService.listarPorAbrigo(idAbrigo));
    }

    @PatchMapping("/{id}/resolver")
    @Operation(summary = "Marcar alerta como resolvido")
    public ResponseEntity<AlertaResponse> resolver(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.resolver(id));
    }
}

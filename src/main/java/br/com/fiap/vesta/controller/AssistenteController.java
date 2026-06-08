package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.request.AssistentePerguntaRequest;
import br.com.fiap.vesta.dto.response.AssistenteRespostaResponse;
import br.com.fiap.vesta.service.AssistenteOperacionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistente")
@Tag(name = "Assistente Operacional IA")
public class AssistenteController {

    private final AssistenteOperacionalService assistenteService;

    public AssistenteController(AssistenteOperacionalService assistenteService) {
        this.assistenteService = assistenteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
    @Operation(summary = "Fazer uma pergunta ao assistente operacional da Vesta")
    public ResponseEntity<AssistenteRespostaResponse> perguntar(
            @RequestBody @Valid AssistentePerguntaRequest request) {
        return ResponseEntity.ok(assistenteService.responder(request));
    }

    @GetMapping("/health")
    @Operation(summary = "Verificar disponibilidade do assistente")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Assistente operacional Vesta disponível");
    }
}

package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.request.TransferenciaRequest;
import br.com.fiap.vesta.dto.response.TransferenciaResponse;
import br.com.fiap.vesta.repository.UsuarioRepository;
import br.com.fiap.vesta.service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
@Tag(name = "Transferências")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;
    private final UsuarioRepository usuarioRepository;

    public TransferenciaController(TransferenciaService transferenciaService,
                                    UsuarioRepository usuarioRepository) {
        this.transferenciaService = transferenciaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/abrigo/{idAbrigo}")
    @Operation(summary = "Listar transferências de um abrigo (origem ou destino)")
    public ResponseEntity<List<TransferenciaResponse>> listar(@PathVariable Long idAbrigo) {
        return ResponseEntity.ok(transferenciaService.listarPorAbrigo(idAbrigo));
    }

    @PostMapping("/abrigo/{idAbrigo}")
    @Operation(summary = "Solicitar transferência de família")
    public ResponseEntity<TransferenciaResponse> solicitar(@PathVariable Long idAbrigo,
                                                            @Valid @RequestBody TransferenciaRequest req,
                                                            @AuthenticationPrincipal UserDetails user) {
        Long idUsuario = usuarioRepository.findByDsEmail(user.getUsername())
            .map(u -> u.getIdUsuario()).orElseThrow();
        return ResponseEntity.ok(transferenciaService.solicitar(idAbrigo, idUsuario, req));
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar transferência pendente")
    public ResponseEntity<TransferenciaResponse> aprovar(@PathVariable Long id) {
        return ResponseEntity.ok(transferenciaService.aprovar(id));
    }

    @PatchMapping("/{id}/concluir")
    @Operation(summary = "Concluir transferência aprovada (movimenta família e ajusta ocupação)")
    public ResponseEntity<TransferenciaResponse> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(transferenciaService.concluir(id));
    }
}

package br.com.fiap.vesta.controller;

import br.com.fiap.vesta.dto.request.EstoqueMinRequest;
import br.com.fiap.vesta.dto.request.MovimentacaoRequest;
import br.com.fiap.vesta.dto.response.EstoqueResponse;
import br.com.fiap.vesta.dto.response.MovimentacaoResponse;
import br.com.fiap.vesta.repository.UsuarioRepository;
import br.com.fiap.vesta.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/abrigos/{idAbrigo}/estoque")
@Tag(name = "Estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final UsuarioRepository usuarioRepository;

    public EstoqueController(EstoqueService estoqueService,
                              UsuarioRepository usuarioRepository) {
        this.estoqueService = estoqueService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar estoque do abrigo")
    public ResponseEntity<List<EstoqueResponse>> listar(@PathVariable Long idAbrigo) {
        return ResponseEntity.ok(estoqueService.listarPorAbrigo(idAbrigo));
    }

    @GetMapping("/criticos")
    @Operation(summary = "Listar itens abaixo do estoque mínimo")
    public ResponseEntity<List<EstoqueResponse>> criticos(@PathVariable Long idAbrigo) {
        return ResponseEntity.ok(estoqueService.listarAbaixoMinimo(idAbrigo));
    }

    @PostMapping("/movimentacao")
    @Operation(summary = "Registrar entrada, saída ou ajuste de estoque")
    public ResponseEntity<MovimentacaoResponse> movimentar(@PathVariable Long idAbrigo,
                                                            @Valid @RequestBody MovimentacaoRequest request,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        Long idUsuario = usuarioRepository.findByDsEmail(userDetails.getUsername())
            .map(u -> u.getIdUsuario())
            .orElseThrow();
        return ResponseEntity.ok(estoqueService.registrarMovimentacao(idAbrigo, idUsuario, request));
    }

    @PatchMapping("/minimo")
    @Operation(summary = "Definir quantidade mínima de um recurso")
    public ResponseEntity<EstoqueResponse> definirMinimo(@PathVariable Long idAbrigo,
                                                          @Valid @RequestBody EstoqueMinRequest request) {
        return ResponseEntity.ok(estoqueService.definirMinimo(idAbrigo, request));
    }
}

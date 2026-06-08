package br.com.fiap.vesta.dto.response;

import java.time.LocalDateTime;

public record AssistenteRespostaResponse(
    String resposta,
    String contextoUsado,
    LocalDateTime geradoEm
) {}

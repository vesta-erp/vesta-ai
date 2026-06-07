package br.com.fiap.vesta.dto.request;

import jakarta.validation.constraints.NotNull;

public record TransferenciaRequest(
    @NotNull Long idFamilia,
    @NotNull Long idAbrigoDestino,
    String dsMotivo
) {}

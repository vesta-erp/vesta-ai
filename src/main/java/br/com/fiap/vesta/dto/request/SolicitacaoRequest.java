package br.com.fiap.vesta.dto.request;

import jakarta.validation.constraints.*;

public record SolicitacaoRequest(
    @NotNull Long idRecurso,
    @NotNull @Min(1) Integer qtSolicitada,
    @Size(max = 500) String dsJustificativa
) {}

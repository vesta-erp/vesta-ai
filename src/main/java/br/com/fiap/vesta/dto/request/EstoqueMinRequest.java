package br.com.fiap.vesta.dto.request;

import jakarta.validation.constraints.*;

public record EstoqueMinRequest(
    @NotNull Long idRecurso,
    @NotNull @Min(0) Integer qtMinima
) {}

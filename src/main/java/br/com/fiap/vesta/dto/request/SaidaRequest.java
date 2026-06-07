package br.com.fiap.vesta.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaidaRequest(@NotNull Long idFamilia) {}

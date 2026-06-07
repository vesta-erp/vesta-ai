package br.com.fiap.vesta.dto.request;

import br.com.fiap.vesta.domain.enums.StatusSolicitacao;
import jakarta.validation.constraints.NotNull;

public record AtualizacaoSolicitacaoRequest(@NotNull StatusSolicitacao novoStatus) {}

package br.com.fiap.vesta.dto.request;

import br.com.fiap.vesta.domain.enums.TipoMovimentacao;
import jakarta.validation.constraints.*;

public record MovimentacaoRequest(
    @NotNull Long idRecurso,
    @NotNull TipoMovimentacao tpMovimentacao,
    @NotNull @Min(1) Integer qtMovimentada,
    @Size(max = 500) String dsObservacao,
    Long idSolicitacao
) {}

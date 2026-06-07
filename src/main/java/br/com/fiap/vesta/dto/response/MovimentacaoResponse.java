package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.TipoMovimentacao;
import java.time.LocalDateTime;

public record MovimentacaoResponse(
    Long idMovimentacao,
    Long idAbrigo,
    Long idRecurso,
    String nmRecurso,
    TipoMovimentacao tpMovimentacao,
    Integer qtMovimentada,
    String dsObservacao,
    Long idSolicitacao,
    LocalDateTime dtMovimentacao
) {}

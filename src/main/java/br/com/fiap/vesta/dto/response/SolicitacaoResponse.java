package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.StatusSolicitacao;
import java.time.LocalDateTime;

public record SolicitacaoResponse(
    Long idSolicitacao,
    Long idAbrigo,
    String nmAbrigo,
    Long idRecurso,
    String nmRecurso,
    Integer qtSolicitada,
    StatusSolicitacao stStatus,
    String dsJustificativa,
    LocalDateTime dtSolicitacao,
    LocalDateTime dtAtualizacao
) {}

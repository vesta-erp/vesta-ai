package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.StatusTransferencia;
import java.time.LocalDateTime;

public record TransferenciaResponse(
    Long idTransferencia,
    Long idAbrigoOrigem,
    String nmAbrigoOrigem,
    Long idAbrigoDestino,
    String nmAbrigoDestino,
    Long idFamilia,
    String nmResponsavel,
    String dsMotivo,
    StatusTransferencia stStatus,
    LocalDateTime dtSolicitacao,
    LocalDateTime dtConclusao
) {}

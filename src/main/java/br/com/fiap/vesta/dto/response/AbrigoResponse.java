package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import java.time.LocalDateTime;

public record AbrigoResponse(
    Long idAbrigo,
    String nmAbrigo,
    String dsEndereco,
    Integer qtCapacidadeMaxima,
    Integer qtOcupacaoAtual,
    Integer qtVagasDisponiveis,
    StatusAbrigo stStatus,
    Long idRegiao,
    String nmRegiao,
    Long idInstituicao,
    String nmInstituicao,
    LocalDateTime dtAbertura
) {}

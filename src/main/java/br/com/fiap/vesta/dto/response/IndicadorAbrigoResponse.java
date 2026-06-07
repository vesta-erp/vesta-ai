package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.StatusAbrigo;

public record IndicadorAbrigoResponse(
    Long idAbrigo,
    String nmAbrigo,
    String nmRegiao,
    StatusAbrigo stStatus,
    Integer qtCapacidadeMaxima,
    Integer qtOcupacaoAtual,
    double taxaOcupacao,
    int qtItensAbaixoMinimo,
    long qtOcorrenciasAbertas,
    int nivelCriticidade,
    String descricaoCriticidade
) {}

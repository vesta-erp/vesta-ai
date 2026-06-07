package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.TipoRecurso;
import java.time.LocalDateTime;

public record EstoqueResponse(
    Long idEstoque,
    Long idAbrigo,
    Long idRecurso,
    String nmRecurso,
    TipoRecurso tpRecurso,
    String dsUnidadeMedida,
    Integer qtAtual,
    Integer qtMinima,
    boolean abaixoMinimo,
    LocalDateTime dtAtualizacao
) {}

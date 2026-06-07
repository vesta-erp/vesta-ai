package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.TipoAlerta;
import java.time.LocalDateTime;

public record AlertaResponse(
    Long idAlerta,
    Long idAbrigo,
    String nmAbrigo,
    TipoAlerta tpAlerta,
    String dsMensagem,
    String stStatus,
    LocalDateTime dtGeracao,
    LocalDateTime dtResolucao
) {}

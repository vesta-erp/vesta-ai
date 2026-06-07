package br.com.fiap.vesta.dto.response;

import br.com.fiap.vesta.domain.enums.SeveridadeOcorrencia;
import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import java.time.LocalDateTime;

public record OcorrenciaResponse(
    Long idOcorrencia,
    Long idAbrigo,
    String nmAbrigo,
    Long idUsuario,
    String nmUsuario,
    String nmTitulo,
    String dsDescricao,
    SeveridadeOcorrencia tpSeveridade,
    StatusOcorrencia stStatus,
    LocalDateTime dtOcorrencia,
    LocalDateTime dtResolucao
) {}

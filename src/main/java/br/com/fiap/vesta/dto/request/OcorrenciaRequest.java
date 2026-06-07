package br.com.fiap.vesta.dto.request;

import br.com.fiap.vesta.domain.enums.SeveridadeOcorrencia;
import jakarta.validation.constraints.*;

public record OcorrenciaRequest(
    @NotBlank @Size(max = 200) String nmTitulo,
    String dsDescricao,
    @NotNull SeveridadeOcorrencia tpSeveridade
) {}

package br.com.fiap.vesta.dto.request;

import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import jakarta.validation.constraints.*;

public record AbrigoRequest(
    @NotBlank @Size(max = 150) String nmAbrigo,
    @NotBlank @Size(max = 300) String dsEndereco,
    @NotNull @Min(1) Integer qtCapacidadeMaxima,
    StatusAbrigo stStatus,
    @NotNull Long idRegiao,
    @NotNull Long idInstituicao
) {}

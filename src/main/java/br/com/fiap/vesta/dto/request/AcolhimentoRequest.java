package br.com.fiap.vesta.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record AcolhimentoRequest(
    @NotBlank String nmResponsavel,
    String nrCpfResponsavel,
    String nrTelefone,
    @NotNull @Size(min = 1) List<MembroRequest> membros
) {
    public record MembroRequest(
        @NotBlank String nmPessoa,
        LocalDate dtNascimento,
        String tpDocumento,
        String nrDocumento
    ) {}
}

package br.com.fiap.vesta.dto.response;

import java.time.LocalDate;

public record PessoaAbrigadaResponse(
    Long idPessoa,
    String nmPessoa,
    LocalDate dtNascimento,
    String tpDocumento,
    String nrDocumento,
    Long idFamilia,
    Long idAbrigo,
    boolean presente
) {}

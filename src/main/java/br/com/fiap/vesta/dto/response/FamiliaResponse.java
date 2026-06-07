package br.com.fiap.vesta.dto.response;

import java.time.LocalDateTime;

public record FamiliaResponse(
    Long idFamilia,
    String nmResponsavel,
    String nrCpfResponsavel,
    String nrTelefone,
    Long idAbrigo,
    String nmAbrigo,
    LocalDateTime dtEntrada,
    LocalDateTime dtSaida,
    boolean presente
) {}

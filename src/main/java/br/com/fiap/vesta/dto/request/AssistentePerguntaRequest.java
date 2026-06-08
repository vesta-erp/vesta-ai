package br.com.fiap.vesta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssistentePerguntaRequest(
    @NotBlank(message = "A pergunta não pode ser vazia")
    @Size(max = 500, message = "Pergunta deve ter no máximo 500 caracteres")
    String pergunta
) {}

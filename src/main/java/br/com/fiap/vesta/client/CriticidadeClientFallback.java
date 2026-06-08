package br.com.fiap.vesta.client;

import br.com.fiap.vesta.client.dto.CriticidadeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class CriticidadeClientFallback implements CriticidadeClient {

    private static final Logger log = LoggerFactory.getLogger(CriticidadeClientFallback.class);

    @Override
    public List<CriticidadeResponse> listarCriticidade() {
        log.warn("[Criticidade] Serviço .NET indisponível — retornando lista vazia como fallback");
        return Collections.emptyList();
    }

    @Override
    public CriticidadeResponse buscarCriticidade(Long id) {
        log.warn("[Criticidade] Serviço .NET indisponível para abrigo {} — retornando fallback", id);
        return new CriticidadeResponse(id, 0, "INDISPONIVEL",
            "Serviço .NET temporariamente indisponível", Collections.emptyList());
    }
}

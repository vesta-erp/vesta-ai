package br.com.fiap.vesta.client;

import br.com.fiap.vesta.client.dto.CriticidadeResponse;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class CriticidadeClientFallback implements CriticidadeClient {

    @Override
    public List<CriticidadeResponse> listarCriticidade() {
        return Collections.emptyList();
    }

    @Override
    public CriticidadeResponse buscarCriticidade(Long id) {
        return new CriticidadeResponse(id, 0, "INDISPONIVEL",
            "Serviço .NET temporariamente indisponível", Collections.emptyList());
    }
}

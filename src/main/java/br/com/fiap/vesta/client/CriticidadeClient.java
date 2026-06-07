package br.com.fiap.vesta.client;

import br.com.fiap.vesta.client.dto.CriticidadeResponse;
import br.com.fiap.vesta.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(
    name = "dotnet-criticidade",
    url = "${vesta.dotnet-service.url}",
    configuration = FeignConfig.class
)
public interface CriticidadeClient {

    @GetMapping("/api/criticidade/abrigos")
    List<CriticidadeResponse> listarCriticidade();

    @GetMapping("/api/criticidade/abrigos/{id}")
    CriticidadeResponse buscarCriticidade(@PathVariable Long id);
}

package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.Abrigo;
import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import br.com.fiap.vesta.dto.response.IndicadorAbrigoResponse;
import br.com.fiap.vesta.repository.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class IndicadorService {

    private final AbrigoRepository abrigoRepository;
    private final EstoqueAbrigoRepository estoqueRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    public IndicadorService(AbrigoRepository abrigoRepository,
                             EstoqueAbrigoRepository estoqueRepository,
                             OcorrenciaRepository ocorrenciaRepository) {
        this.abrigoRepository = abrigoRepository;
        this.estoqueRepository = estoqueRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @Cacheable("indicadores")
    public List<IndicadorAbrigoResponse> rankingCriticidade() {
        return abrigoRepository.findAll().stream()
            .map(this::calcularIndicador)
            .sorted(Comparator.comparingInt(IndicadorAbrigoResponse::nivelCriticidade).reversed())
            .toList();
    }

    public IndicadorAbrigoResponse indicadorPorAbrigo(Long idAbrigo) {
        Abrigo abrigo = abrigoRepository.findById(idAbrigo)
            .orElseThrow(() -> new br.com.fiap.vesta.exception.ResourceNotFoundException("Abrigo", idAbrigo));
        return calcularIndicador(abrigo);
    }

    private IndicadorAbrigoResponse calcularIndicador(Abrigo a) {
        double taxaOcupacao = a.getQtCapacidadeMaxima() > 0
            ? (a.getQtOcupacaoAtual() * 100.0 / a.getQtCapacidadeMaxima())
            : 0;

        List<?> todosItens = estoqueRepository.findByAbrigoIdAbrigo(a.getIdAbrigo());
        int totalItens = todosItens.size();
        int itensAbaixo = estoqueRepository.findItensAbaixoMinimoPorAbrigo(a.getIdAbrigo()).size();
        double scoreEstoque = totalItens > 0 ? (itensAbaixo * 100.0 / totalItens) : 0;

        long ocorrenciasAbertas = ocorrenciaRepository
            .countByAbrigoIdAbrigoAndStStatusNot(a.getIdAbrigo(), StatusOcorrencia.RESOLVIDA);
        double scoreOcorrencias = Math.min(ocorrenciasAbertas * 20.0, 100);

        int nivelCriticidade = (int) ((taxaOcupacao + scoreEstoque + scoreOcorrencias) / 3);

        String descricao = switch (nivelCriticidade / 25) {
            case 0 -> "BAIXO";
            case 1 -> "MEDIO";
            case 2 -> "ALTO";
            default -> "CRITICO";
        };

        return new IndicadorAbrigoResponse(
            a.getIdAbrigo(), a.getNmAbrigo(),
            a.getRegiao() != null ? a.getRegiao().getNmRegiao() : "-",
            a.getStStatus(),
            a.getQtCapacidadeMaxima(), a.getQtOcupacaoAtual(),
            taxaOcupacao, itensAbaixo, ocorrenciasAbertas,
            nivelCriticidade, descricao
        );
    }
}

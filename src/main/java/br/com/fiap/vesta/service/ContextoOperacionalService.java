package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.domain.enums.StatusSolicitacao;
import br.com.fiap.vesta.dto.response.IndicadorAbrigoResponse;
import br.com.fiap.vesta.repository.AbrigoRepository;
import br.com.fiap.vesta.repository.EstoqueAbrigoRepository;
import br.com.fiap.vesta.repository.SolicitacaoRecursoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ContextoOperacionalService {

    private final AbrigoRepository abrigoRepository;
    private final EstoqueAbrigoRepository estoqueRepository;
    private final SolicitacaoRecursoRepository solicitacaoRepository;
    private final IndicadorService indicadorService;

    public ContextoOperacionalService(AbrigoRepository abrigoRepository,
                                      EstoqueAbrigoRepository estoqueRepository,
                                      SolicitacaoRecursoRepository solicitacaoRepository,
                                      IndicadorService indicadorService) {
        this.abrigoRepository = abrigoRepository;
        this.estoqueRepository = estoqueRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.indicadorService = indicadorService;
    }

    public String montarSnapshot() {
        var agora = LocalDateTime.now();
        var sb = new StringBuilder();

        sb.append("=== SNAPSHOT OPERACIONAL — VESTA ===\n");
        sb.append("Data/hora: ").append(agora.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n\n");

        // Abrigos ativos
        var abrigosAtivos = abrigoRepository.findAll().stream()
            .filter(a -> a.getStStatus() == StatusAbrigo.ATIVO || a.getStStatus() == StatusAbrigo.LOTADO)
            .toList();

        sb.append("ABRIGOS ATIVOS (").append(abrigosAtivos.size()).append("):\n");
        for (var abrigo : abrigosAtivos) {
            int itensAbaixo = estoqueRepository.findItensAbaixoMinimoPorAbrigo(abrigo.getIdAbrigo()).size();
            double taxaOcupacao = abrigo.getQtCapacidadeMaxima() > 0
                ? abrigo.getQtOcupacaoAtual() * 100.0 / abrigo.getQtCapacidadeMaxima() : 0;
            String alerta = taxaOcupacao >= 90 ? " ← CRÍTICO" : (taxaOcupacao >= 75 ? " ← ATENÇÃO" : "");
            sb.append(String.format("- %-25s | Ocupação: %d/%d (%.0f%%) | Recursos críticos: %d%s\n",
                abrigo.getNmAbrigo(),
                abrigo.getQtOcupacaoAtual(),
                abrigo.getQtCapacidadeMaxima(),
                taxaOcupacao,
                itensAbaixo,
                alerta));
        }

        // Ranking de criticidade
        sb.append("\nRANKING DE CRITICIDADE:\n");
        List<IndicadorAbrigoResponse> ranking = indicadorService.rankingCriticidade();
        for (int i = 0; i < Math.min(ranking.size(), 5); i++) {
            var ind = ranking.get(i);
            sb.append(String.format("%d. %-25s — score: %d (%s)\n",
                i + 1,
                ind.nmAbrigo(),
                ind.nivelCriticidade(),
                ind.descricaoCriticidade()));
        }

        // Solicitações pendentes e atrasadas
        var statusPendentes = List.of(
            StatusSolicitacao.ABERTA,
            StatusSolicitacao.EM_ANALISE,
            StatusSolicitacao.EM_ATENDIMENTO
        );
        long totalPendentes = solicitacaoRepository.findByStStatusIn(statusPendentes).size();
        long atrasadas = solicitacaoRepository.countByStStatusInAndDtSolicitacaoBefore(
            List.of(StatusSolicitacao.ABERTA, StatusSolicitacao.EM_ANALISE),
            agora.minusHours(24)
        );

        sb.append(String.format("\nSOLICITAÇÕES PENDENTES: %d (%d atrasadas)\n", totalPendentes, atrasadas));

        return sb.toString();
    }
}

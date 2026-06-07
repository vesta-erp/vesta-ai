package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.EstoqueAbrigo;
import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import br.com.fiap.vesta.dto.response.ResumoOperacionalResponse;
import br.com.fiap.vesta.repository.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiAssistantService {

    private final ChatClient chatClient;
    private final AbrigoService abrigoService;
    private final EstoqueAbrigoRepository estoqueRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    public AiAssistantService(ChatClient chatClient,
                               AbrigoService abrigoService,
                               EstoqueAbrigoRepository estoqueRepository,
                               OcorrenciaRepository ocorrenciaRepository) {
        this.chatClient = chatClient;
        this.abrigoService = abrigoService;
        this.estoqueRepository = estoqueRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    public ResumoOperacionalResponse gerarResumo(Long idAbrigo) {
        var abrigo = abrigoService.buscarPorId(idAbrigo);

        List<EstoqueAbrigo> criticos = estoqueRepository.findItensAbaixoMinimoPorAbrigo(idAbrigo);
        List<EstoqueAbrigo> estoques = estoqueRepository.findByAbrigoIdAbrigo(idAbrigo);
        long ocorrenciasAbertas = ocorrenciaRepository
            .countByAbrigoIdAbrigoAndStStatusNot(idAbrigo, StatusOcorrencia.RESOLVIDA);

        String contextoDados = String.format("""
            Abrigo: %s
            Status: %s
            Capacidade máxima: %d | Ocupação atual: %d (%.1f%%)
            Itens em estoque monitorados: %d | Itens abaixo do mínimo: %d
            Itens críticos: %s
            Ocorrências abertas/em andamento: %d
            """,
            abrigo.getNmAbrigo(),
            abrigo.getStStatus(),
            abrigo.getQtCapacidadeMaxima(),
            abrigo.getQtOcupacaoAtual(),
            abrigo.getQtCapacidadeMaxima() > 0
                ? abrigo.getQtOcupacaoAtual() * 100.0 / abrigo.getQtCapacidadeMaxima() : 0,
            estoques.size(),
            criticos.size(),
            criticos.stream().map(e -> e.getRecurso().getNmRecurso()).collect(Collectors.joining(", ")),
            ocorrenciasAbertas
        );

        String resumo = chatClient.prompt()
            .system("""
                Você é um assistente operacional de abrigos emergenciais. \
                Gere resumos concisos em português para gestores de defesa civil, \
                destacando situações críticas e recomendando ações prioritárias.""")
            .user("Com base nos dados abaixo, gere um resumo operacional e liste ações prioritárias:\n\n"
                + contextoDados)
            .call()
            .content();

        return new ResumoOperacionalResponse(idAbrigo, abrigo.getNmAbrigo(), resumo);
    }
}

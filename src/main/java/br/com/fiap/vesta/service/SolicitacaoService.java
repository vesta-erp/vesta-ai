package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.StatusSolicitacao;
import br.com.fiap.vesta.dto.request.AtualizacaoSolicitacaoRequest;
import br.com.fiap.vesta.dto.request.SolicitacaoRequest;
import br.com.fiap.vesta.dto.response.SolicitacaoResponse;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class SolicitacaoService {

    private static final Map<StatusSolicitacao, List<StatusSolicitacao>> TRANSICOES_VALIDAS = Map.of(
        StatusSolicitacao.ABERTA, List.of(StatusSolicitacao.EM_ANALISE, StatusSolicitacao.CANCELADA),
        StatusSolicitacao.EM_ANALISE, List.of(StatusSolicitacao.EM_ATENDIMENTO, StatusSolicitacao.CANCELADA),
        StatusSolicitacao.EM_ATENDIMENTO, List.of(StatusSolicitacao.CONCLUIDA, StatusSolicitacao.CANCELADA),
        StatusSolicitacao.CONCLUIDA, List.of(),
        StatusSolicitacao.CANCELADA, List.of()
    );

    private final SolicitacaoRecursoRepository solicitacaoRepository;
    private final RecursoRepository recursoRepository;
    private final AbrigoService abrigoService;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoService(SolicitacaoRecursoRepository solicitacaoRepository,
                               RecursoRepository recursoRepository,
                               AbrigoService abrigoService,
                               UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.recursoRepository = recursoRepository;
        this.abrigoService = abrigoService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SolicitacaoResponse> listarPorAbrigo(Long idAbrigo) {
        return solicitacaoRepository.findByAbrigoIdAbrigo(idAbrigo)
            .stream().map(this::toResponse).toList();
    }

    public List<SolicitacaoResponse> listarAbertas() {
        return solicitacaoRepository.findByStStatusIn(
            List.of(StatusSolicitacao.ABERTA, StatusSolicitacao.EM_ANALISE, StatusSolicitacao.EM_ATENDIMENTO))
            .stream().map(this::toResponse).toList();
    }

    public SolicitacaoResponse buscarPorId(Long id) {
        return toResponse(solicitacaoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SolicitacaoRecurso", id)));
    }

    @Transactional
    public SolicitacaoResponse abrir(Long idAbrigo, Long idUsuario, SolicitacaoRequest request) {
        Abrigo abrigo = abrigoService.buscarPorId(idAbrigo);
        Recurso recurso = recursoRepository.findById(request.idRecurso())
            .orElseThrow(() -> new ResourceNotFoundException("Recurso", request.idRecurso()));
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));

        SolicitacaoRecurso sol = new SolicitacaoRecurso();
        sol.setAbrigo(abrigo);
        sol.setRecurso(recurso);
        sol.setUsuarioSolicitante(usuario);
        sol.setQtSolicitada(request.qtSolicitada());
        sol.setDsJustificativa(request.dsJustificativa());

        return toResponse(solicitacaoRepository.save(sol));
    }

    @Transactional
    public SolicitacaoResponse atualizarStatus(Long id, AtualizacaoSolicitacaoRequest request) {
        SolicitacaoRecurso sol = solicitacaoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SolicitacaoRecurso", id));

        List<StatusSolicitacao> proximas = TRANSICOES_VALIDAS.getOrDefault(sol.getStStatus(), List.of());
        if (!proximas.contains(request.novoStatus())) {
            throw new BusinessRuleException("Transição inválida: " + sol.getStStatus()
                + " → " + request.novoStatus());
        }
        sol.setStStatus(request.novoStatus());
        return toResponse(solicitacaoRepository.save(sol));
    }

    private SolicitacaoResponse toResponse(SolicitacaoRecurso s) {
        return new SolicitacaoResponse(
            s.getIdSolicitacao(),
            s.getAbrigo().getIdAbrigo(), s.getAbrigo().getNmAbrigo(),
            s.getRecurso().getIdRecurso(), s.getRecurso().getNmRecurso(),
            s.getQtSolicitada(), s.getStStatus(), s.getDsJustificativa(),
            s.getDtSolicitacao(), s.getDtAtualizacao()
        );
    }
}

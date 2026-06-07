package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.TipoAlerta;
import br.com.fiap.vesta.domain.enums.TipoMovimentacao;
import br.com.fiap.vesta.dto.request.EstoqueMinRequest;
import br.com.fiap.vesta.dto.request.MovimentacaoRequest;
import br.com.fiap.vesta.dto.response.EstoqueResponse;
import br.com.fiap.vesta.dto.response.MovimentacaoResponse;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueAbrigoRepository estoqueRepository;
    private final MovimentacaoRecursoRepository movimentacaoRepository;
    private final RecursoRepository recursoRepository;
    private final AbrigoService abrigoService;
    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoRecursoRepository solicitacaoRepository;
    private final AlertaRepository alertaRepository;

    public EstoqueService(EstoqueAbrigoRepository estoqueRepository,
                          MovimentacaoRecursoRepository movimentacaoRepository,
                          RecursoRepository recursoRepository,
                          AbrigoService abrigoService,
                          UsuarioRepository usuarioRepository,
                          SolicitacaoRecursoRepository solicitacaoRepository,
                          AlertaRepository alertaRepository) {
        this.estoqueRepository = estoqueRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.recursoRepository = recursoRepository;
        this.abrigoService = abrigoService;
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.alertaRepository = alertaRepository;
    }

    public List<EstoqueResponse> listarPorAbrigo(Long idAbrigo) {
        return estoqueRepository.findByAbrigoIdAbrigo(idAbrigo)
            .stream().map(this::toResponse).toList();
    }

    public List<EstoqueResponse> listarAbaixoMinimo(Long idAbrigo) {
        return estoqueRepository.findItensAbaixoMinimoPorAbrigo(idAbrigo)
            .stream().map(this::toResponse).toList();
    }

    @Transactional
    public MovimentacaoResponse registrarMovimentacao(Long idAbrigo, Long idUsuario,
                                                       MovimentacaoRequest request) {
        Abrigo abrigo = abrigoService.buscarPorId(idAbrigo);
        Recurso recurso = recursoRepository.findById(request.idRecurso())
            .orElseThrow(() -> new ResourceNotFoundException("Recurso", request.idRecurso()));
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));

        EstoqueAbrigo estoque = estoqueRepository
            .findByAbrigoIdAbrigoAndRecursoIdRecurso(idAbrigo, request.idRecurso())
            .orElseGet(() -> {
                EstoqueAbrigo novo = new EstoqueAbrigo();
                novo.setAbrigo(abrigo);
                novo.setRecurso(recurso);
                novo.setQtMinima(0);
                return novo;
            });

        if (request.tpMovimentacao() == TipoMovimentacao.SAIDA) {
            if (estoque.getQtAtual() < request.qtMovimentada()) {
                throw new BusinessRuleException("Estoque insuficiente. Disponível: " + estoque.getQtAtual());
            }
            estoque.setQtAtual(estoque.getQtAtual() - request.qtMovimentada());
        } else if (request.tpMovimentacao() == TipoMovimentacao.ENTRADA) {
            estoque.setQtAtual(estoque.getQtAtual() + request.qtMovimentada());
        } else {
            estoque.setQtAtual(request.qtMovimentada());
        }
        estoqueRepository.save(estoque);

        MovimentacaoRecurso mov = new MovimentacaoRecurso();
        mov.setAbrigo(abrigo);
        mov.setRecurso(recurso);
        mov.setUsuario(usuario);
        mov.setTpMovimentacao(request.tpMovimentacao());
        mov.setQtMovimentada(request.qtMovimentada());
        mov.setDsObservacao(request.dsObservacao());
        if (request.idSolicitacao() != null) {
            solicitacaoRepository.findById(request.idSolicitacao())
                .ifPresent(mov::setSolicitacao);
        }
        movimentacaoRepository.save(mov);

        verificarEstoqueCritico(abrigo, estoque, recurso);

        return toMovResponse(mov);
    }

    @Transactional
    public EstoqueResponse definirMinimo(Long idAbrigo, EstoqueMinRequest request) {
        Abrigo abrigo = abrigoService.buscarPorId(idAbrigo);
        Recurso recurso = recursoRepository.findById(request.idRecurso())
            .orElseThrow(() -> new ResourceNotFoundException("Recurso", request.idRecurso()));

        EstoqueAbrigo estoque = estoqueRepository
            .findByAbrigoIdAbrigoAndRecursoIdRecurso(idAbrigo, request.idRecurso())
            .orElseGet(() -> {
                EstoqueAbrigo novo = new EstoqueAbrigo();
                novo.setAbrigo(abrigo);
                novo.setRecurso(recurso);
                return novo;
            });
        estoque.setQtMinima(request.qtMinima());
        return toResponse(estoqueRepository.save(estoque));
    }

    private void verificarEstoqueCritico(Abrigo abrigo, EstoqueAbrigo estoque, Recurso recurso) {
        if (estoque.isAbaixoMinimo()) {
            boolean jaExiste = alertaRepository
                .findByAbrigoIdAbrigoAndTpAlertaAndStStatus(abrigo.getIdAbrigo(), TipoAlerta.ESTOQUE_CRITICO, "ATIVO")
                .isPresent();
            if (!jaExiste) {
                Alerta alerta = new Alerta();
                alerta.setAbrigo(abrigo);
                alerta.setTpAlerta(TipoAlerta.ESTOQUE_CRITICO);
                alerta.setDsMensagem("Estoque crítico: " + recurso.getNmRecurso()
                    + " abaixo do mínimo (" + estoque.getQtAtual() + "/" + estoque.getQtMinima() + ")");
                alertaRepository.save(alerta);
            }
        }
    }

    private EstoqueResponse toResponse(EstoqueAbrigo e) {
        return new EstoqueResponse(
            e.getIdEstoque(),
            e.getAbrigo().getIdAbrigo(),
            e.getRecurso().getIdRecurso(),
            e.getRecurso().getNmRecurso(),
            e.getRecurso().getTpRecurso(),
            e.getRecurso().getDsUnidadeMedida(),
            e.getQtAtual(), e.getQtMinima(), e.isAbaixoMinimo(),
            e.getDtAtualizacao()
        );
    }

    private MovimentacaoResponse toMovResponse(MovimentacaoRecurso m) {
        return new MovimentacaoResponse(
            m.getIdMovimentacao(), m.getAbrigo().getIdAbrigo(),
            m.getRecurso().getIdRecurso(), m.getRecurso().getNmRecurso(),
            m.getTpMovimentacao(), m.getQtMovimentada(), m.getDsObservacao(),
            m.getSolicitacao() != null ? m.getSolicitacao().getIdSolicitacao() : null,
            m.getDtMovimentacao()
        );
    }
}

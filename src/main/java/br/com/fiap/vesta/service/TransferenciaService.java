package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.domain.enums.StatusTransferencia;
import br.com.fiap.vesta.dto.request.TransferenciaRequest;
import br.com.fiap.vesta.dto.response.TransferenciaResponse;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferenciaService {

    private final TransferenciaAbrigoRepository transferenciaRepository;
    private final FamiliaRepository familiaRepository;
    private final PessoaAbrigadaRepository pessoaRepository;
    private final AbrigoRepository abrigoRepository;
    private final AbrigoService abrigoService;
    private final UsuarioRepository usuarioRepository;

    public TransferenciaService(TransferenciaAbrigoRepository transferenciaRepository,
                                 FamiliaRepository familiaRepository,
                                 PessoaAbrigadaRepository pessoaRepository,
                                 AbrigoRepository abrigoRepository,
                                 AbrigoService abrigoService,
                                 UsuarioRepository usuarioRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.familiaRepository = familiaRepository;
        this.pessoaRepository = pessoaRepository;
        this.abrigoRepository = abrigoRepository;
        this.abrigoService = abrigoService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<TransferenciaResponse> listarPorAbrigo(Long idAbrigo) {
        List<TransferenciaAbrigo> list = transferenciaRepository.findByAbrigoOrigemIdAbrigo(idAbrigo);
        list.addAll(transferenciaRepository.findByAbrigoDestinoIdAbrigo(idAbrigo));
        return list.stream().map(this::toResponse).toList();
    }

    @Transactional
    public TransferenciaResponse solicitar(Long idAbrigoOrigem, Long idUsuario,
                                           TransferenciaRequest request) {
        if (idAbrigoOrigem.equals(request.idAbrigoDestino())) {
            throw new BusinessRuleException("Origem e destino não podem ser o mesmo abrigo.");
        }

        Abrigo origem = abrigoService.buscarPorId(idAbrigoOrigem);
        Abrigo destino = abrigoService.buscarPorId(request.idAbrigoDestino());
        Familia familia = familiaRepository.findById(request.idFamilia())
            .orElseThrow(() -> new ResourceNotFoundException("Familia", request.idFamilia()));
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));

        if (destino.getStStatus() == StatusAbrigo.INTERDITADO ||
            destino.getStStatus() == StatusAbrigo.INATIVO) {
            throw new BusinessRuleException("Abrigo destino não está disponível.");
        }

        long qtPessoas = pessoaRepository.countByAbrigoIdAbrigoAndStPresente(
            familia.getAbrigo().getIdAbrigo(), "S");

        if (destino.getVagasDisponiveis() < qtPessoas) {
            throw new BusinessRuleException("Abrigo destino não possui vagas suficientes. Disponível: "
                + destino.getVagasDisponiveis());
        }

        TransferenciaAbrigo t = new TransferenciaAbrigo();
        t.setAbrigoOrigem(origem);
        t.setAbrigoDestino(destino);
        t.setFamilia(familia);
        t.setUsuarioResponsavel(usuario);
        t.setDsMotivo(request.dsMotivo());

        return toResponse(transferenciaRepository.save(t));
    }

    @Transactional
    public TransferenciaResponse concluir(Long id) {
        TransferenciaAbrigo t = transferenciaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TransferenciaAbrigo", id));
        if (t.getStStatus() != StatusTransferencia.APROVADA) {
            throw new BusinessRuleException("Transferência precisa estar APROVADA para ser concluída.");
        }

        Familia familia = t.getFamilia();
        List<PessoaAbrigada> pessoas = pessoaRepository.findByFamiliaIdFamilia(familia.getIdFamilia());
        int qtPessoas = (int) pessoas.stream().filter(PessoaAbrigada::isPresente).count();

        Abrigo origem = t.getAbrigoOrigem();
        Abrigo destino = t.getAbrigoDestino();

        origem.setQtOcupacaoAtual(Math.max(0, origem.getQtOcupacaoAtual() - qtPessoas));
        destino.setQtOcupacaoAtual(destino.getQtOcupacaoAtual() + qtPessoas);
        if (destino.isAtingindoCapacidade()) destino.setStStatus(StatusAbrigo.LOTADO);
        if (origem.getStStatus() == StatusAbrigo.LOTADO) origem.setStStatus(StatusAbrigo.ATIVO);

        familia.setAbrigo(destino);
        familiaRepository.save(familia);
        pessoas.forEach(p -> { p.setAbrigo(destino); pessoaRepository.save(p); });
        abrigoRepository.save(origem);
        abrigoRepository.save(destino);

        t.setStStatus(StatusTransferencia.CONCLUIDA);
        t.setDtConclusao(LocalDateTime.now());
        return toResponse(transferenciaRepository.save(t));
    }

    @Transactional
    public TransferenciaResponse aprovar(Long id) {
        TransferenciaAbrigo t = transferenciaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TransferenciaAbrigo", id));
        if (t.getStStatus() != StatusTransferencia.PENDENTE) {
            throw new BusinessRuleException("Somente transferências PENDENTES podem ser aprovadas.");
        }
        t.setStStatus(StatusTransferencia.APROVADA);
        return toResponse(transferenciaRepository.save(t));
    }

    private TransferenciaResponse toResponse(TransferenciaAbrigo t) {
        return new TransferenciaResponse(
            t.getIdTransferencia(),
            t.getAbrigoOrigem().getIdAbrigo(), t.getAbrigoOrigem().getNmAbrigo(),
            t.getAbrigoDestino().getIdAbrigo(), t.getAbrigoDestino().getNmAbrigo(),
            t.getFamilia().getIdFamilia(), t.getFamilia().getNmResponsavel(),
            t.getDsMotivo(), t.getStStatus(),
            t.getDtSolicitacao(), t.getDtConclusao()
        );
    }
}

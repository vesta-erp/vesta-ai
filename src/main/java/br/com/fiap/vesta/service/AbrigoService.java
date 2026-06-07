package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.domain.enums.TipoAlerta;
import br.com.fiap.vesta.dto.request.AbrigoRequest;
import br.com.fiap.vesta.dto.response.AbrigoResponse;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AbrigoService {

    private final AbrigoRepository abrigoRepository;
    private final RegiaoRepository regiaoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final AlertaRepository alertaRepository;

    public AbrigoService(AbrigoRepository abrigoRepository,
                         RegiaoRepository regiaoRepository,
                         InstituicaoRepository instituicaoRepository,
                         AlertaRepository alertaRepository) {
        this.abrigoRepository = abrigoRepository;
        this.regiaoRepository = regiaoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.alertaRepository = alertaRepository;
    }

    @Cacheable("abrigos")
    public List<AbrigoResponse> listarTodos() {
        return abrigoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<AbrigoResponse> listarPorRegiao(Long idRegiao) {
        return abrigoRepository.findByRegiaoIdRegiao(idRegiao).stream().map(this::toResponse).toList();
    }

    public AbrigoResponse buscarResponsePorId(Long id) {
        return toResponse(buscarPorId(id));
    }

    public Abrigo buscarPorId(Long id) {
        return abrigoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Abrigo", id));
    }

    @Transactional
    @CacheEvict(value = "abrigos", allEntries = true)
    public AbrigoResponse criar(AbrigoRequest request) {
        Regiao regiao = regiaoRepository.findById(request.idRegiao())
            .orElseThrow(() -> new ResourceNotFoundException("Regiao", request.idRegiao()));
        Instituicao inst = instituicaoRepository.findById(request.idInstituicao())
            .orElseThrow(() -> new ResourceNotFoundException("Instituicao", request.idInstituicao()));

        Abrigo abrigo = new Abrigo();
        abrigo.setNmAbrigo(request.nmAbrigo());
        abrigo.setDsEndereco(request.dsEndereco());
        abrigo.setQtCapacidadeMaxima(request.qtCapacidadeMaxima());
        abrigo.setStStatus(request.stStatus() != null ? request.stStatus() : StatusAbrigo.ATIVO);
        abrigo.setRegiao(regiao);
        abrigo.setInstituicao(inst);

        return toResponse(abrigoRepository.save(abrigo));
    }

    @Transactional
    @CacheEvict(value = "abrigos", allEntries = true)
    public AbrigoResponse atualizar(Long id, AbrigoRequest request) {
        Abrigo abrigo = buscarPorId(id);
        abrigo.setNmAbrigo(request.nmAbrigo());
        abrigo.setDsEndereco(request.dsEndereco());
        abrigo.setQtCapacidadeMaxima(request.qtCapacidadeMaxima());
        if (request.stStatus() != null) abrigo.setStStatus(request.stStatus());
        if (request.idRegiao() != null) {
            abrigo.setRegiao(regiaoRepository.findById(request.idRegiao())
                .orElseThrow(() -> new ResourceNotFoundException("Regiao", request.idRegiao())));
        }
        return toResponse(abrigoRepository.save(abrigo));
    }

    @Transactional
    @CacheEvict(value = "abrigos", allEntries = true)
    public void atualizarStatus(Long id, StatusAbrigo novoStatus) {
        Abrigo abrigo = buscarPorId(id);
        abrigo.setStStatus(novoStatus);
        abrigoRepository.save(abrigo);

        if (novoStatus == StatusAbrigo.LOTADO) {
            gerarAlertaLotacao(abrigo);
        }
    }

    void gerarAlertaLotacao(Abrigo abrigo) {
        boolean jaExiste = alertaRepository
            .findByAbrigoIdAbrigoAndTpAlertaAndStStatus(abrigo.getIdAbrigo(), TipoAlerta.LOTACAO, "ATIVO")
            .isPresent();
        if (!jaExiste) {
            Alerta alerta = new Alerta();
            alerta.setAbrigo(abrigo);
            alerta.setTpAlerta(TipoAlerta.LOTACAO);
            alerta.setDsMensagem("Abrigo " + abrigo.getNmAbrigo() + " atingiu capacidade máxima.");
            alertaRepository.save(alerta);
        }
    }

    public AbrigoResponse toResponse(Abrigo a) {
        return new AbrigoResponse(
            a.getIdAbrigo(), a.getNmAbrigo(), a.getDsEndereco(),
            a.getQtCapacidadeMaxima(), a.getQtOcupacaoAtual(), a.getVagasDisponiveis(),
            a.getStStatus(),
            a.getRegiao() != null ? a.getRegiao().getIdRegiao() : null,
            a.getRegiao() != null ? a.getRegiao().getNmRegiao() : null,
            a.getInstituicao() != null ? a.getInstituicao().getIdInstituicao() : null,
            a.getInstituicao() != null ? a.getInstituicao().getNmInstituicao() : null,
            a.getDtAbertura()
        );
    }
}

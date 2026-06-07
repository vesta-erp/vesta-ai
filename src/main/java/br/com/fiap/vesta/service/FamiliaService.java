package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.dto.request.AcolhimentoRequest;
import br.com.fiap.vesta.dto.response.FamiliaResponse;
import br.com.fiap.vesta.dto.response.PessoaAbrigadaResponse;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FamiliaService {

    private final FamiliaRepository familiaRepository;
    private final PessoaAbrigadaRepository pessoaRepository;
    private final AbrigoRepository abrigoRepository;
    private final AbrigoService abrigoService;

    public FamiliaService(FamiliaRepository familiaRepository,
                          PessoaAbrigadaRepository pessoaRepository,
                          AbrigoRepository abrigoRepository,
                          AbrigoService abrigoService) {
        this.familiaRepository = familiaRepository;
        this.pessoaRepository = pessoaRepository;
        this.abrigoRepository = abrigoRepository;
        this.abrigoService = abrigoService;
    }

    public List<FamiliaResponse> listarPorAbrigo(Long idAbrigo) {
        return familiaRepository.findByAbrigoIdAbrigoAndDtSaidaIsNull(idAbrigo)
            .stream().map(this::toResponse).toList();
    }

    public FamiliaResponse buscarPorId(Long id) {
        return toResponse(familiaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Familia", id)));
    }

    public List<PessoaAbrigadaResponse> listarPessoasDaFamilia(Long idFamilia) {
        return pessoaRepository.findByFamiliaIdFamilia(idFamilia)
            .stream().map(this::toPessoaResponse).toList();
    }

    @Transactional
    public FamiliaResponse registrarAcolhimento(Long idAbrigo, AcolhimentoRequest request) {
        Abrigo abrigo = abrigoService.buscarPorId(idAbrigo);

        if (abrigo.getStStatus() == StatusAbrigo.INTERDITADO ||
            abrigo.getStStatus() == StatusAbrigo.INATIVO) {
            throw new BusinessRuleException("Abrigo não está disponível para acolhimento.");
        }
        int novaOcupacao = abrigo.getQtOcupacaoAtual() + request.membros().size();
        if (novaOcupacao > abrigo.getQtCapacidadeMaxima()) {
            throw new BusinessRuleException("Abrigo atingiu capacidade máxima. Vagas disponíveis: "
                + abrigo.getVagasDisponiveis());
        }

        Familia familia = new Familia();
        familia.setNmResponsavel(request.nmResponsavel());
        familia.setNrCpfResponsavel(request.nrCpfResponsavel());
        familia.setNrTelefone(request.nrTelefone());
        familia.setAbrigo(abrigo);
        familia = familiaRepository.save(familia);

        for (AcolhimentoRequest.MembroRequest membro : request.membros()) {
            PessoaAbrigada pessoa = new PessoaAbrigada();
            pessoa.setNmPessoa(membro.nmPessoa());
            pessoa.setDtNascimento(membro.dtNascimento());
            pessoa.setTpDocumento(membro.tpDocumento());
            pessoa.setNrDocumento(membro.nrDocumento());
            pessoa.setFamilia(familia);
            pessoa.setAbrigo(abrigo);
            pessoaRepository.save(pessoa);
        }

        abrigo.setQtOcupacaoAtual(novaOcupacao);
        if (novaOcupacao >= abrigo.getQtCapacidadeMaxima()) {
            abrigo.setStStatus(StatusAbrigo.LOTADO);
        }
        abrigoRepository.save(abrigo);

        return toResponse(familia);
    }

    @Transactional
    public void registrarSaida(Long idFamilia) {
        Familia familia = familiaRepository.findById(idFamilia)
            .orElseThrow(() -> new ResourceNotFoundException("Familia", idFamilia));
        if (familia.getDtSaida() != null) {
            throw new BusinessRuleException("Família já registrou saída.");
        }

        LocalDateTime agora = LocalDateTime.now();
        familia.setDtSaida(agora);
        familiaRepository.save(familia);

        List<PessoaAbrigada> pessoas = pessoaRepository.findByFamiliaIdFamilia(idFamilia);
        int qtPessoas = 0;
        for (PessoaAbrigada p : pessoas) {
            if (p.isPresente()) {
                p.setStPresente("N");
                p.setDtSaida(agora);
                pessoaRepository.save(p);
                qtPessoas++;
            }
        }

        Abrigo abrigo = familia.getAbrigo();
        int novaOcupacao = Math.max(0, abrigo.getQtOcupacaoAtual() - qtPessoas);
        abrigo.setQtOcupacaoAtual(novaOcupacao);
        if (abrigo.getStStatus() == StatusAbrigo.LOTADO && novaOcupacao < abrigo.getQtCapacidadeMaxima()) {
            abrigo.setStStatus(StatusAbrigo.ATIVO);
        }
        abrigoRepository.save(abrigo);
    }

    private FamiliaResponse toResponse(Familia f) {
        return new FamiliaResponse(
            f.getIdFamilia(), f.getNmResponsavel(), f.getNrCpfResponsavel(), f.getNrTelefone(),
            f.getAbrigo().getIdAbrigo(), f.getAbrigo().getNmAbrigo(),
            f.getDtEntrada(), f.getDtSaida(), f.isPresente()
        );
    }

    private PessoaAbrigadaResponse toPessoaResponse(PessoaAbrigada p) {
        return new PessoaAbrigadaResponse(
            p.getIdPessoa(), p.getNmPessoa(), p.getDtNascimento(),
            p.getTpDocumento(), p.getNrDocumento(),
            p.getFamilia().getIdFamilia(), p.getAbrigo().getIdAbrigo(), p.isPresente()
        );
    }
}

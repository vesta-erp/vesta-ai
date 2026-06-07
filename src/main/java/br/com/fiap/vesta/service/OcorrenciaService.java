package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.SeveridadeOcorrencia;
import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import br.com.fiap.vesta.domain.enums.TipoAlerta;
import br.com.fiap.vesta.dto.request.OcorrenciaRequest;
import br.com.fiap.vesta.dto.response.OcorrenciaResponse;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final AbrigoService abrigoService;
    private final UsuarioRepository usuarioRepository;
    private final AlertaRepository alertaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository,
                              AbrigoService abrigoService,
                              UsuarioRepository usuarioRepository,
                              AlertaRepository alertaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.abrigoService = abrigoService;
        this.usuarioRepository = usuarioRepository;
        this.alertaRepository = alertaRepository;
    }

    public List<OcorrenciaResponse> listarPorAbrigo(Long idAbrigo) {
        return ocorrenciaRepository.findByAbrigoIdAbrigo(idAbrigo)
            .stream().map(this::toResponse).toList();
    }

    public OcorrenciaResponse buscarPorId(Long id) {
        return toResponse(ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ocorrencia", id)));
    }

    @Transactional
    public OcorrenciaResponse criar(Long idAbrigo, Long idUsuario, OcorrenciaRequest request) {
        Abrigo abrigo = abrigoService.buscarPorId(idAbrigo);
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));

        Ocorrencia oc = new Ocorrencia();
        oc.setAbrigo(abrigo);
        oc.setUsuario(usuario);
        oc.setNmTitulo(request.nmTitulo());
        oc.setDsDescricao(request.dsDescricao());
        oc.setTpSeveridade(request.tpSeveridade());

        Ocorrencia saved = ocorrenciaRepository.save(oc);

        if (request.tpSeveridade() == SeveridadeOcorrencia.CRITICA) {
            gerarAlertaOcorrenciaCritica(abrigo, request.nmTitulo());
        }

        return toResponse(saved);
    }

    @Transactional
    public OcorrenciaResponse atualizarStatus(Long id, StatusOcorrencia novoStatus) {
        Ocorrencia oc = ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ocorrencia", id));
        oc.setStStatus(novoStatus);
        if (novoStatus == StatusOcorrencia.RESOLVIDA) {
            oc.setDtResolucao(LocalDateTime.now());
        }
        return toResponse(ocorrenciaRepository.save(oc));
    }

    private void gerarAlertaOcorrenciaCritica(Abrigo abrigo, String titulo) {
        Alerta alerta = new Alerta();
        alerta.setAbrigo(abrigo);
        alerta.setTpAlerta(TipoAlerta.OCORRENCIA_CRITICA);
        alerta.setDsMensagem("Ocorrência crítica registrada: " + titulo);
        alertaRepository.save(alerta);
    }

    private OcorrenciaResponse toResponse(Ocorrencia o) {
        return new OcorrenciaResponse(
            o.getIdOcorrencia(),
            o.getAbrigo().getIdAbrigo(), o.getAbrigo().getNmAbrigo(),
            o.getUsuario().getIdUsuario(), o.getUsuario().getNmUsuario(),
            o.getNmTitulo(), o.getDsDescricao(), o.getTpSeveridade(),
            o.getStStatus(), o.getDtOcorrencia(), o.getDtResolucao()
        );
    }
}

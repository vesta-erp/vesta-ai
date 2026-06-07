package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.Alerta;
import br.com.fiap.vesta.dto.response.AlertaResponse;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.AlertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<AlertaResponse> listarAtivos() {
        return alertaRepository.findByStStatus("ATIVO").stream().map(this::toResponse).toList();
    }

    public List<AlertaResponse> listarPorAbrigo(Long idAbrigo) {
        return alertaRepository.findByAbrigoIdAbrigoAndStStatus(idAbrigo, "ATIVO")
            .stream().map(this::toResponse).toList();
    }

    @Transactional
    public AlertaResponse resolver(Long id) {
        Alerta alerta = alertaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Alerta", id));
        alerta.setStStatus("RESOLVIDO");
        alerta.setDtResolucao(LocalDateTime.now());
        return toResponse(alertaRepository.save(alerta));
    }

    private AlertaResponse toResponse(Alerta a) {
        return new AlertaResponse(
            a.getIdAlerta(),
            a.getAbrigo().getIdAbrigo(), a.getAbrigo().getNmAbrigo(),
            a.getTpAlerta(), a.getDsMensagem(), a.getStStatus(),
            a.getDtGeracao(), a.getDtResolucao()
        );
    }
}

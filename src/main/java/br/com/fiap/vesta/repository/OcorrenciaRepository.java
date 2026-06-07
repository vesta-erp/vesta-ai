package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Ocorrencia;
import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByAbrigoIdAbrigo(Long idAbrigo);
    List<Ocorrencia> findByAbrigoIdAbrigoAndStStatus(Long idAbrigo, StatusOcorrencia status);
    long countByAbrigoIdAbrigoAndStStatusNot(Long idAbrigo, StatusOcorrencia status);
}

package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.SolicitacaoRecurso;
import br.com.fiap.vesta.domain.enums.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SolicitacaoRecursoRepository extends JpaRepository<SolicitacaoRecurso, Long> {
    List<SolicitacaoRecurso> findByAbrigoIdAbrigo(Long idAbrigo);
    List<SolicitacaoRecurso> findByStStatusIn(List<StatusSolicitacao> statuses);
    List<SolicitacaoRecurso> findByAbrigoIdAbrigoAndStStatus(Long idAbrigo, StatusSolicitacao status);
    long countByStStatusInAndDtSolicitacaoBefore(List<StatusSolicitacao> statuses, LocalDateTime limite);
}

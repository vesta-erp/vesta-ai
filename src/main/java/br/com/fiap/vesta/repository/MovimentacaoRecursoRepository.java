package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.MovimentacaoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentacaoRecursoRepository extends JpaRepository<MovimentacaoRecurso, Long> {
    List<MovimentacaoRecurso> findByAbrigoIdAbrigoOrderByDtMovimentacaoDesc(Long idAbrigo);
    List<MovimentacaoRecurso> findBySolicitacaoIdSolicitacao(Long idSolicitacao);
}

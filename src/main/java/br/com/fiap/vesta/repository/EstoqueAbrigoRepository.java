package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.EstoqueAbrigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface EstoqueAbrigoRepository extends JpaRepository<EstoqueAbrigo, Long> {
    List<EstoqueAbrigo> findByAbrigoIdAbrigo(Long idAbrigo);
    Optional<EstoqueAbrigo> findByAbrigoIdAbrigoAndRecursoIdRecurso(Long idAbrigo, Long idRecurso);

    @Query("SELECT e FROM EstoqueAbrigo e WHERE e.abrigo.idAbrigo = :idAbrigo AND e.qtAtual < e.qtMinima")
    List<EstoqueAbrigo> findItensAbaixoMinimoPorAbrigo(Long idAbrigo);
}

package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Abrigo;
import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {
    List<Abrigo> findByRegiaoIdRegiao(Long idRegiao);
    List<Abrigo> findByStStatus(StatusAbrigo status);

    @Query("SELECT a FROM Abrigo a WHERE a.stStatus = 'ATIVO' AND a.qtOcupacaoAtual < a.qtCapacidadeMaxima")
    List<Abrigo> findComVagas();

    @Query("SELECT a FROM Abrigo a ORDER BY (a.qtOcupacaoAtual * 1.0 / a.qtCapacidadeMaxima) DESC")
    List<Abrigo> findOrderByOcupacaoDesc();
}

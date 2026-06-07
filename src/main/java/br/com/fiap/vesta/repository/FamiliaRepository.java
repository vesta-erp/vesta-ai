package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    List<Familia> findByAbrigoIdAbrigoAndDtSaidaIsNull(Long idAbrigo);
    long countByAbrigoIdAbrigoAndDtSaidaIsNull(Long idAbrigo);
}

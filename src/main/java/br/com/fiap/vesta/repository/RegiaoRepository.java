package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    List<Regiao> findBySgEstado(String sgEstado);
}

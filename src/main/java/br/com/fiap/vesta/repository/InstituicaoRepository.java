package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    List<Instituicao> findByRegiaoIdRegiao(Long idRegiao);
}

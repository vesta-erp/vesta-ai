package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Recurso;
import br.com.fiap.vesta.domain.enums.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByTpRecurso(TipoRecurso tpRecurso);
}

package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.PessoaAbrigada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PessoaAbrigadaRepository extends JpaRepository<PessoaAbrigada, Long> {
    List<PessoaAbrigada> findByFamiliaIdFamilia(Long idFamilia);
    List<PessoaAbrigada> findByAbrigoIdAbrigoAndStPresente(Long idAbrigo, String stPresente);
    long countByAbrigoIdAbrigoAndStPresente(Long idAbrigo, String stPresente);
}

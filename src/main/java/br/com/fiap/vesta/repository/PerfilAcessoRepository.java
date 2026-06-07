package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.PerfilAcesso;
import br.com.fiap.vesta.domain.enums.NomePerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, Long> {
    Optional<PerfilAcesso> findByNmPerfil(NomePerfil nmPerfil);
}

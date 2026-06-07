package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDsEmail(String dsEmail);
    boolean existsByDsEmail(String dsEmail);
}

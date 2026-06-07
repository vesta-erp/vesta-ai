package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.Alerta;
import br.com.fiap.vesta.domain.enums.TipoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByAbrigoIdAbrigoAndStStatus(Long idAbrigo, String stStatus);
    List<Alerta> findByStStatus(String stStatus);
    Optional<Alerta> findByAbrigoIdAbrigoAndTpAlertaAndStStatus(Long idAbrigo, TipoAlerta tipo, String status);
}

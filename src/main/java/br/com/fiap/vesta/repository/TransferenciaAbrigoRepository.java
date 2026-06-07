package br.com.fiap.vesta.repository;

import br.com.fiap.vesta.domain.entity.TransferenciaAbrigo;
import br.com.fiap.vesta.domain.enums.StatusTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferenciaAbrigoRepository extends JpaRepository<TransferenciaAbrigo, Long> {
    List<TransferenciaAbrigo> findByAbrigoOrigemIdAbrigo(Long idAbrigo);
    List<TransferenciaAbrigo> findByAbrigoDestinoIdAbrigo(Long idAbrigo);
    List<TransferenciaAbrigo> findByStStatus(StatusTransferencia status);
}

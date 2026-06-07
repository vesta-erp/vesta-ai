package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.Abrigo;
import br.com.fiap.vesta.domain.entity.Familia;
import br.com.fiap.vesta.domain.entity.Usuario;
import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.dto.request.TransferenciaRequest;
import br.com.fiap.vesta.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferenciaServiceTest {

    @Mock TransferenciaAbrigoRepository transferenciaRepository;
    @Mock FamiliaRepository familiaRepository;
    @Mock PessoaAbrigadaRepository pessoaRepository;
    @Mock AbrigoRepository abrigoRepository;
    @Mock AbrigoService abrigoService;
    @Mock UsuarioRepository usuarioRepository;

    @InjectMocks TransferenciaService transferenciaService;

    @Test
    void solicitarTransferencia_mesmoAbrigo_lancaException() {
        assertThatThrownBy(() ->
            transferenciaService.solicitar(1L, 1L, new TransferenciaRequest(1L, 1L, "motivo")))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("mesmo abrigo");
    }

    @Test
    void solicitarTransferencia_destinoSemVagas_lancaException() {
        Abrigo origem = new Abrigo();
        origem.setIdAbrigo(1L); origem.setQtCapacidadeMaxima(100); origem.setQtOcupacaoAtual(50);
        origem.setStStatus(StatusAbrigo.ATIVO);

        Abrigo destino = new Abrigo();
        destino.setIdAbrigo(2L); destino.setQtCapacidadeMaxima(10); destino.setQtOcupacaoAtual(10);
        destino.setStStatus(StatusAbrigo.LOTADO);

        Familia familia = new Familia(); familia.setIdFamilia(1L); familia.setAbrigo(origem);

        Usuario usuario = new Usuario(); usuario.setIdUsuario(1L);

        when(abrigoService.buscarPorId(1L)).thenReturn(origem);
        when(abrigoService.buscarPorId(2L)).thenReturn(destino);
        when(familiaRepository.findById(1L)).thenReturn(java.util.Optional.of(familia));
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        when(pessoaRepository.countByAbrigoIdAbrigoAndStPresente(1L, "S")).thenReturn(3L);

        assertThatThrownBy(() ->
            transferenciaService.solicitar(1L, 1L, new TransferenciaRequest(1L, 2L, "motivo")))
            .isInstanceOf(BusinessRuleException.class);
    }
}

package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.TipoMovimentacao;
import br.com.fiap.vesta.dto.request.MovimentacaoRequest;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock EstoqueAbrigoRepository estoqueRepository;
    @Mock MovimentacaoRecursoRepository movimentacaoRepository;
    @Mock RecursoRepository recursoRepository;
    @Mock AbrigoService abrigoService;
    @Mock UsuarioRepository usuarioRepository;
    @Mock SolicitacaoRecursoRepository solicitacaoRepository;
    @Mock AlertaRepository alertaRepository;

    @InjectMocks EstoqueService estoqueService;

    @Test
    void saida_semEstoqueSuficiente_lancaException() {
        Abrigo abrigo = new Abrigo(); abrigo.setIdAbrigo(1L);
        Recurso recurso = new Recurso(); recurso.setIdRecurso(1L);
        EstoqueAbrigo estoque = new EstoqueAbrigo();
        estoque.setAbrigo(abrigo); estoque.setRecurso(recurso);
        estoque.setQtAtual(5); estoque.setQtMinima(0);
        Usuario usuario = new Usuario(); usuario.setIdUsuario(1L);

        when(abrigoService.buscarPorId(1L)).thenReturn(abrigo);
        when(recursoRepository.findById(1L)).thenReturn(Optional.of(recurso));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(estoqueRepository.findByAbrigoIdAbrigoAndRecursoIdRecurso(1L, 1L))
            .thenReturn(Optional.of(estoque));

        var req = new MovimentacaoRequest(1L, TipoMovimentacao.SAIDA, 10, null, null);
        assertThatThrownBy(() -> estoqueService.registrarMovimentacao(1L, 1L, req))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("insuficiente");
    }
}

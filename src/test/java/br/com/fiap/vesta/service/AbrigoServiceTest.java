package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.exception.ResourceNotFoundException;
import br.com.fiap.vesta.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @Mock AbrigoRepository abrigoRepository;
    @Mock RegiaoRepository regiaoRepository;
    @Mock InstituicaoRepository instituicaoRepository;
    @Mock AlertaRepository alertaRepository;

    @InjectMocks AbrigoService abrigoService;

    private Abrigo abrigo;

    @BeforeEach
    void setup() {
        abrigo = new Abrigo();
        abrigo.setIdAbrigo(1L);
        abrigo.setNmAbrigo("Abrigo Teste");
        abrigo.setQtCapacidadeMaxima(100);
        abrigo.setQtOcupacaoAtual(50);
        abrigo.setStStatus(StatusAbrigo.ATIVO);
    }

    @Test
    void buscarPorId_existente_retornaAbrigo() {
        when(abrigoRepository.findById(1L)).thenReturn(Optional.of(abrigo));
        Abrigo result = abrigoService.buscarPorId(1L);
        assertThat(result.getIdAbrigo()).isEqualTo(1L);
    }

    @Test
    void buscarPorId_inexistente_lancaException() {
        when(abrigoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> abrigoService.buscarPorId(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void atualizarStatus_lotado_geraAlerta() {
        when(abrigoRepository.findById(1L)).thenReturn(Optional.of(abrigo));
        abrigo.setQtOcupacaoAtual(100);
        when(alertaRepository.findByAbrigoIdAbrigoAndTpAlertaAndStStatus(any(), any(), any()))
            .thenReturn(Optional.empty());
        when(abrigoRepository.save(any())).thenReturn(abrigo);

        abrigoService.atualizarStatus(1L, StatusAbrigo.LOTADO);

        verify(alertaRepository).save(any());
    }
}

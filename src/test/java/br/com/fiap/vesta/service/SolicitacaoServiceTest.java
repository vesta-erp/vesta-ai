package br.com.fiap.vesta.service;

import br.com.fiap.vesta.domain.entity.*;
import br.com.fiap.vesta.domain.enums.*;
import br.com.fiap.vesta.dto.request.AtualizacaoSolicitacaoRequest;
import br.com.fiap.vesta.dto.request.SolicitacaoRequest;
import br.com.fiap.vesta.exception.BusinessRuleException;
import br.com.fiap.vesta.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitacaoServiceTest {

    @Mock SolicitacaoRecursoRepository solicitacaoRepository;
    @Mock RecursoRepository recursoRepository;
    @Mock AbrigoService abrigoService;
    @Mock UsuarioRepository usuarioRepository;

    @InjectMocks SolicitacaoService solicitacaoService;

    private SolicitacaoRecurso solicitacao;

    @BeforeEach
    void setup() {
        solicitacao = new SolicitacaoRecurso();
        solicitacao.setIdSolicitacao(1L);
        solicitacao.setStStatus(StatusSolicitacao.ABERTA);
        Abrigo a = new Abrigo(); a.setIdAbrigo(1L); a.setNmAbrigo("X");
        Recurso r = new Recurso(); r.setIdRecurso(1L); r.setNmRecurso("Agua");
        solicitacao.setAbrigo(a);
        solicitacao.setRecurso(r);
    }

    @Test
    void transicaoValida_ABERTA_para_EM_ANALISE_funciona() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacao));
        when(solicitacaoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var resp = solicitacaoService.atualizarStatus(1L,
            new AtualizacaoSolicitacaoRequest(StatusSolicitacao.EM_ANALISE));

        assertThat(resp.stStatus()).isEqualTo(StatusSolicitacao.EM_ANALISE);
    }

    @Test
    void transicaoInvalida_CONCLUIDA_para_ABERTA_lancaException() {
        solicitacao.setStStatus(StatusSolicitacao.CONCLUIDA);
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacao));

        assertThatThrownBy(() -> solicitacaoService.atualizarStatus(1L,
            new AtualizacaoSolicitacaoRequest(StatusSolicitacao.ABERTA)))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("inválida");
    }
}

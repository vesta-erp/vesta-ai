package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MOVIMENTACAO_RECURSO")
public class MovimentacaoRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long idMovimentacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitacao")
    private SolicitacaoRecurso solicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_movimentacao", nullable = false, length = 20)
    private TipoMovimentacao tpMovimentacao;

    @Column(name = "qt_movimentada", nullable = false)
    private Integer qtMovimentada;

    @Column(name = "ds_observacao", length = 500)
    private String dsObservacao;

    @Column(name = "dt_movimentacao", nullable = false, updatable = false)
    private LocalDateTime dtMovimentacao;

    @PrePersist
    void prePersist() {
        if (dtMovimentacao == null) dtMovimentacao = LocalDateTime.now();
    }

    public Long getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(Long idMovimentacao) { this.idMovimentacao = idMovimentacao; }
    public SolicitacaoRecurso getSolicitacao() { return solicitacao; }
    public void setSolicitacao(SolicitacaoRecurso solicitacao) { this.solicitacao = solicitacao; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public Recurso getRecurso() { return recurso; }
    public void setRecurso(Recurso recurso) { this.recurso = recurso; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public TipoMovimentacao getTpMovimentacao() { return tpMovimentacao; }
    public void setTpMovimentacao(TipoMovimentacao tpMovimentacao) { this.tpMovimentacao = tpMovimentacao; }
    public Integer getQtMovimentada() { return qtMovimentada; }
    public void setQtMovimentada(Integer qtMovimentada) { this.qtMovimentada = qtMovimentada; }
    public String getDsObservacao() { return dsObservacao; }
    public void setDsObservacao(String dsObservacao) { this.dsObservacao = dsObservacao; }
    public LocalDateTime getDtMovimentacao() { return dtMovimentacao; }
}

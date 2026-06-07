package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.StatusSolicitacao;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SOLICITACAO_RECURSO")
public class SolicitacaoRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitacao")
    private Long idSolicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_solicitante", nullable = false)
    private Usuario usuarioSolicitante;

    @Column(name = "qt_solicitada", nullable = false)
    private Integer qtSolicitada;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 20)
    private StatusSolicitacao stStatus = StatusSolicitacao.ABERTA;

    @Column(name = "ds_justificativa", length = 500)
    private String dsJustificativa;

    @Column(name = "dt_solicitacao", nullable = false, updatable = false)
    private LocalDateTime dtSolicitacao;

    @Column(name = "dt_atualizacao", nullable = false)
    private LocalDateTime dtAtualizacao;

    @PrePersist
    void prePersist() {
        if (dtSolicitacao == null) dtSolicitacao = LocalDateTime.now();
        dtAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() { dtAtualizacao = LocalDateTime.now(); }

    public Long getIdSolicitacao() { return idSolicitacao; }
    public void setIdSolicitacao(Long idSolicitacao) { this.idSolicitacao = idSolicitacao; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public Recurso getRecurso() { return recurso; }
    public void setRecurso(Recurso recurso) { this.recurso = recurso; }
    public Usuario getUsuarioSolicitante() { return usuarioSolicitante; }
    public void setUsuarioSolicitante(Usuario usuarioSolicitante) { this.usuarioSolicitante = usuarioSolicitante; }
    public Integer getQtSolicitada() { return qtSolicitada; }
    public void setQtSolicitada(Integer qtSolicitada) { this.qtSolicitada = qtSolicitada; }
    public StatusSolicitacao getStStatus() { return stStatus; }
    public void setStStatus(StatusSolicitacao stStatus) { this.stStatus = stStatus; }
    public String getDsJustificativa() { return dsJustificativa; }
    public void setDsJustificativa(String dsJustificativa) { this.dsJustificativa = dsJustificativa; }
    public LocalDateTime getDtSolicitacao() { return dtSolicitacao; }
    public LocalDateTime getDtAtualizacao() { return dtAtualizacao; }
}

package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.StatusTransferencia;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_TRANSFERENCIA_ABRIGO")
public class TransferenciaAbrigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia")
    private Long idTransferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo_origem", nullable = false)
    private Abrigo abrigoOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo_destino", nullable = false)
    private Abrigo abrigoDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_familia", nullable = false)
    private Familia familia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_responsavel", nullable = false)
    private Usuario usuarioResponsavel;

    @Column(name = "ds_motivo", length = 500)
    private String dsMotivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 20)
    private StatusTransferencia stStatus = StatusTransferencia.PENDENTE;

    @Column(name = "dt_solicitacao", nullable = false, updatable = false)
    private LocalDateTime dtSolicitacao;

    @Column(name = "dt_conclusao")
    private LocalDateTime dtConclusao;

    @PrePersist
    void prePersist() {
        if (dtSolicitacao == null) dtSolicitacao = LocalDateTime.now();
    }

    public Long getIdTransferencia() { return idTransferencia; }
    public void setIdTransferencia(Long idTransferencia) { this.idTransferencia = idTransferencia; }
    public Abrigo getAbrigoOrigem() { return abrigoOrigem; }
    public void setAbrigoOrigem(Abrigo abrigoOrigem) { this.abrigoOrigem = abrigoOrigem; }
    public Abrigo getAbrigoDestino() { return abrigoDestino; }
    public void setAbrigoDestino(Abrigo abrigoDestino) { this.abrigoDestino = abrigoDestino; }
    public Familia getFamilia() { return familia; }
    public void setFamilia(Familia familia) { this.familia = familia; }
    public Usuario getUsuarioResponsavel() { return usuarioResponsavel; }
    public void setUsuarioResponsavel(Usuario usuarioResponsavel) { this.usuarioResponsavel = usuarioResponsavel; }
    public String getDsMotivo() { return dsMotivo; }
    public void setDsMotivo(String dsMotivo) { this.dsMotivo = dsMotivo; }
    public StatusTransferencia getStStatus() { return stStatus; }
    public void setStStatus(StatusTransferencia stStatus) { this.stStatus = stStatus; }
    public LocalDateTime getDtSolicitacao() { return dtSolicitacao; }
    public LocalDateTime getDtConclusao() { return dtConclusao; }
    public void setDtConclusao(LocalDateTime dtConclusao) { this.dtConclusao = dtConclusao; }
}

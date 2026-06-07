package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.TipoInstituicao;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_INSTITUICAO")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instituicao")
    private Long idInstituicao;

    @Column(name = "nm_instituicao", nullable = false, length = 150)
    private String nmInstituicao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_instituicao", nullable = false, length = 50)
    private TipoInstituicao tpInstituicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private LocalDateTime dtCriacao;

    @PrePersist
    void prePersist() {
        if (dtCriacao == null) dtCriacao = LocalDateTime.now();
    }

    public Long getIdInstituicao() { return idInstituicao; }
    public void setIdInstituicao(Long idInstituicao) { this.idInstituicao = idInstituicao; }
    public String getNmInstituicao() { return nmInstituicao; }
    public void setNmInstituicao(String nmInstituicao) { this.nmInstituicao = nmInstituicao; }
    public TipoInstituicao getTpInstituicao() { return tpInstituicao; }
    public void setTpInstituicao(TipoInstituicao tpInstituicao) { this.tpInstituicao = tpInstituicao; }
    public Regiao getRegiao() { return regiao; }
    public void setRegiao(Regiao regiao) { this.regiao = regiao; }
    public LocalDateTime getDtCriacao() { return dtCriacao; }
}

package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.StatusAbrigo;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ABRIGO")
public class Abrigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abrigo")
    private Long idAbrigo;

    @Column(name = "nm_abrigo", nullable = false, length = 150)
    private String nmAbrigo;

    @Column(name = "ds_endereco", nullable = false, length = 300)
    private String dsEndereco;

    @Column(name = "qt_capacidade_maxima", nullable = false)
    private Integer qtCapacidadeMaxima;

    @Column(name = "qt_ocupacao_atual", nullable = false)
    private Integer qtOcupacaoAtual = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 20)
    private StatusAbrigo stStatus = StatusAbrigo.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao", nullable = false)
    private Instituicao instituicao;

    @Column(name = "dt_abertura", nullable = false)
    private LocalDateTime dtAbertura;

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private LocalDateTime dtCriacao;

    @PrePersist
    void prePersist() {
        if (dtCriacao == null) dtCriacao = LocalDateTime.now();
        if (dtAbertura == null) dtAbertura = LocalDateTime.now();
    }

    public boolean isAtingindoCapacidade() {
        return qtOcupacaoAtual >= qtCapacidadeMaxima;
    }

    public int getVagasDisponiveis() {
        return Math.max(0, qtCapacidadeMaxima - qtOcupacaoAtual);
    }

    public Long getIdAbrigo() { return idAbrigo; }
    public void setIdAbrigo(Long idAbrigo) { this.idAbrigo = idAbrigo; }
    public String getNmAbrigo() { return nmAbrigo; }
    public void setNmAbrigo(String nmAbrigo) { this.nmAbrigo = nmAbrigo; }
    public String getDsEndereco() { return dsEndereco; }
    public void setDsEndereco(String dsEndereco) { this.dsEndereco = dsEndereco; }
    public Integer getQtCapacidadeMaxima() { return qtCapacidadeMaxima; }
    public void setQtCapacidadeMaxima(Integer qtCapacidadeMaxima) { this.qtCapacidadeMaxima = qtCapacidadeMaxima; }
    public Integer getQtOcupacaoAtual() { return qtOcupacaoAtual; }
    public void setQtOcupacaoAtual(Integer qtOcupacaoAtual) { this.qtOcupacaoAtual = qtOcupacaoAtual; }
    public StatusAbrigo getStStatus() { return stStatus; }
    public void setStStatus(StatusAbrigo stStatus) { this.stStatus = stStatus; }
    public Regiao getRegiao() { return regiao; }
    public void setRegiao(Regiao regiao) { this.regiao = regiao; }
    public Instituicao getInstituicao() { return instituicao; }
    public void setInstituicao(Instituicao instituicao) { this.instituicao = instituicao; }
    public LocalDateTime getDtAbertura() { return dtAbertura; }
    public void setDtAbertura(LocalDateTime dtAbertura) { this.dtAbertura = dtAbertura; }
    public LocalDateTime getDtCriacao() { return dtCriacao; }
}

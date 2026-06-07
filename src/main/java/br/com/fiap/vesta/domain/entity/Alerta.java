package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.TipoAlerta;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ALERTA")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_alerta", nullable = false, length = 50)
    private TipoAlerta tpAlerta;

    @Column(name = "ds_mensagem", nullable = false, length = 500)
    private String dsMensagem;

    @Column(name = "st_status", nullable = false, length = 20)
    private String stStatus = "ATIVO";

    @Column(name = "dt_geracao", nullable = false, updatable = false)
    private LocalDateTime dtGeracao;

    @Column(name = "dt_resolucao")
    private LocalDateTime dtResolucao;

    @PrePersist
    void prePersist() {
        if (dtGeracao == null) dtGeracao = LocalDateTime.now();
    }

    public boolean isAtivo() { return "ATIVO".equals(stStatus); }

    public Long getIdAlerta() { return idAlerta; }
    public void setIdAlerta(Long idAlerta) { this.idAlerta = idAlerta; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public TipoAlerta getTpAlerta() { return tpAlerta; }
    public void setTpAlerta(TipoAlerta tpAlerta) { this.tpAlerta = tpAlerta; }
    public String getDsMensagem() { return dsMensagem; }
    public void setDsMensagem(String dsMensagem) { this.dsMensagem = dsMensagem; }
    public String getStStatus() { return stStatus; }
    public void setStStatus(String stStatus) { this.stStatus = stStatus; }
    public LocalDateTime getDtGeracao() { return dtGeracao; }
    public LocalDateTime getDtResolucao() { return dtResolucao; }
    public void setDtResolucao(LocalDateTime dtResolucao) { this.dtResolucao = dtResolucao; }
}

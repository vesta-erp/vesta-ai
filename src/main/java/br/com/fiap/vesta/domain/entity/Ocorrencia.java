package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.SeveridadeOcorrencia;
import br.com.fiap.vesta.domain.enums.StatusOcorrencia;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_OCORRENCIA")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ocorrencia")
    private Long idOcorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nm_titulo", nullable = false, length = 200)
    private String nmTitulo;

    @Lob
    @Column(name = "ds_descricao")
    private String dsDescricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_severidade", nullable = false, length = 20)
    private SeveridadeOcorrencia tpSeveridade;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 20)
    private StatusOcorrencia stStatus = StatusOcorrencia.ABERTA;

    @Column(name = "dt_ocorrencia", nullable = false)
    private LocalDateTime dtOcorrencia;

    @Column(name = "dt_resolucao")
    private LocalDateTime dtResolucao;

    @PrePersist
    void prePersist() {
        if (dtOcorrencia == null) dtOcorrencia = LocalDateTime.now();
    }

    public Long getIdOcorrencia() { return idOcorrencia; }
    public void setIdOcorrencia(Long idOcorrencia) { this.idOcorrencia = idOcorrencia; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getNmTitulo() { return nmTitulo; }
    public void setNmTitulo(String nmTitulo) { this.nmTitulo = nmTitulo; }
    public String getDsDescricao() { return dsDescricao; }
    public void setDsDescricao(String dsDescricao) { this.dsDescricao = dsDescricao; }
    public SeveridadeOcorrencia getTpSeveridade() { return tpSeveridade; }
    public void setTpSeveridade(SeveridadeOcorrencia tpSeveridade) { this.tpSeveridade = tpSeveridade; }
    public StatusOcorrencia getStStatus() { return stStatus; }
    public void setStStatus(StatusOcorrencia stStatus) { this.stStatus = stStatus; }
    public LocalDateTime getDtOcorrencia() { return dtOcorrencia; }
    public void setDtOcorrencia(LocalDateTime dtOcorrencia) { this.dtOcorrencia = dtOcorrencia; }
    public LocalDateTime getDtResolucao() { return dtResolucao; }
    public void setDtResolucao(LocalDateTime dtResolucao) { this.dtResolucao = dtResolucao; }
}

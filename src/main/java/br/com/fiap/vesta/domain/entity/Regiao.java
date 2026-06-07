package br.com.fiap.vesta.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_REGIAO")
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regiao")
    private Long idRegiao;

    @Column(name = "nm_regiao", nullable = false, length = 100, unique = true)
    private String nmRegiao;

    @Column(name = "sg_estado", nullable = false, length = 2)
    private String sgEstado;

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private LocalDateTime dtCriacao;

    @PrePersist
    void prePersist() {
        if (dtCriacao == null) dtCriacao = LocalDateTime.now();
    }

    public Long getIdRegiao() { return idRegiao; }
    public void setIdRegiao(Long idRegiao) { this.idRegiao = idRegiao; }
    public String getNmRegiao() { return nmRegiao; }
    public void setNmRegiao(String nmRegiao) { this.nmRegiao = nmRegiao; }
    public String getSgEstado() { return sgEstado; }
    public void setSgEstado(String sgEstado) { this.sgEstado = sgEstado; }
    public LocalDateTime getDtCriacao() { return dtCriacao; }
}

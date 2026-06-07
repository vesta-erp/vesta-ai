package br.com.fiap.vesta.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ESTOQUE_ABRIGO",
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_abrigo", "id_recurso"}))
public class EstoqueAbrigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Long idEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    @Column(name = "qt_atual", nullable = false)
    private Integer qtAtual = 0;

    @Column(name = "qt_minima", nullable = false)
    private Integer qtMinima;

    @Column(name = "dt_atualizacao", nullable = false)
    private LocalDateTime dtAtualizacao;

    @PrePersist
    @PreUpdate
    void preUpdate() {
        dtAtualizacao = LocalDateTime.now();
    }

    public boolean isAbaixoMinimo() { return qtAtual < qtMinima; }

    public Long getIdEstoque() { return idEstoque; }
    public void setIdEstoque(Long idEstoque) { this.idEstoque = idEstoque; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public Recurso getRecurso() { return recurso; }
    public void setRecurso(Recurso recurso) { this.recurso = recurso; }
    public Integer getQtAtual() { return qtAtual; }
    public void setQtAtual(Integer qtAtual) { this.qtAtual = qtAtual; }
    public Integer getQtMinima() { return qtMinima; }
    public void setQtMinima(Integer qtMinima) { this.qtMinima = qtMinima; }
    public LocalDateTime getDtAtualizacao() { return dtAtualizacao; }
}

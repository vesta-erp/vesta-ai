package br.com.fiap.vesta.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_FAMILIA")
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_familia")
    private Long idFamilia;

    @Column(name = "nm_responsavel", nullable = false, length = 150)
    private String nmResponsavel;

    @Column(name = "nr_cpf_responsavel", length = 14)
    private String nrCpfResponsavel;

    @Column(name = "nr_telefone", length = 20)
    private String nrTelefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @Column(name = "dt_entrada", nullable = false)
    private LocalDateTime dtEntrada;

    @Column(name = "dt_saida")
    private LocalDateTime dtSaida;

    @PrePersist
    void prePersist() {
        if (dtEntrada == null) dtEntrada = LocalDateTime.now();
    }

    public boolean isPresente() { return dtSaida == null; }

    public Long getIdFamilia() { return idFamilia; }
    public void setIdFamilia(Long idFamilia) { this.idFamilia = idFamilia; }
    public String getNmResponsavel() { return nmResponsavel; }
    public void setNmResponsavel(String nmResponsavel) { this.nmResponsavel = nmResponsavel; }
    public String getNrCpfResponsavel() { return nrCpfResponsavel; }
    public void setNrCpfResponsavel(String nrCpfResponsavel) { this.nrCpfResponsavel = nrCpfResponsavel; }
    public String getNrTelefone() { return nrTelefone; }
    public void setNrTelefone(String nrTelefone) { this.nrTelefone = nrTelefone; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public LocalDateTime getDtEntrada() { return dtEntrada; }
    public void setDtEntrada(LocalDateTime dtEntrada) { this.dtEntrada = dtEntrada; }
    public LocalDateTime getDtSaida() { return dtSaida; }
    public void setDtSaida(LocalDateTime dtSaida) { this.dtSaida = dtSaida; }
}

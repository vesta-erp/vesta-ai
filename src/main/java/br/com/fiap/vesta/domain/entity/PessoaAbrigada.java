package br.com.fiap.vesta.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PESSOA_ABRIGADA")
public class PessoaAbrigada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Long idPessoa;

    @Column(name = "nm_pessoa", nullable = false, length = 150)
    private String nmPessoa;

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    @Column(name = "tp_documento", length = 20)
    private String tpDocumento;

    @Column(name = "nr_documento", length = 30)
    private String nrDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_familia", nullable = false)
    private Familia familia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo", nullable = false)
    private Abrigo abrigo;

    @Column(name = "st_presente", nullable = false, length = 1)
    private String stPresente = "S";

    @Column(name = "dt_entrada", nullable = false)
    private LocalDateTime dtEntrada;

    @Column(name = "dt_saida")
    private LocalDateTime dtSaida;

    @PrePersist
    void prePersist() {
        if (dtEntrada == null) dtEntrada = LocalDateTime.now();
    }

    public boolean isPresente() { return "S".equals(stPresente); }

    public Long getIdPessoa() { return idPessoa; }
    public void setIdPessoa(Long idPessoa) { this.idPessoa = idPessoa; }
    public String getNmPessoa() { return nmPessoa; }
    public void setNmPessoa(String nmPessoa) { this.nmPessoa = nmPessoa; }
    public LocalDate getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(LocalDate dtNascimento) { this.dtNascimento = dtNascimento; }
    public String getTpDocumento() { return tpDocumento; }
    public void setTpDocumento(String tpDocumento) { this.tpDocumento = tpDocumento; }
    public String getNrDocumento() { return nrDocumento; }
    public void setNrDocumento(String nrDocumento) { this.nrDocumento = nrDocumento; }
    public Familia getFamilia() { return familia; }
    public void setFamilia(Familia familia) { this.familia = familia; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public String getStPresente() { return stPresente; }
    public void setStPresente(String stPresente) { this.stPresente = stPresente; }
    public LocalDateTime getDtEntrada() { return dtEntrada; }
    public void setDtEntrada(LocalDateTime dtEntrada) { this.dtEntrada = dtEntrada; }
    public LocalDateTime getDtSaida() { return dtSaida; }
    public void setDtSaida(LocalDateTime dtSaida) { this.dtSaida = dtSaida; }
}

package br.com.fiap.vesta.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nm_usuario", nullable = false, length = 150)
    private String nmUsuario;

    @Column(name = "ds_email", nullable = false, length = 200, unique = true)
    private String dsEmail;

    @Column(name = "ds_senha_hash", nullable = false, length = 255)
    private String dsSenhaHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil", nullable = false)
    private PerfilAcesso perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abrigo")
    private Abrigo abrigo;

    @Column(name = "st_ativo", nullable = false, length = 1)
    private String stAtivo = "S";

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    private LocalDateTime dtCriacao;

    @PrePersist
    void prePersist() {
        if (dtCriacao == null) dtCriacao = LocalDateTime.now();
    }

    public boolean isAtivo() { return "S".equals(stAtivo); }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNmUsuario() { return nmUsuario; }
    public void setNmUsuario(String nmUsuario) { this.nmUsuario = nmUsuario; }
    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }
    public String getDsSenhaHash() { return dsSenhaHash; }
    public void setDsSenhaHash(String dsSenhaHash) { this.dsSenhaHash = dsSenhaHash; }
    public PerfilAcesso getPerfil() { return perfil; }
    public void setPerfil(PerfilAcesso perfil) { this.perfil = perfil; }
    public Abrigo getAbrigo() { return abrigo; }
    public void setAbrigo(Abrigo abrigo) { this.abrigo = abrigo; }
    public String getStAtivo() { return stAtivo; }
    public void setStAtivo(String stAtivo) { this.stAtivo = stAtivo; }
    public LocalDateTime getDtCriacao() { return dtCriacao; }
}

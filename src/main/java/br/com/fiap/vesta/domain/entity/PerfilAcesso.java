package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.NomePerfil;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_PERFIL_ACESSO")
public class PerfilAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "nm_perfil", nullable = false, length = 50, unique = true)
    private NomePerfil nmPerfil;

    @Column(name = "ds_permissoes", length = 500)
    private String dsPermissoes;

    public Long getIdPerfil() { return idPerfil; }
    public void setIdPerfil(Long idPerfil) { this.idPerfil = idPerfil; }
    public NomePerfil getNmPerfil() { return nmPerfil; }
    public void setNmPerfil(NomePerfil nmPerfil) { this.nmPerfil = nmPerfil; }
    public String getDsPermissoes() { return dsPermissoes; }
    public void setDsPermissoes(String dsPermissoes) { this.dsPermissoes = dsPermissoes; }
}

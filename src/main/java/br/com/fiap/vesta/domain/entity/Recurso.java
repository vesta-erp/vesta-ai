package br.com.fiap.vesta.domain.entity;

import br.com.fiap.vesta.domain.enums.TipoRecurso;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_RECURSO")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private Long idRecurso;

    @Column(name = "nm_recurso", nullable = false, length = 150, unique = true)
    private String nmRecurso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_recurso", nullable = false, length = 50)
    private TipoRecurso tpRecurso;

    @Column(name = "ds_unidade_medida", nullable = false, length = 30)
    private String dsUnidadeMedida;

    public Long getIdRecurso() { return idRecurso; }
    public void setIdRecurso(Long idRecurso) { this.idRecurso = idRecurso; }
    public String getNmRecurso() { return nmRecurso; }
    public void setNmRecurso(String nmRecurso) { this.nmRecurso = nmRecurso; }
    public TipoRecurso getTpRecurso() { return tpRecurso; }
    public void setTpRecurso(TipoRecurso tpRecurso) { this.tpRecurso = tpRecurso; }
    public String getDsUnidadeMedida() { return dsUnidadeMedida; }
    public void setDsUnidadeMedida(String dsUnidadeMedida) { this.dsUnidadeMedida = dsUnidadeMedida; }
}

package academico.controleinterno.cdp;

import academico.util.academico.cdp.AreaConhecimento;
import academico.util.persistencia.ObjetoPersistente;
import java.util.List;
import javax.persistence.*;

/**
 * Representa as disciplinas de um curso da instituição.
 *
 * @author FS
 */
@Entity
public class Disciplina extends ObjetoPersistente {
    private String nome;
    private Integer cargaHoraria;
    private Integer numCreditos;
    private List<AreaConhecimento> areaConhecimento;
    private Integer periodoCorrespondente;
    private List<Disciplina> prerequisito;
    private Curso curso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(Integer numCreditos) {
        this.numCreditos = numCreditos;
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "DisciplinaConhecimento",
    joinColumns = {
        @JoinColumn(name = "disciplina_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "aconhecimento_id")})
    public List<AreaConhecimento> getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(List<AreaConhecimento> areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public Integer getPeriodoCorrespondente() {
        return periodoCorrespondente;
    }

    public void setPeriodoCorrespondente(Integer periodoCorrespondente) {
        this.periodoCorrespondente = periodoCorrespondente;
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "PreRequisito",
    joinColumns = {
        @JoinColumn(name = "disciplina_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "prerequisito_id")})
    public List<Disciplina> getPrerequisito() {
        return prerequisito;
    }

    public void setPrerequisito(List<Disciplina> prerequisito) {
        this.prerequisito = prerequisito;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
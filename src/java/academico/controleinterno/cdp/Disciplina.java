/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cdp;

import academico.util.academico.cdp.AreaConhecimento;
import academico.util.persistencia.ObjetoPersistente;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Administrador
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

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(Integer numCreditos) {
        this.numCreditos = numCreditos;
    }

    @ManyToMany(cascade= CascadeType.PERSIST)
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @ManyToMany(cascade= CascadeType.PERSIST)
    @JoinTable(name = "prerequisito",
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

    @Override
    public String toString() {
        return this.nome;
    }
}
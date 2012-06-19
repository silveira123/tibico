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
    private int cargaHoraria;
    private int numCreditos;
    private AreaConhecimento areaConhecimento;
    private int periodoCorrespondente;
    private List<Disciplina> prerequisito;
    private Curso curso;

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(int numCreditos) {
        this.numCreditos = numCreditos;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public AreaConhecimento getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public int getPeriodoCorrespondente() {
        return periodoCorrespondente;
    }

    public void setPeriodoCorrespondente(int periodoCorrespondente) {
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
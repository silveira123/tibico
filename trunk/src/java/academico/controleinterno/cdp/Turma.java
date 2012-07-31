/*
 * Aluno.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:18:24
 * Copyright (c) 2012 Fabrica de Software IFES.
 * Incubadora de Empresas IFES, sala 11
 * Rodovia ES-010 - Km 6,5 - Manguinhos, Serra, ES, 29164-321, Brasil.
 * All rights reserved.
 *
 * This software is the confidential and proprietary 
 * information of Fabrica de Software IFES. ("Confidential Information"). You 
 * shall not disclose such Confidential Information and 
 * shall use it only in accordance with the terms of the 
 * license agreement you entered into with Fabrica de Software IFES.
 */

package academico.controleinterno.cdp;

import academico.util.horario.cdp.Horario;
import academico.util.persistencia.ObjetoPersistente;
import java.util.List;
import javax.persistence.*;


/**
 * Esta classe descreve as informações de um aluno, a classe possui a associação com um Curso 
 * e faz herança com Pessoa, do pacote de utilitários, contemplando informações necessárias para 
 * o registro do aluno.
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see academico.controleinterno.cdp.Aluno
 */
@Entity
public class Turma extends ObjetoPersistente {

    private Integer numVagas;
    private Calendario calendario;
    private Disciplina disciplina;
    private List<Horario> horario;
    private Professor professor;
    private SituacaoTurma estadoTurma;
    
    public Turma() {
        
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public SituacaoTurma getEstadoTurma() {
        return estadoTurma;
    }

    public void setEstadoTurma(SituacaoTurma estadoTurma) {
        this.estadoTurma = estadoTurma;
    }
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
    
    @ManyToMany(cascade= CascadeType.PERSIST, fetch= FetchType.EAGER)
    @JoinTable(name = "turmaHorario",
    joinColumns = {
        @JoinColumn(name = "id_turma")},
    inverseJoinColumns = {
        @JoinColumn(name = "id_horario")})
    public List<Horario> getHorario() {
        return horario;
    }
    public void setHorario(List<Horario> horario) {
        this.horario = horario;
    }

    public Integer getNumVagas() {
        return numVagas;
    }

    public void setNumVagas(Integer numVagas) {
        this.numVagas = numVagas;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    @Override
    public String toString() {
        return this.disciplina+" "+this.calendario;
    }
}
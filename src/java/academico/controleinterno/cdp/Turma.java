/*
 * Turma.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:18:31
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
import java.util.ArrayList;
import javax.persistence.*;


/**
 * Esta classe descreve as informações de uma turma, a classe possui a associação com Disciplina
 * representando a respectiva disciplina, além do (os) horários, na associação com a classe Horario
 * do pacote de utilitários e uma associação para CalendarioAcademico
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see academico.controleinterno.cdp.Turma
 */
@Entity
public class Turma extends ObjetoPersistente
{
    
    private int numVagas;
    private Disciplina disciplina;
    private ArrayList<Horario> horario;
    
    public Turma() {
    }

    /**
     * Obtém disciplina de Turma
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Disciplina getDisciplina() {
        return disciplina;
    }

    /**
     * Altera disciplina em Turma
     * @param disciplina 
     */
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    /**
     * Obtém horario de Turma
     * @return 
     */
    @OneToMany(mappedBy = "turma", cascade= CascadeType.ALL) 
    public ArrayList<Horario> getHorario() {
        return horario;
    }

    /**
     * Altera horario em Turma
     * @param horario 
     */
    public void setHorario(ArrayList<Horario> horario) {
        this.horario = horario;
    }

    /**
     * Obtém numVagas de Turma
     * @return 
     */
    public int getNumVagas() {
        return numVagas;
    }

    /**
     * Altera numVagas em Turma
     * @param numVagas 
     */
    public void setNumVagas(int numVagas) {
        this.numVagas = numVagas;
    }
    
}
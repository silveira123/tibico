/*
 * Frequencia.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:22:39
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

package academico.controlepauta.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * Esta classe representa a quantidade de faltas de um aluno em uma aula da respectiva turma
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see academico.controlepauta.cdp.Frequencia.java
 */
@Entity
public class Frequencia extends ObjetoPersistente implements Cloneable{

    private Integer numFaltasAula;
    private MatriculaTurma matriculaTurma;

    /**
     * Obtém o número de faltas de um Aluno em uma Aula
     * @return 
     */
    public Integer getNumFaltasAula() {
        return numFaltasAula;
    }

    /**
     * Altera o valor do número de faltas de um Aluno em uma Aula
     * @param numFaltasAula 
     */
    public void setNumFaltasAula(Integer numFaltasAula) {
        this.numFaltasAula = numFaltasAula;
    }
        
    /**
     * Obtém o valor da variável que representa a ligação de uma Matrícula a uma determinada Turma
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public MatriculaTurma getMatriculaTurma() {
        return matriculaTurma;
    }

    /**
     * Altera o valor da variável que representa a ligação de uma Matrícula a uma determinada Turma
     * @param matriculaTurma 
     */
    public void setMatriculaTurma(MatriculaTurma matriculaTurma) {
        this.matriculaTurma = matriculaTurma;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
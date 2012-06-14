/*
 * MatriculaTurma.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:28:57
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

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.*;

/**
 * Esta classe foi criada para conter os valores calculados do resultado final de um Aluno numa Turma, bem como seu percentual de presença.
 * 
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
@Entity
public class MatriculaTurma extends ObjetoPersistente {

    private double resultadoFinal;
    private double percentualPresenca;
    private Aluno aluno;
    private Turma turma;
    private SituacaoAlunoTurma situacaoAluno;

   /**
    * Obtém o Aluno da relação MatriculaTurma
    * @return 
    */ 
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * Altera o valor de Aluno numa relação MatriculaTurma
     * @param aluno 
     */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    /**
     * Obtém o percentual de presença de um Aluno numa Turma
     * @return 
     */
    public double getPercentualPresenca() {
        return percentualPresenca;
    }

    /**
     * Altera o valor do percentual de presença de um Aluno numa Turma
     * @param percentualPresenca 
     */
    public void setPercentualPresenca(double percentualPresenca) {
        this.percentualPresenca = percentualPresenca;
    }

    /**
     * Obtém o resultado final de um Aluno numa Turma
     * @return 
     */
    public double getResultadoFinal() {
        return resultadoFinal;
    }

    /**
     * Altera o valor do resultado final de um Aluno numa Turma
     * @param resultadoFinal 
     */
    public void setResultadoFinal(double resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    /**
     * Obtém a situação de um Aluno com relação à uma Turma
     * @return 
     */
    @Enumerated(EnumType.STRING)
    public SituacaoAlunoTurma getSituacaoAluno() {
        return situacaoAluno;
    }

    /**
     * Altera o valor da situação de um Aluno com relação à uma Turma
     * @param situacaoAluno 
     */
    public void setSituacaoAluno(SituacaoAlunoTurma situacaoAluno) {
        this.situacaoAluno = situacaoAluno;
    }

    /**
     * Obtém a Turma da relação MatriculaTurma
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Turma getTurma() {
        return turma;
    }
    
    /**
     * Altera o valor da Turma numa relação MatriculaTurma
     * @param turma 
     */
    public void setTurma(Turma turma) {
        this.turma = turma;
    }
}
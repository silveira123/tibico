/*
 * Resultado.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:20:45
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
 * Esta classe representa o resultado de uma Avaliacao feita por um Aluno de uma determinada Turma (MatriculaTurma).
 * 
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
@Entity
public class Resultado extends ObjetoPersistente{

    private Double pontuacao;
    private String observacao;
    private Avaliacao avaliacao;
    private MatriculaTurma matriculaTurma;

    /**
     * Construtor padrão para Resultado
     */
    public Resultado() {
    }

    /**
     * Construtor para Resultado
     * @param avaliacao
     * @param matriculaTurma 
     */
    public Resultado(Avaliacao avaliacao, MatriculaTurma matriculaTurma){
        this.pontuacao = null;
        this.observacao = "";
        this.avaliacao = avaliacao;
        this.matriculaTurma = matriculaTurma;
    }
    
   /**
    * Obtém a Avaliação relativa ao Resultado em questão
    * @return 
    */    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    /**
     * Altera o valor da Avaliacao relativa ao Resultado em questão
     * @param avaliacao 
     */
    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * Obtém a relação MatriculaTurma relativa ao Resultado em questão
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public MatriculaTurma getMatriculaTurma() {
        return matriculaTurma;
    }

    /**
     * Altera o valor da relação MatriculaTurma relativa ao Resultado em questão
     * @param matriculaTurma 
     */
    public void setMatriculaTurma(MatriculaTurma matriculaTurma) {
        this.matriculaTurma = matriculaTurma;
    }

    /**
     * Obtém a observação feita para o determinado Resultado
     * @return 
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * Altera a observação do Resultado
     * @param observacao 
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * Obtém a pontuação relacionada ao Resultado
     * @return 
     */
    public Double getPontuacao() {
        return pontuacao;
    }

    /**
     * Altera o valor da pontuação relacionada ao Resultado
     * @param pontuacao 
     */
    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }
}
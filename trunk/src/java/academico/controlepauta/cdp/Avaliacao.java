/*
 * Avaliacao.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:15:41
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

import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Esta classe representa uma avaliação criada por um Professor para uma determinada Turma.
 * 
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
@Entity
public class Avaliacao extends ObjetoPersistente {

    private String nome;
    private Integer peso;
    private Turma turma;

   /**
    * Obtém o nome de uma Avaliação
    * @return 
    */
    public String getNome() {
        return nome;
    }
    
    /**
     * Altera o valor do nome de uma Avaliação
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Obtém o peso de uma Avaliação
     * @return 
     */
    public Integer getPeso() {
        return peso;
    }
    
    /**
     * Altera o valor do peso de uma Avaliação
     * @param peso 
     */
    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    /**
     * Obtém a Turma da qual a Avaliação faz parte
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Turma getTurma() {
        return turma;
    }

    /**
     * Altera a Turma da qual a Avliação faz parte
     * @param turma 
     */
    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
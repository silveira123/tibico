/*
 * Aula.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:16:06
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
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;


/**
 * Esta classe descreve uma aula e está acossiada ao respectivo curso, abrangendo a lista de Frequencia
 * dos respectivos alunos a está turma, matriculados.
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Aula extends ObjetoPersistente {

    private Calendar dia;
    private Integer quantidade;
    private String conteudo;
    private Turma turma;
    private List<Frequencia> frequencia;
    
    public Aula() {
    }

   /**
    * Obtém o conteudo de Aula
    * @return 
    */
    public String getConteudo() {
        return conteudo;
    }

    /**
     * Altera o valor do conteudo de Aula
     * @param conteudo 
     */
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    /**
     * Obtém a dia de Aula
     * @return 
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDia() {
        return dia;
    }

    /**
     * Altera o valor da dia de uma Aula
     * @param dia 
     */
    public void setDia(Calendar dia) {
        this.dia = dia;
    }

    /**
     * Obtém a quantidade de aulas
     * @return 
     */
    public Integer getQuantidade() {
        return quantidade;
    }

    /**
     * Altera o valor da quantidade de aulas
     * @param quantidade 
     */
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém a Turma a qual a Aula se refere
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public Turma getTurma() {
        return turma;
    }

    /**
     * Altera o valor da Turma a qual a Aula se refere
     * @param turma 
     */
    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    /**
     * Obtém a lista de frequências da Aula
     * @return 
     */
    
    //TODO consertar a annotation para tirar a tabela aux do banco
    @OneToMany(cascade= CascadeType.PERSIST, fetch= FetchType.EAGER)
    public List<Frequencia> getFrequencia() {
        return frequencia;
    }

    /**
     * Altera o valor da lista de frequências de Aula
     * @param frequencia 
     */
    public void setFrequencia(List<Frequencia> frequencia) {
        this.frequencia = frequencia;
    }

    
}
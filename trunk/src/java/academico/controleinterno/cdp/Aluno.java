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

import academico.util.pessoa.cdp.Pessoa;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * Esta classe descreve as informações de um aluno, a classe possui a associação com um Curso 
 * e faz herança com Pessoa, do pacote de utilitários, contemplando informações necessárias para 
 * o registro do aluno.
 * 
 * @author Gabriel Quézid
 * @author Rodrigo Maia
 * @version 0.1
 * @see academico.controleinterno.cdp.Aluno
 */
@Entity
public class Aluno extends Pessoa{

    private String matricula;
    private Double coeficiente;
    private String nomePai;
    private String nomeMae;
    private Curso curso;
    
    public Aluno() {
        
    }

    /**
     * Obtém coeficiente de Aluno
     * @return 
     */
    public Double getCoeficiente() {
        return coeficiente;
    }

    /**
     * Altera o valor de coeficiente em Aluno
     * @param coeficiente 
     */
    public void setCoeficiente(Double coeficiente) {
        this.coeficiente = coeficiente;
    }

    /**
     * Obtém curso de Aluno
     * @return 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
    public Curso getCurso() {
        return curso;
    }

    /**
     * Altera o curso em Aluno
     * @param curso 
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * Obtém matricula de Aluno
     * @return 
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Altera o valor de matricula em aluno
     * @param matricula 
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtém o nomeMae (nome da mãe) de Aluno
     * @return 
     */
    public String getNomeMae() {
        return nomeMae;
    }

    /**
     * Altera o valor do nomeMae em Aluno
     * @param nomeMae 
     */
    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    /**
     * Obtém o nomePai (nome do pai) de Aluno
     * @return 
     */
    public String getNomePai() {
        return nomePai;
    }

    /**
     * Altera o nomePai em Aluno
     * @param nomePai 
     */
    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
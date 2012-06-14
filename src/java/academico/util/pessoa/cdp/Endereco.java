/*
 * Pais.java 
 * Versão: 0.1 
 * Data de Criação : 29/05/2012
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

package academico.util.pessoa.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Esta classe representa o endereço de Pessoa, abrangendo as principais informações do endereço
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Endereco  extends ObjetoPersistente{
    private String logradouro;
    private int cep;
    private int numero;
    private String Complemento;
    private Bairro bairro;

    /**
     * Obtém complemento de Endereco
     * @return 
     */
    public String getComplemento() {
        return Complemento;
    }

    /**
     * Altera o valor do complemento em Endereco
     * @param Complemento 
     */
    public void setComplemento(String Complemento) {
        this.Complemento = Complemento;
    }

    /**
     * Obtém cep de Endereco
     * @return 
     */
    public int getCep() {
        return cep;
    }

    /**
     * Altera o valor do cep em Endereco
     * @param cep 
     */
    public void setCep(int cep) {
        this.cep = cep;
    }

    /**
     * Obtém logradouro de Endereco
     * @return 
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Alterar o valor de logradouro em Endereco
     * @param logradouro 
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * Obtém numero de Endereco
     * @return 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Altera o valor de numero em Endereco
     * @param numero 
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    /**
     * Obtém bairro de Endereco
     * @return 
     */
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Bairro getBairro() {
        return bairro;
    }

    /**
     * Altera o valor do bairro em Endereco
     * @param bairro 
     */
    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
}

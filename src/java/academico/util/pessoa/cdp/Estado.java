/*
 * Estado.java 
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
 * Esta classe descreve um estado e o respectivo pais
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Estado  extends ObjetoPersistente{
    private Pais pais;
    private String nome;
    private String sigla;

    /**
     * Obtém nome em Estado
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o valor do nome em Estado
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Obtém pais em Estado
     * @return 
     */
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Pais getPais() {
        return pais;
    }

    /**
     * Altera o valor do respectivo pais vinculado ao estado em Estado
     * @param pais 
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * Obtém sigla que representa o estado em Estado
     * @return 
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Altera a sigla que representa o estado em Estado
     * @param sigla 
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
}

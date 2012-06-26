/*
 * Telefone.java 
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
import javax.persistence.Entity;

/**
 * Esta classe descreve o número de telefone de uma pessoa, assim como o DDD (Discagem direta a distância) 
 * e DDI (Discagem Direta Internacional)
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Telefone extends ObjetoPersistente{
    private Integer numero;
    private Integer ddd;
    private Integer ddi;

    /**
     * Obtém o DDD de Telefone.
     */
    public Integer getDdd() {
        return ddd;
    }

    /**
     * Altera o valor do DDD de Telefone.
     */
    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    /**
     * Obtém o DDI de Telefone.
     */
    public Integer getDdi() {
        return ddi;
    }

    /**
     * Altera o valor do DDI de Telefone.
     */
    public void setDdi(Integer ddi) {
        this.ddi = ddi;
    }

    /**
     * Obtém o número (numero) de Telefone.
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Altera o valor do número (numero) de Telefone.
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
}
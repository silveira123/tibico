/*
 * GrandeAreaConhecimento.java 
 * Versão: 0.1 
 * Data de Criação : 29/05/2012, 14:36:06
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

package academico.util.academico.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.Entity;

/**
 * Esta classe descreve as Grandes Áreas do Conhecimento do CNPQ
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class GrandeAreaConhecimento extends ObjetoPersistente{
    private String nome;

    /**
     * Retorna o nome de GrandeAreaConhecimento.
     */
    public String getNome() {
        return nome;
    }

    /*
     * Obtém o nome de GrandeAreaConhecimento.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna o nome de GrandeAreaConhecimento no formato de uma string
     * @return 
     */
    @Override
    public String toString()
    {
        return nome;
    }
}

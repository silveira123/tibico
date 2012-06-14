/*
 * AreaConhecimento.java 
 * Versão: 0.1 
 * Data de Criação : 29/05/2012, 14:16:07
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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Esta classe descreve as Áreas do Conhecimento do CNPQ 
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class AreaConhecimento extends ObjetoPersistente {

    private String nome;
    private GrandeAreaConhecimento gAreaConhecimento;

    /**
     * Retorna a respectiva GrandeAreaConhecimento de AreaConhecimento.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public GrandeAreaConhecimento getgAreaConhecimento() {
        return gAreaConhecimento;
    }

    /**
     * Obtém a GrandeAreaConhecimento referente à AreaConhecimento.
     */
    public void setgAreaConhecimento(GrandeAreaConhecimento gAreaConhecimento) {
        this.gAreaConhecimento = gAreaConhecimento;
    }

    /** 
     * Retorna o nome de AreaConhecimento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém nome de AreaConhecimento.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o nome de AreaConhecimento no formato de uma string
     * @return 
     */
    @Override
    public String toString() {
        return nome;
    }
}

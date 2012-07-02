/*
 * Municipio.java 
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
 * Esta classe descreve um município e o respectivo estado
 * 
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Municipio extends ObjetoPersistente{
    private Estado estado;
    private String nome;
    
    /**
     * Obtém o estado do Municipio
     * @return 
     */
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Estado getEstado() {
        return estado;
    }

    /**
     * Altera o valor do estado em Municipio
     * @param estado 
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Obtém o nome do Municipio
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o valor do nome de Municipio
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString(){
        return nome;
    }
    
}

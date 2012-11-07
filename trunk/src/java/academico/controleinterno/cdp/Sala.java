/*
 * Sala.java 
 * Versão: 0.1 
 * Data de Criação : 26/09/2012, 13:23:51
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

/**
 * Esta classe descreve as informações de uma sala, a classe possui a associação com uma ou mais Turmas 
 * contemplando informações necessárias para a criação de uma sala
 * 
 * @author Gabriel Miranda
 * @version 0.1
 * @see academico.controleinterno.cdp.Sala
 */

import academico.util.persistencia.ObjetoPersistente;
import java.util.List;
import javax.persistence.*;



@Entity
public class Sala extends ObjetoPersistente{
    private Integer numBloco;
    private Integer numSala;
    private List<Equipamento> equipamentoSala;

    public Sala() {
    }
    
    

    public Integer getNumBloco() {
        return numBloco;
    }

    public void setNumBloco(Integer numBloco) {
        this.numBloco = numBloco;
    }

    public Integer getNumSala() {
        return numSala;
    }

    public void setNumSala(Integer numSala) {
        this.numSala = numSala;
    }
    @ManyToMany(cascade= CascadeType.PERSIST, fetch= FetchType.EAGER)
    @JoinTable(name = "EquipamentoSala",
    joinColumns = {
        @JoinColumn(name = "id_sala")},
    inverseJoinColumns = {
        @JoinColumn(name = "id_equipamento")})
    public List<Equipamento> getEquipamentoSala() {
        return equipamentoSala;
    }

    public void setEquipamentoSala(List<Equipamento> equipamentoSala) {
        this.equipamentoSala = equipamentoSala;
    }

    @Override
    public String toString() {
        if(this.numSala<10) 
            return this.numBloco.toString() +0+ this.numSala.toString();
        return this.numBloco.toString() + this.numSala.toString();
    }
    
    
}

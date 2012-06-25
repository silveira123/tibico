/*
 * Professor.java 
 * Versão: 0.1 
 * Data de Criação : 13/06/2012, 15:24:50
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

import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.pessoa.cdp.Pessoa;
import java.util.ArrayList;
import javax.persistence.*;


/**
 * Esta classe descreve as informações de um professor, a classe possui a associação com GrauInstrucao 
 * e AreaConhecimento e faz herança com Pessoa, do pacote de utilitários, contemplando informações 
 * necessárias para o registro do professor.
 * 
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
@Entity
public class Professor extends Pessoa{
    
    private GrauInstrucao grauInstrucao;
    private ArrayList<AreaConhecimento> areaConhecimento;
    
    public Professor() {
    }

    /**
     * Obtém o conjunto de areaConhecimento de Professor
     * @return 
     */
    @ManyToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinTable(name = "professorConhecimento",
    joinColumns = {
        @JoinColumn(name = "id_pessoa")},
    inverseJoinColumns = {
        @JoinColumn(name = "id_aconhecimento")})
    public ArrayList<AreaConhecimento> getAreaConhecimento() {
        return areaConhecimento;
    }

    /**
     * Altera o valor de areaConhecimento em Professor
     * @param areaConhecimento 
     */
     public void setAreaConhecimento(ArrayList<AreaConhecimento> areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    /**
     * Obtém grauInstrucao de Professor
     * @return 
     */
    @Enumerated(EnumType.STRING)
    public GrauInstrucao getGrauInstrucao() {
        return grauInstrucao;
    }

    /**
     * Altera o grauInstrucao de Professor
     * @param grauInstrucao 
     */
    public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }
  
}
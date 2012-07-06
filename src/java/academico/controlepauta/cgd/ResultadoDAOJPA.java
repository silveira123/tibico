/*
 * ResultadoDAOJPA.java 
 * Versão: _._ 
 * Data de Criação : 11/06/2012, 13:11:34
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

package academico.controlepauta.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.Resultado;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

/**
 * Esta classe faz herança com DAOJPA e implementa ResultadoDAO
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public class ResultadoDAOJPA extends DAOJPA<Resultado> implements ResultadoDAO {
    
    public List<Resultado> obterResultados(Turma t) { 
         List<Resultado> resultado = entityManager.createQuery("select fr from Resultado fr, MatriculaTurma mt where mt.turma.id = ?1").setParameter(1, t.getId()).getResultList();
         return resultado;
    }
    
    public List<Resultado> obterResultados(Aluno a) { 
         List<Resultado> resultado = entityManager.createQuery("select fr from Resultado fr, MatriculaTurma mt where mt.aluno.id = ?1 and fr.matriculaTurma.id = mt.id").setParameter(1, a.getId()).getResultList();
          return resultado;
    }
    
    public List<Resultado> obterResultados(MatriculaTurma mturma) { 
         List<Resultado> resultado = entityManager.createQuery("select fr from Resultado fr, MatriculaTurma mt where mt.id = ?1").setParameter(1, mturma.getId()).getResultList();
         return resultado;
    }
}
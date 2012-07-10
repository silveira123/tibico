/*
 * AlunoDAOJPA.java 
 * Versão: _._ 
 * Data de Criação : 13/06/2012, 15:44:55
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

package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.DAOJPA;
import java.util.List;


/**
 * Esta classe faz herança com DAOJPA e implementa AlunoDAO
 * 
 * @author Fábrica de Software
 * @version
 * @see
 */
public class AlunoDAOJPA extends DAOJPA<Aluno> implements AlunoDAO{
    
     public List<Aluno> obterAlunosporTurma(Turma t) {
         List<Aluno> aluno = entityManager.createQuery("select mt.aluno a from MatriculaTurma mt where mt.turma.id = ?1").setParameter(1, t.getId()).getResultList();
         return aluno;
    }

    public Aluno obterAluno(String matricula) {
        List<Aluno> aluno = entityManager.createQuery("select mt a from Aluno mt where mt.matricula = ?1").setParameter(1, matricula).getResultList();
        return aluno.get(0);
    }
    
    public Aluno obterAluno(Long id) {
        List<Aluno> aluno = entityManager.createQuery("select mt a from Aluno mt where mt.id = ?1").setParameter(1, id).getResultList();
        return aluno.get(0);
    }

}
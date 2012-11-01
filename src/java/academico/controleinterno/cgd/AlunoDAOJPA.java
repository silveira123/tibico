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
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

/**
 * Esta classe faz herança com DAOJPA e implementa AlunoDAO
 * <p/>
 * @author Fábrica de Software
 * @version
 * @see
 */
public class AlunoDAOJPA extends DAOJPA<Aluno> implements AlunoDAO {

    public List<Aluno> obterAlunosporTurma(Turma t) {
        List<Aluno> aluno = entityManager.createQuery("select mt.aluno from MatriculaTurma mt where mt.turma.id = ?1 ORDER BY mt.aluno.nome").setParameter(1, t.getId()).getResultList();
        return aluno;
    }
    
    public List<Aluno> obterAlunosporCurso(Curso c) {
        Query query = entityManager.createQuery("select a from Aluno a where a.curso.id = ?1");
        query.setParameter(1, c.getId());
        return (List<Aluno>) query.getResultList();
    }
    
    public Aluno obterAluno(Long id) {
        Query query = entityManager.createQuery("select mt from Aluno mt where mt.id = ?1");
        query.setParameter(1, id);
        return (Aluno) query.getSingleResult();
    }

    //Retorna os alunos pesquisados por nome ou matricula
    public List<Aluno> obterAluno(String parametro) {
        Query query = entityManager.createQuery("select distinct a from Pessoa p, Aluno a where p.id = a.id and (lower(p.nome) like lower('%" + parametro + "%') or lower(a.matricula) like lower('%" + parametro + "%'))");
        return (List<Aluno>) query.getResultList();
    }
}
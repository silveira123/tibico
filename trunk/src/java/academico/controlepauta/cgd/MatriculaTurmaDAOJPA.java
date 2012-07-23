/*
 * MatriculaTurmaDAOJPA.java 
 * Versão: 0.1 
 * Data de Criação : 11/06/2012, 13:17:10
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
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.SituacaoAlunoTurma;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;


/**
 * Esta classe faz herança com DAOJPA e implementa MatriculaTurmaDAO
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public class MatriculaTurmaDAOJPA extends DAOJPA<MatriculaTurma> implements MatriculaTurmaDAO {

    /**
     * Obtém todas as matriculaTurma de um aluno
     * @param aluno
     * @return 
     */
    public List<MatriculaTurma> obter(Aluno aluno) {
        Query query = entityManager.createQuery("Select mt from MatriculaTurma mt where mt.aluno.id = ?1" );
        query.setParameter(1, aluno.getId());
        return query.getResultList();
    }
    
    /**
     * Obtém todas as MatriculaTurma, através de um aluno com uma situação acadêmica especifica
     * @param aluno
     * @param situacao
     * @return 
     */
    public List<MatriculaTurma> obter(Aluno aluno, SituacaoAlunoTurma situacao) {
        Query query = entityManager.createQuery("Select mt from MatriculaTurma mt where mt.aluno.id = ?1 and mt.situacaoAluno = ?2" );
        query.setParameter(1, aluno.getId());
        query.setParameter(2, situacao);
        return query.getResultList();
    }
    
    /**
     * Obtém todas as MatriculaTurma, através de um aluno e calendário especifico
     * @param aluno
     * @param calendario
     * @return 
     */
    public List<MatriculaTurma> obter(Aluno aluno, Calendario calendario) {
        Query query = entityManager.createQuery("Select mt from MatriculaTurma mt where mt.aluno.id = ?1 and mt.turma.calendario.id = ?2" );
        query.setParameter(1, aluno.getId());
        query.setParameter(2, calendario.getId());
        return query.getResultList();
    }
    
    /**
     * Obtém todas as MatriculaTurma de uma turma
     * @param t
     * @return 
     */
    public List<MatriculaTurma> obter(Turma t) {
         List<MatriculaTurma> matriculaturma = entityManager.createQuery("select mt from MatriculaTurma mt where mt.turma.id = ?1 order by mt.aluno.nome").setParameter(1, t.getId()).getResultList();
         return matriculaturma;
    }

    /**
     * Obtém todos os calendários, através de um aluno
     * @param aluno
     * @return 
     */
    public List<Calendario> obterCalendarios(Aluno aluno) {
        Query query = entityManager.createQuery("Select distinct mt.turma.calendario from MatriculaTurma mt, Turma t, Calendario c where mt.turma.id = t.id and "
                                                + "t.calendario.id = c.id and mt.aluno.id = ?1" );
        query.setParameter(1, aluno.getId());
        return query.getResultList();
    }
    
    /**
     * Obtém todas as matriculaTurma de um aluno, em turmas que já foram finalizadas
     * @param aluno
     * @return 
     */
    public List<MatriculaTurma> obterCursadas(Aluno aluno) {
        Query query = entityManager.createQuery("Select mt from MatriculaTurma mt where mt.aluno.id = ?1 and (mt.situacaoAluno = ?2 or mt.situacaoAluno = ?3 or mt.situacaoAluno = ?4)" );
        query.setParameter(1, aluno.getId());
        query.setParameter(2, SituacaoAlunoTurma.APROVADO);
        query.setParameter(3, SituacaoAlunoTurma.REPROVADOFALTA);
        query.setParameter(4, SituacaoAlunoTurma.REPROVADONOTA);
        return query.getResultList();
    }
}

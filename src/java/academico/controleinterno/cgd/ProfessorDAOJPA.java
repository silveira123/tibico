/*
 * ProfessorDAOJPA.java 
 * Versão: _._ 
 * Data de Criação : 13/06/2012, 15:48:50
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
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Professor;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

/**
 * Esta classe faz herança com DAOJPA e implementa ProfessorDAOJPA
 *
 * @author Fábrica de Software
 * @version
 * @see
 */
public class ProfessorDAOJPA extends DAOJPA<Professor> implements ProfessorDAO {

    public Professor obterProfessor(Long id) {
        Query query = entityManager.createQuery("select mt from Professor mt where mt.id = ?1");
        query.setParameter(1, id);
        return (Professor) query.getSingleResult();
    }

    public List<Professor> obterProfessor(Calendario c) {
        Query query = entityManager.createQuery("select distinct t.professor from Turma t where t.calendario.id = ?1");
        query.setParameter(1, c.getId());
        return query.getResultList();
    }
    
    //Retorna os alunos pesquisados por nome ou matricula
    public List<Professor> obterProfessor(String nome) {
        //Query query = entityManager.createQuery("select distinct a from Pessoa p, Professor a where p.id = a.id and (p.nome like '%" + nome + "%')");
        Query query = entityManager.createQuery("select distinct a from Pessoa p, Professor a where p.id = a.id and (lower(p.nome) like lower('%" + nome + "%') or lower(a.grauInstrucao) like lower('%" + nome + "%'))");
        return (List<Professor>) query.getResultList();
    }
}
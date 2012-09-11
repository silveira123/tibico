/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgd;

import academico.controleinterno.cdp.*;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

public class TurmaDAOJPA extends DAOJPA<Turma> implements TurmaDAO {

    public List<Turma> obterTurmasAtuais(Aluno aluno) {
        javax.persistence.Query query = entityManager.createQuery("SELECT t FROM Aluno a, Curso c, Disciplina d, Turma t, Calendario cal WHERE a.id = ?1 AND t.disciplina.id = d.id AND d.curso.id = c.id AND d.curso.id = a.curso.id AND t.calendario.id = cal.id AND cal.situacao = ?2");
        query.setParameter(1, aluno.getId());
        query.setParameter(2, SituacaoCalendario.ABERTO); 
        List<Turma> list = query.getResultList();
        return list;
    }

    public List<Turma> obter(Calendario calendario) {
        javax.persistence.Query query = entityManager.createQuery("SELECT t FROM Turma t WHERE t.calendario.id = ?1 ");
        query.setParameter(1, calendario.getId());
        List<Turma> list = query.getResultList();
        return list;
    }

    public List<Turma> obter(Professor prof, Curso c) {

        javax.persistence.Query query = entityManager.createQuery("SELECT t FROM Turma t WHERE t.professor.id = ?1 AND t.disciplina.curso.id = ?2");
        query.setParameter(1, prof.getId());
        query.setParameter(2, c.getId());
        List<Turma> list = query.getResultList();
        return list;
    }
    public List<Turma> obter(Professor prof) {

        javax.persistence.Query query = entityManager.createQuery("SELECT t FROM Turma t WHERE t.professor.id = ?1");
        query.setParameter(1, prof.getId());
        List<Turma> list = query.getResultList();
        return list;
    }
    
    public List<Turma> obterAtivas() {
        javax.persistence.Query query = entityManager.createQuery("SELECT t FROM Turma t WHERE t.estadoTurma = ?1 ");
        query.setParameter(1, SituacaoTurma.CURSANDO);
        List<Turma> list = query.getResultList();
        return list;
    }
    
    public List<Turma> obterAtivas(Professor prof) {
        javax.persistence.Query query = entityManager.createQuery("SELECT t FROM Turma t WHERE t.professor.id = ?1 AND t.estadoTurma = ?2 ");
        query.setParameter(1, prof.getId());
        query.setParameter(2, SituacaoTurma.CURSANDO);
        List<Turma> list = query.getResultList();
        return list;
    }

    public List<Turma> obterTurma(String parametro) {
        Query query = entityManager.createQuery("select distinct t from Turma t, Disciplina d, Professor p, Calendario c, Curso curso where t.disciplina.id = d.id and t.professor.id = p.id and t.calendario.id = c.id and curso.id = t.disciplina.curso.id and (lower(p.nome) like lower('%" + parametro + "%') or lower(t.estadoTurma) like lower('%" + parametro + "%') or lower(d.nome) like lower('%" + parametro + "%') or lower(c.identificador) like lower('%" + parametro + "%') or lower(curso.nome) like lower('%" + parametro + "%'))");
        return (List<Turma>) query.getResultList();
    }
}

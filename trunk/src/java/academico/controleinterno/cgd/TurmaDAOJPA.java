/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.DAOJPA;
import java.util.Calendar;
import java.util.List;

public class TurmaDAOJPA extends DAOJPA<Turma> implements TurmaDAO 
{

    public List<Turma> obterTurmasAtuais(Aluno aluno) {
        javax.persistence.Query query = entityManager.createQuery("SELECT t "+
                                                                  "FROM Aluno a, "+
                                                                       "Curso c, "+
                                                                       "Disciplina d, "+
                                                                       "Turma t, "+
                                                                       "Calendario cal "+
                                                                  "WHERE a.id = ?1 "+
                                                                       "AND t.disciplina.id = d.id "+
                                                                       "AND d.curso.id = c.id "+
                                                                       "AND t.calendario.id = cal.id "+
                                                                       "AND (?2 - cal.dataInicioPM >= 0 "+
                                                                       "AND ?2 - cal.dataFimPM <= 0)");
        query.setParameter(1, aluno.getId());
        query.setParameter(2, Calendar.getInstance());
                
        List<Turma> list = query.getResultList();
        return list;
    }

}

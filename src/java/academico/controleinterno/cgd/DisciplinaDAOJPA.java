package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controlepauta.cdp.SituacaoAlunoTurma;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

/**
 * Responsável pela persistência dos objetos Disciplina.
 *
 * @author FS
 */
public class DisciplinaDAOJPA extends DAOJPA<Disciplina> implements DisciplinaDAO {
    /** Obtém as disciplinas de um curso. */
    public List<Disciplina> obter(Curso c) {
        Query query = entityManager.createQuery("SELECT d FROM Disciplina d WHERE d.curso.id = ?1");
        query.setParameter(1, c.getId());
        return query.getResultList();
    }

    
    /** Obtém as disciplinas às quais um aluno está vinculado (aprovado, cursando ou
     * matriculado). */
    public List<Disciplina> obterVinculadas(Aluno aluno) {
        Query query = entityManager.createQuery("SELECT d FROM Turma t, Disciplina d WHERE d.id = t.disciplina.id AND d.id IN ("
                + "SELECT d1.id FROM MatriculaTurma mt, Turma t1, Disciplina d1 "
                + "WHERE mt.aluno.id = ?1 AND mt.turma.id = t1.id AND t1.disciplina.id = d1.id AND "
                + "(mt.situacaoAluno = ?2 OR mt.situacaoAluno = ?3 OR mt.situacaoAluno = ?4)"
                + ")");
        query.setParameter(1, aluno.getId());
        query.setParameter(2, SituacaoAlunoTurma.APROVADO);
        query.setParameter(3, SituacaoAlunoTurma.CURSANDO);
        query.setParameter(4, SituacaoAlunoTurma.MATRICULADO);

        List<Disciplina> list = query.getResultList();
        return list;
    }

    /** Obtém as disciplinas em que um aluno foi aprovado. */
    public List<Disciplina> obterAprovadas(Aluno aluno) {
        Query query = entityManager.createQuery("SELECT d FROM Turma t, Disciplina d WHERE d.id = t.disciplina.id AND d.id IN ("
                + "SELECT d1.id FROM MatriculaTurma mt, Turma t1, Disciplina d1 "
                + "WHERE mt.aluno.id = ?1 AND mt.turma.id = t1.id AND t1.disciplina.id = d1.id AND "
                + "mt.situacaoAluno = ?2"
                + ")");
        query.setParameter(1, aluno.getId());
        query.setParameter(2, SituacaoAlunoTurma.APROVADO);

        List<Disciplina> list = query.getResultList();
        return list;
    }
}
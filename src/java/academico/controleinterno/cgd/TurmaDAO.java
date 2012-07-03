package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.DAO;
import java.util.List;

public interface TurmaDAO extends DAO<Turma> {

    public List<Turma> obterTurmasAtuais(Aluno aluno);
}

package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.DAO;
import java.util.List;

public interface TurmaDAO extends DAO<Turma> {

    public List<Turma> obterTurmasAtuais(Aluno aluno);
    public List<Turma> obter(Calendario calendario);
    public List<Turma> obter(Professor prof);
    public List<Turma> obterAtivas();
    public List<Turma> obterAtivas(Professor prof);
}

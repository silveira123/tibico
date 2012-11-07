package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.persistencia.DAO;
import java.util.List;

/**
 * Interface para persistência dos objetos Disciplina.
 * @author FS
 */
public interface DisciplinaDAO extends DAO<Disciplina> {
    public List<Disciplina> obter(Curso curso);
    public List<Disciplina> obterVinculadas(Aluno aluno);
    public List<Disciplina> obterAprovadas(Aluno aluno);
    public List<Disciplina> obterCadidatosPrerequisito(Curso curso, int periodo);
}

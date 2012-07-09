package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.persistencia.DAO;
import java.util.List;

public interface DisciplinaDAO extends DAO<Disciplina> 
{
    public List<Disciplina> obter(Curso c);
    public List<Disciplina> obterVinculadas(Aluno aluno);
    public List<Disciplina> obter(Disciplina disciplina);
    public List<Disciplina> obterAprovadas(Aluno aluno);
}

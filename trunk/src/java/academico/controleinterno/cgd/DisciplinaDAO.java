package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.persistencia.DAO;
import java.util.List;

public interface DisciplinaDAO extends DAO<Disciplina> 
{
    public List<Disciplina> obter(Curso c);
}

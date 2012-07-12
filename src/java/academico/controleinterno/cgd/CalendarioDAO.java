package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.util.persistencia.DAO;
import java.util.List;

public interface CalendarioDAO extends DAO<Calendario> {
    public List<Calendario> obter(Curso c);
    public boolean verificarPeriodoMatricula(Curso curso);
    public boolean verificarPeriodoLetivo(Curso curso);
    public boolean verificarPeriodoCalendarioAcademico(Curso curso);
    
}

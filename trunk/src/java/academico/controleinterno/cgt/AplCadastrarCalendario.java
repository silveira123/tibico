package academico.controleinterno.cgt;

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cgd.CalendarioDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AplCadastrarCalendario {

    private DAO apDaoCalendario = DAOFactory.obterDAO("JPA", Calendario.class);
    private AplCadastroCurso aplCadastrarCurso = AplCadastroCurso.getInstance();
    
    private AplCadastrarCalendario() {
    }
    
    private static AplCadastrarCalendario instance = null;

    public static AplCadastrarCalendario getInstance() {
        if (instance == null) {
            instance = new AplCadastrarCalendario();
        }
        return instance;
    }

    public Calendario incluirCalendario(ArrayList<Object> args) throws AcademicoException {
        Calendario calendario = new Calendario();
        Curso c = (Curso) args.get(0);
        calendario.setCurso(c);
        calendario.setIdentificador((String) args.get(1));
        calendario.setDuracao((String) args.get(2));
        calendario.setDataInicioCA((Calendar) args.get(3));
        calendario.setDataFimCA((Calendar) args.get(4));
        calendario.setDataInicioPL((Calendar) args.get(5));
        calendario.setDataFimPL((Calendar) args.get(6));
        calendario.setDataInicioPM((Calendar) args.get(7));
        calendario.setDataFimPM((Calendar) args.get(8));
        calendario.setSequencial(1);
        return (Calendario) apDaoCalendario.salvar(calendario);
    }

    public Calendario alterarCalendario(Calendario calendario) throws AcademicoException {
        return (Calendario) apDaoCalendario.salvar(calendario);
    }

    public void apagarCalendario(Calendario calendario) throws AcademicoException {
        apDaoCalendario.excluir(calendario);
    }

    public List<Calendario> obterCalendarios() throws AcademicoException {
        return (List<Calendario>) apDaoCalendario.obter(Calendario.class);
    }
    
    public List<Calendario> obterCalendarios(Curso curso) throws AcademicoException {
        return (List<Calendario>) ((CalendarioDAO) apDaoCalendario).obter(curso);
    }

    public boolean verificarPeriodoMatricula(Curso curso) {
        return (boolean) ((CalendarioDAO) apDaoCalendario).verificarPeriodoMatricula(curso);
    }
    
    public boolean verificarPeriodoLetivo(Curso curso) {
        return (boolean) ((CalendarioDAO) apDaoCalendario).verificarPeriodoLetivo(curso);
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return aplCadastrarCurso.obterCursos();
    }
}

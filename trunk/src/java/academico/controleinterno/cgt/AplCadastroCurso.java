package academico.controleinterno.cgt;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgd.DisciplinaDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.academico.cdp.Regime;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.List;

public class AplCadastroCurso {

    private DAO apDaoCurso = DAOFactory.obterDAO("JPA", Curso.class);
    private DAO apDaoDisciplina = DAOFactory.obterDAO("JPA", Disciplina.class);
    private DAO apDaoGrandeAreaConhecimento = DAOFactory.obterDAO("JPA", GrandeAreaConhecimento.class);
    private DAO apDaoAreaConhecimento = DAOFactory.obterDAO("JPA", AreaConhecimento.class);
    
    private AplCadastroCurso() {
    }
    private static AplCadastroCurso instance = null;

    public static AplCadastroCurso getInstance() {
        if (instance == null) {
            instance = new AplCadastroCurso();
        }
        return instance;
    }

    public Curso incluirCurso(ArrayList<Object> args) throws AcademicoException {
        Curso curso = new Curso();
        curso.setNome((String) args.get(0));
        curso.setDuracao((Integer) args.get(1));
        curso.setDescricao((String) args.get(2));
        curso.setGrauInstrucao((GrauInstrucao) args.get(3));
        curso.setGrandeAreaConhecimento((GrandeAreaConhecimento) args.get(4));
        curso.setRegime((Regime) args.get(5));

        return (Curso) apDaoCurso.salvar(curso);
    }

    public Curso alterarCurso(Curso curso) throws Exception {
        return (Curso) apDaoCurso.salvar(curso);
    }

    public void apagarCurso(Curso curso) throws Exception {
        apDaoCurso.excluir(curso);
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return (List<Curso>) apDaoCurso.obter(Curso.class);
    }

    public Disciplina incluirDisciplina(ArrayList<Object> args) throws AcademicoException {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome((String) args.get(0));
        disciplina.setCargaHoraria((Integer) args.get(1));
        disciplina.setNumCreditos((Integer) args.get(2));
        disciplina.setPeriodoCorrespondente((Integer) args.get(3));
        disciplina.setPrerequisito((List<Disciplina>)args.get(4));
        disciplina.setCurso((Curso) args.get(5));
        disciplina.setAreaConhecimento((List<AreaConhecimento>) args.get(6));

        return (Disciplina) apDaoDisciplina.salvar(disciplina);
    }

    public Disciplina alterarDisciplina(Disciplina disciplina) throws AcademicoException {
        return (Disciplina) apDaoDisciplina.salvar(disciplina);
    }

    public boolean apagarDisciplina(Disciplina disciplina) throws Exception {
        if(disciplina.getPrerequisito().isEmpty() && !CtrlLetivo.getInstance().obterTurma().contains(disciplina))
        {//TODO continuar aki o esquema.
            apDaoDisciplina.excluir(disciplina);
            return true;
        }
        else
            return false;
    }

    public List<Disciplina> obterDisciplinas() throws AcademicoException {
        return (List<Disciplina>) apDaoDisciplina.obter(Disciplina.class);
    }

    public List<Disciplina> obterDisciplinas(Curso curso) {
        return (List<Disciplina>) ((DisciplinaDAO) apDaoDisciplina).obter(curso);
    }
    
    public List<GrandeAreaConhecimento> obterGrandeAreaConhecimentos() throws AcademicoException {
        return (List<GrandeAreaConhecimento>) apDaoGrandeAreaConhecimento.obter(GrandeAreaConhecimento.class);
    }

    public List<AreaConhecimento> obterAreaConhecimentos() throws AcademicoException {
        return (List<AreaConhecimento>) apDaoAreaConhecimento.obter(AreaConhecimento.class);
    }
}

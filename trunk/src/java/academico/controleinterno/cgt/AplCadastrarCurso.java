package academico.controleinterno.cgt;

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
import java.util.List;

/**
 * Aplicação responsável por prover os eventos cadastrais de Curso e Disciplina.
 *
 * @author FS
 */
public class AplCadastrarCurso {
    // DAOs para persistência.
    //FIXME: não definir "JPA" aqui.

    private DAO apDaoCurso = DAOFactory.obterDAO("JPA", Curso.class);
    private DAO apDaoDisciplina = DAOFactory.obterDAO("JPA", Disciplina.class);
    private DAO apDaoGrandeAreaConhecimento = DAOFactory.obterDAO("JPA", GrandeAreaConhecimento.class);
    private DAO apDaoAreaConhecimento = DAOFactory.obterDAO("JPA", AreaConhecimento.class);
    // Instância única desta aplicação.
    private static AplCadastrarCurso instance = null;

    /**
     * Construtor privado, para implementação do padrão Singleton.
     */
    private AplCadastrarCurso() {
    }

    /**
     * Obtém uma instância da aplicação. Implementação do padrão Singleton.
     */
    public static AplCadastrarCurso getInstance() {
        if (instance == null) {
            instance = new AplCadastrarCurso();
        }
        return instance;
    }

    //////////////////// CURSO ////////////////////
    /**
     * Inclui um novo curso.
     */
    // FIXME: rever questão da passagem de parâmetros.
    public Curso incluirCurso(List<Object> args) throws AcademicoException {
        Curso curso = null;
        // validação
        if ((Integer) args.get(1) > 0) {
            // criação do objeto
            curso = new Curso();
            curso.setNome((String) args.get(0));
            curso.setDuracao((Integer) args.get(1));
            curso.setDescricao((String) args.get(2));
            curso.setGrauInstrucao((GrauInstrucao) args.get(3));
            curso.setGrandeAreaConhecimento((GrandeAreaConhecimento) args.get(4));
            curso.setRegime((Regime) args.get(5));
            curso.setSigla((String) args.get(6));
            // registro do objeto
            curso = (Curso) apDaoCurso.salvar(curso);
        }
        return curso;
    }

    /**
     * Altera um curso existente.
     */
    //FIXME: por que aqui já vem o objeto curso alterado e não os parâmetros?
    public Curso alterarCurso(Curso curso) throws Exception {
        // validação
        if (curso.getDuracao() > 0) {
            // alteração??

            // registro
            return (Curso) apDaoCurso.salvar(curso);
        }
        return null;
    }

    /**
     * Exclui um curso.
     */
    public boolean excluirCurso(Curso curso) throws Exception {
        //validação
        if(!this.obterDisciplinas(curso).isEmpty())
            return false;
        if(!AplCadastrarPessoa.getInstance().obterAlunosporCurso(curso).isEmpty())
            return false;
        if(!AplCadastrarCalendarioSala.getInstance().obterCalendarios(curso).isEmpty())
            return false;
        
        //exclusão
        apDaoCurso.excluir(curso);
        
        return true;
    }

    /**
     * Obtém todos os cursos.
     */
    public List<Curso> obterCursos() throws AcademicoException {
        return (List<Curso>) apDaoCurso.obter(Curso.class);
    }

    //////////////////// DISCIPLINA ////////////////////
    /**
     * Inclui uma nova disciplina.
     */
    // FIXME: rever questão da passagem de parâmetros.
    public Disciplina incluirDisciplina(List<Object> args) throws AcademicoException {
        // validação
        // criação do objeto
        Disciplina disciplina = new Disciplina();
        disciplina.setNome((String) args.get(0));
        disciplina.setCargaHoraria((Integer) args.get(1));
        disciplina.setNumCreditos((Integer) args.get(2));
        disciplina.setPeriodoCorrespondente((Integer) args.get(3));
        disciplina.setPrerequisito((List<Disciplina>) args.get(4));
        disciplina.setCurso((Curso) args.get(5));
        disciplina.setAreaConhecimento((List<AreaConhecimento>) args.get(6));
        // registro
        return (Disciplina) apDaoDisciplina.salvar(disciplina);
    }

    /**
     * Altera uma disciplina existente.
     */
    //FIXME: por que aqui já vem o objeto disciplina alterado e não os parâmetros?
    public Disciplina alterarDisciplina(Disciplina disciplina) throws AcademicoException {
        //validação
        //alteração
        //registro
        return (Disciplina) apDaoDisciplina.salvar(disciplina);
    }

    /**
     * Exclui uma disciplina.
     */
    public boolean excluirDisciplina(Disciplina disciplina) throws Exception {
        // obtém as turmas
        List<Turma> listas = AplControlarTurma.getInstance().obterTurmas(disciplina);

        // verificar se a disciplina tem turma
        if (listas != null)
        for (Turma turma : listas) {
            if (turma.getDisciplina().equals(disciplina)) {
                return false;
            }
        }

        //recebe a lista de disciplinas do curso
        List<Disciplina> dispList = AplCadastrarCurso.getInstance().obterDisciplinas(disciplina.getCurso());
        
        if (dispList != null)
        for (Disciplina d : dispList) {
            //se a disciplina a ser excluida estiver no grupo de prerrequisito de outras a exclusão é impedida
            if (d.getPrerequisito().contains(disciplina))
                return false;
        }

        // exclui a disciplina
        apDaoDisciplina.excluir(disciplina);
        return true;
    }

    /**
     * Obtém todas as disciplinas.
     */
    public List<Disciplina> obterDisciplinas() throws AcademicoException {
        return (List<Disciplina>) apDaoDisciplina.obter(Disciplina.class);
    }

    /**
     * Obtém todas as disciplinas de um curso.
     */
    public List<Disciplina> obterDisciplinas(Curso curso) {
        return (List<Disciplina>) ((DisciplinaDAO) apDaoDisciplina).obter(curso);
    }

    /**
     * Obtém todas as grandes áreas de conhecimento.
     */
    public List<GrandeAreaConhecimento> obterGrandeAreaConhecimentos() throws AcademicoException {
        return (List<GrandeAreaConhecimento>) apDaoGrandeAreaConhecimento.obter(GrandeAreaConhecimento.class);
    }

    /**
     * Obtém todas as áreas de conhecimento.
     */
    public List<AreaConhecimento> obterAreaConhecimentos() throws AcademicoException {
        return (List<AreaConhecimento>) apDaoAreaConhecimento.obter(AreaConhecimento.class);
    }
}
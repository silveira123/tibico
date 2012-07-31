/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgt;

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgd.DisciplinaDAO;
import academico.controleinterno.cgd.TurmaDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.horario.cdp.Horario;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Turma
 * @author pietrochrist
 */
public class AplControlarTurma {
    private DAO apDaoTurma = DAOFactory.obterDAO("JPA", Turma.class);
    private DAO apDaoHorario = DAOFactory.obterDAO("JPA", Horario.class);
    private DAO apDaoDisciplina = DAOFactory.obterDAO("JPA", Disciplina.class);
    private AplCadastrarPessoa aplPessoa = AplCadastrarPessoa.getInstance();
    
    private AplControlarTurma() {
    }
    
    private static AplControlarTurma instance = null;

    public static AplControlarTurma getInstance() {
        if (instance == null) {
            instance = new AplControlarTurma();
        }
        return instance;
    }

    public Turma incluirTurma(ArrayList<Object> args) throws AcademicoException {
        Turma turma = new Turma();
        Disciplina d = (Disciplina) args.get(0);
        turma.setDisciplina(d);
        Calendario c = (Calendario) args.get(1);
        turma.setCalendario(c);
        turma.setNumVagas((Integer)args.get(2));
        ArrayList<Horario> h = (ArrayList<Horario>) args.get(3);       
        turma.setHorario(h);
        
        if (args.get(4) != null) {
            Professor p = (Professor) args.get(4);
            turma.setProfessor(p);
        }

        turma.setEstadoTurma((SituacaoTurma) args.get(5));
        
        return (Turma) apDaoTurma.salvar(turma);
    }

    public Turma alterarTurma(Turma turma) throws AcademicoException {
        return (Turma) apDaoTurma.salvar(turma);
    }

    public void apagarTurma(Turma turma) throws AcademicoException {
        apDaoTurma.excluir(turma);
    }

    public List<Turma> obterTurmas() throws AcademicoException {
        return (List<Turma>) apDaoTurma.obter(Turma.class);
    }
    
    public List<Turma> obterTurmasAtivas(){
        return (List<Turma>)((TurmaDAO) apDaoTurma).obterAtivas();
    }
    
    public List<Turma> obterTurmasAtivas(Professor p){
        return (List<Turma>)((TurmaDAO) apDaoTurma).obterAtivas(p);
    }
    
    public List<Turma> obterTurmas(Professor p) throws AcademicoException {
        return (List<Turma>)((TurmaDAO) apDaoTurma).obter(p);
    }
    
    public List<Horario> obterHorarios() throws AcademicoException {
        return (List<Horario>) apDaoHorario.obter(Horario.class);
    }
    
    public List<Disciplina> obterDisciplinas(Curso curso) {
        return (List<Disciplina>) ((DisciplinaDAO) apDaoDisciplina).obter(curso);
    }

    public List<Turma> obterTurmas (Calendario calendario){
        return (List<Turma>) ((TurmaDAO) apDaoTurma).obter(calendario);
    }
    
    public List<Professor> obterProfessores(Disciplina disciplina)throws AcademicoException{
        List<Professor> listProfessor = aplPessoa.obterProfessor();
        
        for (int i = 0; i < listProfessor.size();) {
            if (!listProfessor.get(i).getAreaConhecimento().containsAll(disciplina.getAreaConhecimento())) {
                listProfessor.remove(i);
            }
            else{
                i++;
            }
        }
        
        return listProfessor;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgt;

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
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
        Calendario c = (Calendario) args.get(0);
        Disciplina d = (Disciplina) args.get(1);
        ArrayList<Horario> h = (ArrayList<Horario>) args.get(2);
        
        turma.setCalendario(c);
        turma.setDisciplina(d);
        turma.setNumVagas((Integer)args.get(2));
        turma.setHorario(h);
        
        return (Turma) apDaoTurma.salvar(turma);
    }

    public Turma alterarTurma(Turma turma) throws Exception {
        return (Turma) apDaoTurma.salvar(turma);
    }

    public void apagarTurma(Turma turma) throws Exception {
        apDaoTurma.excluir(turma);
    }

    public List<Turma> obterTurmas() throws AcademicoException {
        return (List<Turma>) apDaoTurma.obter(Turma.class);
    }
    public List<Horario> obterHorarios() throws AcademicoException {
        return (List<Horario>) apDaoHorario.obter(Horario.class);
    }
}

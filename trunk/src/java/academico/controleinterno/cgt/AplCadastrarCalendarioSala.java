package academico.controleinterno.cgt;

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgd.CalendarioDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AplCadastrarCalendarioSala {

    private DAO apDaoCalendario = DAOFactory.obterDAO("JPA", Calendario.class);
    private DAO apDaoSala = DAOFactory.obterDAO("JPA", Sala.class);
    private DAO apDaoEquipamento = DAOFactory.obterDAO("JPA", Equipamento.class);
    private AplCadastrarCurso aplCadastrarCurso = AplCadastrarCurso.getInstance();
    private static AplCadastrarCalendarioSala instance = null;
    
    private AplCadastrarCalendarioSala() {
    }
    

    public static AplCadastrarCalendarioSala getInstance() {
        if (instance == null) {
            instance = new AplCadastrarCalendarioSala();
        }
        return instance;
    }
    
    public Sala incluirSala(ArrayList<Object> args) throws AcademicoException {
        Sala sala = new Sala();
        sala.setNumBloco((Integer) args.get(0));
        sala.setNumSala((Integer) args.get(1));
        sala.setEquipamentoSala((List<Equipamento>) args.get(2));
        return (Sala) apDaoSala.salvar(sala);
    }

    public Sala alterarSala(Sala sala) throws AcademicoException {
        return (Sala) apDaoSala.salvar(sala);
    }

    public boolean apagarSala(Sala sala) throws AcademicoException {
        apDaoSala.excluir(sala);
        return true;
    }

    public List<Sala> obterSala() throws AcademicoException {
        return (List<Sala>) apDaoSala.obter(Sala.class);
    }
    public List<Equipamento> obterEquipamentos() throws AcademicoException {
        return (List<Equipamento>) apDaoEquipamento.obter(Equipamento.class);
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

    public boolean apagarCalendario(Calendario calendario) throws AcademicoException {
        //Verifica se o calendario possui alguma turma vinculada a ele
        List<Turma> listas = AplControlarTurma.getInstance().obterTurmas(calendario);
        if(!listas.isEmpty())    
            return false;
        
        apDaoCalendario.excluir(calendario);
        return true;
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
    
    public boolean verificarPeriodoLetivo(Curso curso, Turma t) {
        boolean calendarioAberto = t.getCalendario().getSituacao().equals(SituacaoCalendario.ABERTO);
        return (boolean) ((CalendarioDAO) apDaoCalendario).verificarPeriodoLetivo(curso) && calendarioAberto;
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return aplCadastrarCurso.obterCursos();
    }

    public boolean fecharCalendario(Calendario select) throws AcademicoException {
        List<Turma> turmas = AplControlarTurma.getInstance().obterTurmas(select);
        int flag=0;
        for (Turma turma : turmas) {
            if(!turma.getEstadoTurma().equals(SituacaoTurma.ENCERRADA))
                flag++;
        }
        if(flag==0){
            select.setSituacao(SituacaoCalendario.FECHADO);
            this.alterarCalendario(select);
            return true;
        }
        else return false;
        
    }

    public void abrirCalendario(Calendario select) throws AcademicoException {
        select.setSituacao(SituacaoCalendario.ABERTO);
        this.alterarCalendario(select);
    }
}

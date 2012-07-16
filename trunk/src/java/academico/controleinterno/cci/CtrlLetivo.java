/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgt.AplCadastrarCalendario;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.controleinterno.cih.PagEventosCalendario;
import academico.controleinterno.cih.PagEventosTurma;
import academico.controleinterno.cih.PagVisualizarTurmas;
import academico.util.Exceptions.AcademicoException;
import academico.util.horario.cdp.Horario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Administrador
 */
public class CtrlLetivo {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private AplCadastrarCalendario apl = AplCadastrarCalendario.getInstance();
    private PagEventosCalendario pagEventosCalendario;
    private PagEventosTurma pagEventosTurma;
    private PagVisualizarTurmas pagVisualizarTurmas;
    private static CtrlLetivo instance = null;
    private AplControlarTurma aplC = AplControlarTurma.getInstance();

    public static CtrlLetivo getInstance() {
        if (instance == null) {
            instance = new CtrlLetivo();
        }
        return instance;
    }

    public void setPagEventosCalendario(PagEventosCalendario pagEventosCalendario) {
        this.pagEventosCalendario = pagEventosCalendario;
    }

    public void setPagEventosTurma(PagEventosTurma pagEventosTurma) {
        this.pagEventosTurma = pagEventosTurma;
    }
    
    public void setPagVisualizarTurmas(PagVisualizarTurmas pagVisualizarTurmas) {
        this.pagVisualizarTurmas = pagVisualizarTurmas;
    }

    private CtrlLetivo() {
    }

    public Calendario incluirCalendario(ArrayList<Object> args) {
        Calendario c = null;
        try {
            c = apl.incluirCalendario(args);
            pagEventosCalendario.addCalendario(c);
            pagEventosCalendario.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosCalendario.setMensagemAviso("error", "Erro ao cadastrar o calendário");
            System.err.println(ex.getMessage());
        }
        return c;
    }

    public Calendario alterarCalendario(Calendario calendario) {
        Calendario c = null;
        try {
            c = apl.alterarCalendario(calendario);
            pagEventosCalendario.refreshCalendario(c);
            pagEventosCalendario.setMensagemAviso("success", "Cadastro editado com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosCalendario.setMensagemAviso("error", "Erro ao editar o calendário");
            System.err.println(ex.getMessage());
        }
        return c;
    }
    
    public Turma fecharTurma(Turma turma) throws Exception {
        Turma t = aplC.alterarTurma(turma);
        System.out.println("turma" + t);
        pagVisualizarTurmas.refreshTurma(t);
        return t;
    }

    public void apagarCalendario(Calendario calendario) throws Exception {
        apl.apagarCalendario(calendario);
    }

    public List<Calendario> obterCalendario() throws AcademicoException {
        return apl.obterCalendarios();
    }

    public void abrirIncluirCalendario(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.SALVAR);
        map.put("obj", curso);
        Executions.createComponents("/PagFormularioCalendario.zul", null, map);
    }

    public void abrirEditarCalendario(Calendario calendario) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.EDITAR);
        map.put("obj", calendario);
        Executions.createComponents("/PagFormularioCalendario.zul", null, map);
    }

    public void abrirConsultarCalendario(Calendario calendario) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlLetivo.CONSULTAR);
        map.put("obj", calendario);
        Executions.createComponents("/PagFormularioCalendario.zul", null, map);
    }

    public Turma incluirTurma(ArrayList<Object> args) {
        Turma t = null;
        try {
            t = aplC.incluirTurma(args);
            pagEventosTurma.addTurma(t);
            pagEventosTurma.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosTurma.setMensagemAviso("error", "Erro ao cadastrar o calendário");
            System.err.println(ex.getMessage());
        }
        return t;
    }

    public Turma alterarTurma(Turma turma) {
        Turma t = null;
        try {
            t = aplC.alterarTurma(turma);
            pagEventosTurma.refreshTurma(t);
            pagEventosTurma.setMensagemAviso("success", "Cadastro editado com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosTurma.setMensagemAviso("error", "Erro ao editar a turma");
            System.err.println(ex.getMessage());
        }
        return t;
    }

    public void apagarTurma(Turma turma) throws Exception {
        aplC.apagarTurma(turma);
    }

    public List<Turma> obterTurma() throws AcademicoException {
        return aplC.obterTurmas();
    }

    public List<Turma> obterTurma(Professor p) throws AcademicoException {
        return aplC.obterTurmas(p);
    }

    
    public void abrirFechamentoTurmas(Turma t) {
        Map map = new HashMap();
        map.put("turma", t);
        Executions.createComponents("/PagFechamentoTurmas.zul", null, map);
    }
    
    public void abrirIncluirTurma() {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.SALVAR);
        map.put("p", 1);
        Executions.createComponents("/PagFormularioTurma.zul", null, map);
    }

    public void abrirEditarTurma(Turma turma, int p) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.EDITAR);
        map.put("obj", turma);
        map.put("p", p);
        Executions.createComponents("/PagFormularioTurma.zul", null, map);
    }

    public void abrirConsultarTurma(Turma turma) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlLetivo.CONSULTAR);
        map.put("obj", turma);
        map.put("p", 1);
        Executions.createComponents("/PagFormularioTurma.zul", null, map);
    }

    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }

    public List<Horario> obterHorario() throws AcademicoException {
        return aplC.obterHorarios();
    }

    public Component abrirEventosTurma(int tipo) {
        Map map = new HashMap();
        map.put("class", tipo);
        return Executions.createComponents("/pagEventosTurma.zul", null, map);
    }
    
    public Component abrirVisualizarTurmas() {
        return Executions.createComponents("/pagVisualizarTurmas.zul", null, null);
    }

    public Component abrirEventosAlocarProfessor(int tipo) {
        Map map = new HashMap();
        map.put("class", tipo);
        return Executions.createComponents("/pagEventosAlocarProfessor.zul", null, map);
    }

    public Component abrirEventosCalendario() {
        return Executions.createComponents("/pagEventosCalendario.zul", null, null);
    }

    public List<Disciplina> obterDisciplinas(Curso curso) {

        return aplC.obterDisciplinas(curso);
    }

    public List<Calendario> obterCalendarios(Curso curso) {
        try {
            return apl.obterCalendarios(curso);
        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlLetivo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<Professor> obterProfessores(Disciplina disciplina) {
        try {
            return aplC.obterProfessores(disciplina);
        }
        catch (Exception ex) {
            Logger.getLogger(CtrlLetivo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return apl.obterCursos();
    }
    
    public boolean verificarPeriodoLetivo(Curso curso) {
        return apl.verificarPeriodoLetivo(curso);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgt.AplCadastrarCalendarioSala;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.controleinterno.cih.PagAlocarHorarioSala;
import academico.controleinterno.cih.PagEventosCalendario;
import academico.controleinterno.cih.PagEventosSala;
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
    private AplCadastrarCalendarioSala apl = AplCadastrarCalendarioSala.getInstance();
    private PagEventosCalendario pagEventosCalendario;
    private PagEventosTurma pagEventosTurma;
    private PagVisualizarTurmas pagVisualizarTurmas;
    private PagEventosSala pagEventosSala;
    private PagAlocarHorarioSala pagAlocarHorarioSala;
    private AplControlarTurma aplC = AplControlarTurma.getInstance();

    public static CtrlLetivo getInstance() {
        CtrlLetivo instance = (CtrlLetivo) Executions.getCurrent().getSession().getAttribute("ctrlLetivo");
        if (instance == null) {
            instance = new CtrlLetivo();
            Executions.getCurrent().getSession().setAttribute("ctrlLetivo", instance);
        }
        return instance;
    }

    public void setPagEventosSala(PagEventosSala pagEventosSala) {
        this.pagEventosSala = pagEventosSala;
    }
    public void setPagAlocarHorarioSala(PagAlocarHorarioSala pagAlocarHorarioSala) {
        this.pagAlocarHorarioSala = pagAlocarHorarioSala;
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
        
    public boolean apagarCalendario(Calendario calendario) throws Exception {
        return apl.apagarCalendario(calendario);
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
    
    public Turma alterarVisualizarTurma(Turma turma) {
        Turma t = null;
        try {
            t = aplC.alterarTurma(turma);
            if(turma.getEstadoTurma().equals(SituacaoTurma.CURSANDO))
                aplC.alterarStatusCursando(turma);
            pagVisualizarTurmas.refreshTurma(t);
            pagVisualizarTurmas.setMensagemAviso("success", "Cadastro editado com sucesso");
        }
        catch (AcademicoException ex) {
            pagVisualizarTurmas.setMensagemAviso("error", "Erro ao editar a turma");
            System.err.println(ex.getMessage());
        }
        return t;
    }

    public boolean apagarTurma(Turma turma) throws Exception {
        return aplC.apagarTurma(turma);
    }

    public List<Turma> obterTurma() throws AcademicoException {
        return aplC.obterTurmas();
    }

    public List<Turma> obterTurma(Professor p, Curso c) throws AcademicoException {
        return aplC.obterTurmas(p, c);
    }
    public List<Turma> obterTurma(Professor p) throws AcademicoException {
        return aplC.obterTurmas(p);
    }
    public List<Turma> obterTurmasAtivas(){
        return aplC.obterTurmasAtivas();
    }

    public List<Turma> obterTurmasAtivas(Professor p){
        return aplC.obterTurmasAtivas(p);
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
        return Executions.createComponents("/PagEventosTurma.zul", null, map);
    }
    
    public Component abrirVisualizarTurmas() {
        return Executions.createComponents("/PagVisualizarTurmas.zul", null, null);
    }
    
    public Component abrirVisualizarTurmas(Professor prof) {
        Map map = new HashMap();
        map.put("professor", prof);
        return Executions.createComponents("/PagVisualizarTurmas.zul", null, map);
    }

    public Component abrirEventosAlocarProfessor(int tipo) {
        Map map = new HashMap();
        map.put("class", tipo);
        return Executions.createComponents("/PagEventosAlocarProfessor.zul", null, map);
    }
    
    public Component abrirEventosSala() {
        return Executions.createComponents("/PagEventosSala.zul", null, null);
    }

    public Component abrirEventosCalendario() {
        return Executions.createComponents("/PagEventosCalendario.zul", null, null);
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
    
    public boolean verificarPeriodoLetivo(Curso curso, Turma t) {
        return apl.verificarPeriodoLetivo(curso, t);
    }

    public boolean fecharPeriodo(Calendario select) throws AcademicoException {
        return apl.fecharCalendario(select);
    }

    public void abrirPeriodo(Calendario select) throws AcademicoException{
        apl.abrirCalendario(select);
    }

    public List<Turma> obterTurmaPesquisa(String parametro) throws AcademicoException{
        return aplC.obterTurmaPesquisa(parametro);
    }
    
    public List<Equipamento> obterEquipamentos() throws AcademicoException {
        return apl.obterEquipamentos();
    }
    
    public Sala incluirSala (ArrayList<Object> args) throws AcademicoException {
        Sala s = null;
        try {
            s = apl.incluirSala(args);
            pagEventosSala.addSala(s);
            pagEventosSala.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosSala.setMensagemAviso("error", "Erro ao cadastrar a sala");
            System.err.println(ex.getMessage());
        }
        return s;
    }
    
    public Sala alterarSala(Sala sala) throws AcademicoException {
        Sala s = null;
        try {
            s = apl.alterarSala(sala);
            pagEventosSala.refreshSala(s);
            pagEventosSala.setMensagemAviso("success", "Sala editada com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosTurma.setMensagemAviso("error", "Erro ao editar a sala");
            System.err.println(ex.getMessage());
        }
        return s;
    }
     
    public boolean apagarSala(Sala sala) throws AcademicoException {
        return apl.apagarSala(sala);
    }
    
    public List<Sala> obterSala() throws AcademicoException {
        return apl.obterSala();
    }
    
    public void abrirIncluirSala() {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.SALVAR);
        map.put("p", 1);
        Executions.createComponents("/PagFormularioSala.zul", null, map);
    }

    public void abrirEditarSala(Sala sala) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.EDITAR);
        map.put("obj", sala);
        Executions.createComponents("/PagFormularioSala.zul", null, map);
    }

    public void abrirConsultarSala(Sala sala) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.CONSULTAR);
        map.put("obj", sala);
        map.put("p", 1);
        Executions.createComponents("/PagFormularioSala.zul", null, map);
    }
    public void abrirAlocarHorarioSala(Turma turma) {
        Map map = new HashMap();
        map.put("obj", turma);
        Executions.createComponents("/PagAlocarHorarioSala.zul", null, map);
    }
}

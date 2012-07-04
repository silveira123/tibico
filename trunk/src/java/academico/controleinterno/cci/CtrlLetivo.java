/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgt.AplCadastrarCalendario;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.util.Exceptions.AcademicoException;
import academico.util.horario.cdp.Horario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static CtrlLetivo instance = null;
    private AplControlarTurma aplC = AplControlarTurma.getInstance();

    public static CtrlLetivo getInstance() {
        if (instance == null) {
            instance = new CtrlLetivo();
        }
        return instance;
    }

    private CtrlLetivo() {
    }

    public Calendario incluirCalendario(ArrayList<Object> args) throws AcademicoException {

        return apl.incluirCalendario(args);
    }

    public Calendario alterarCalendario(Calendario calendario) throws Exception {
        return apl.alterarCalendario(calendario);
    }

    public void apagarCalendario(Calendario calendario) throws Exception {
        apl.apagarCalendario(calendario);
    }

    public List<Calendario> obterCalendario() throws AcademicoException {
        return apl.obterCalendarios();
    }

    public void abrirIncluirCalendario() {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.SALVAR);
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

    public Turma incluirTurma(ArrayList<Object> args) throws AcademicoException {

        return aplC.incluirTurma(args);
    }

    public Turma alterarTurma(Turma turma) throws Exception {
        return aplC.alterarTurma(turma);
    }

    public void apagarTurma(Turma turma) throws Exception {
        aplC.apagarTurma(turma);
    }

    public List<Turma> obterTurma() throws AcademicoException {
        return aplC.obterTurmas();
    }

    public void abrirIncluirTurma() {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.SALVAR);
        Executions.createComponents("/PagFormularioTurma.zul", null, map);
    }

    public void abrirEditarTurma(Turma turma) {
        Map map = new HashMap();
        map.put("tipo", CtrlLetivo.EDITAR);
        map.put("obj", turma);
        Executions.createComponents("/PagFormularioTurma.zul", null, map);
    }

    public void abrirConsultarTurma(Turma turma) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlLetivo.CONSULTAR);
        map.put("obj", turma);
        Executions.createComponents("/PagFormularioTurma.zul", null, map);
    }

    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }

    public List<Horario> obterHorario() throws AcademicoException {
        return aplC.obterHorarios();
    }

    
    public Component abrirEventosTurma()
    {
        return Executions.createComponents("/pagEventosTurma.zul", null, null);
    }
     
    public Component abrirEventosCalendario()
    {
        return Executions.createComponents("/pagEventosCalendario.zul", null, null);
    }

    
    public List<Disciplina> obterDisciplinas(Curso curso){
       
            return aplC.obterDisciplinas(curso);
        
        
        
        
    }
    
    public List<Calendario> obterCalendarios(Curso curso){
        try {
            return apl.obterCalendarios(curso);
        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlLetivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public List<Professor> obterProfessores(Disciplina disciplina){
        try {
            return aplC.obterProfessores(disciplina);
        }
        catch (Exception ex) {
            Logger.getLogger(CtrlLetivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}

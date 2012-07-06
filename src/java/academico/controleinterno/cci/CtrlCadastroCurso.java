/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cgt.AplCadastroCurso;
import academico.controleinterno.cih.PagEventosCurso;
import academico.controleinterno.cih.PagEventosDisciplina;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 *
 * @author Administrador
 */
public class CtrlCadastroCurso {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private Window window;
    private PagEventosCurso pagEventosCurso;
    private PagEventosDisciplina pagEventosDisciplina;
    private AplCadastroCurso apl = AplCadastroCurso.getInstance();
    private static CtrlCadastroCurso instance = null;

    public static CtrlCadastroCurso getInstance() {
        if (instance == null) {
            instance = new CtrlCadastroCurso();
        }
        return instance;
    }

    public void setPagEventosCurso(PagEventosCurso pagEventosCurso) {
        this.pagEventosCurso = pagEventosCurso;
    }

    public void setPagEventosDisciplina(PagEventosDisciplina pagEventosDisciplina) {
        this.pagEventosDisciplina = pagEventosDisciplina;
    }

    private CtrlCadastroCurso() {
    }
    //TODO testando resolver exceção
    public Curso incluirCurso(ArrayList<Object> args) throws Exception {
        Curso c = apl.incluirCurso(args);
        pagEventosCurso.addCurso(c);
        return c;
    }

    public Curso alterarCurso(Curso args) throws Exception {
        Curso c = apl.alterarCurso(args);
        pagEventosCurso.refreshCurso(c);
        return apl.alterarCurso(args);
    }

    public void apagarCurso(Curso curso) throws Exception {
        apl.apagarCurso(curso);
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return apl.obterCursos();
    }

    public Disciplina incluirDisciplina(ArrayList<Object> args) throws Exception {
        Disciplina d = apl.incluirDisciplina(args);
        pagEventosDisciplina.addDisciplina(d);
        return d;
    }

    public Disciplina alterarDisciplina(Disciplina args) throws Exception {
        Disciplina d = apl.alterarDisciplina(args);
        pagEventosDisciplina.refreshDisciplina(d);
        return d;
    }

    public boolean apagarDisciplina(Disciplina disciplina) throws Exception {
        return apl.apagarDisciplina(disciplina);
    }

    public List<Disciplina> obterDisciplinas() throws AcademicoException {
        return apl.obterDisciplinas();
    }

    public List<Disciplina> obterDisciplinas(Curso curso) {
        return apl.obterDisciplinas(curso);
    }

    public List<GrandeAreaConhecimento> obterGrandeAreaConhecimento() throws AcademicoException {
        return apl.obterGrandeAreaConhecimentos();
    }

    public List<AreaConhecimento> obterAreaConhecimento() throws AcademicoException {
        return apl.obterAreaConhecimentos();
    }

    public void abrirIncluirCurso() {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCurso.SALVAR);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirEditarCurso(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCurso.EDITAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirConsultarCurso(Curso curso) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlCadastroCurso.CONSULTAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirIncluirDisciplina(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCurso.SALVAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirEditarDisciplina(Disciplina disciplina) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCurso.EDITAR);
        map.put("obj", disciplina);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirConsultarDisciplina(Disciplina disciplina) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCurso.CONSULTAR);
        map.put("obj", disciplina);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }
    
    public Component abrirEventosCurso()
    {
        window = (Window) Executions.createComponents("/pagEventosCurso.zul", null, null);
        return window;
    }
    
    public Component abrirEventosDisciplina()
    {
        return Executions.createComponents("/pagEventosDisciplina.zul", null, null);
    }
}

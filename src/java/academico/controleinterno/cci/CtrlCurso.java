/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cgt.AplCadastrarCurso;
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
import org.zkoss.zul.Window;

/**
 *
 * @author Administrador
 */
public class CtrlCurso {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private Window window;
    private PagEventosCurso pagEventosCurso;
    private PagEventosDisciplina pagEventosDisciplina;
    private AplCadastrarCurso apl = AplCadastrarCurso.getInstance();

    public static CtrlCurso getInstance() {
        CtrlCurso instance = (CtrlCurso) Executions.getCurrent().getSession().getAttribute("ctrlCurso");
        if (instance == null) {
            instance = new CtrlCurso();
            Executions.getCurrent().getSession().setAttribute("ctrlCurso", instance);
        }
        return instance;
    }

    public void setPagEventosCurso(PagEventosCurso pagEventosCurso) {
        this.pagEventosCurso = pagEventosCurso;
    }

    public void setPagEventosDisciplina(PagEventosDisciplina pagEventosDisciplina) {
        this.pagEventosDisciplina = pagEventosDisciplina;
    }

    private CtrlCurso() {
    }
    //TODO testando resolver exceção

    public Curso incluirCurso(ArrayList<Object> args) {
        Curso c = null;
        try {
            c = apl.incluirCurso(args);
            if (c != null) {
                pagEventosCurso.addCurso(c);
                pagEventosCurso.setMensagemAviso("success", "Cadastro feito com sucesso");
            }
        }
        catch (Exception ex) {
            pagEventosDisciplina.setMensagemAviso("error", "Erro ao cadastrar o curso");
            System.err.println(ex.getMessage());
        }
        return c;
    }

    public Curso alterarCurso(Curso args) {
        Curso c = null;
        try {
            c = apl.alterarCurso(args);
            if (c != null) {
                pagEventosCurso.refreshCurso(c);
                pagEventosCurso.setMensagemAviso("success", "Cadastro editado com sucesso");
            }
        }
        catch (Exception ex) {
            pagEventosDisciplina.setMensagemAviso("error", "Erro ao cadastrar o curso");
            System.err.println(ex.getMessage());
        }
        return c;
    }

    public void apagarCurso(Curso curso) throws Exception {
        apl.apagarCurso(curso);
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return apl.obterCursos();
    }

    public Disciplina incluirDisciplina(ArrayList<Object> args) {
        Disciplina d = null;
        try {
            d = apl.incluirDisciplina(args);
            pagEventosDisciplina.addDisciplina(d);
            pagEventosDisciplina.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosDisciplina.setMensagemAviso("error", "Erro ao cadastrar a disciplina");
            System.err.println(ex.getMessage());
        }
        return d;
    }

    public Disciplina alterarDisciplina(Disciplina args) {
        Disciplina d = null;
        try {
            d = apl.alterarDisciplina(args);
            pagEventosDisciplina.refreshDisciplina(d);
            pagEventosDisciplina.setMensagemAviso("success", "Cadastro editado com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosDisciplina.setMensagemAviso("error", "Erro ao editar a disciplina");
            System.err.println(ex.getMessage());
        }
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
        map.put("tipo", CtrlCurso.SALVAR);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirEditarCurso(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCurso.EDITAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirConsultarCurso(Curso curso) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlCurso.CONSULTAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirIncluirDisciplina(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCurso.SALVAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirEditarDisciplina(Disciplina disciplina) {
        Map map = new HashMap();
        map.put("tipo", CtrlCurso.EDITAR);
        map.put("obj", disciplina);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirConsultarDisciplina(Disciplina disciplina) {
        Map map = new HashMap();
        map.put("tipo", CtrlCurso.CONSULTAR);
        map.put("obj", disciplina);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }

    public Component abrirEventosCurso() {
        window = (Window) Executions.createComponents("/pagEventosCurso.zul", null, null);
        return window;
    }

    public Component abrirEventosDisciplina() {
        return Executions.createComponents("/pagEventosDisciplina.zul", null, null);
    }
}

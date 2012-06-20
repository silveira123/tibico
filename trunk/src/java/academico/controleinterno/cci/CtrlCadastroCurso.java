/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cci;

import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cgt.AplCadastroCursoDisciplina;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Administrador
 */
public class CtrlCadastroCursoDisplina {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private AplCadastroCursoDisciplina apl = AplCadastroCursoDisciplina.getInstance();
    private static CtrlCadastroCursoDisplina instance = null;

    public static CtrlCadastroCursoDisplina getInstance() {
        if (instance == null) {
            instance = new CtrlCadastroCursoDisplina();
        }
        return instance;
    }

    private CtrlCadastroCursoDisplina() {
    }

    public Curso incluirCurso(ArrayList<Object> args) throws Exception {
        return apl.incluirCurso(args);
    }

    public Curso alterarCurso(Curso args) throws Exception {
        return apl.alterarCurso(args);
    }

    public void apagarCurso(Curso curso) throws Exception {
        apl.apagarCurso(curso);
    }

    public List<Curso> obterCursos() throws AcademicoException {
        return apl.obterCursos();
    }

    public Disciplina incluirDisciplina(ArrayList<Object> args) throws Exception {
        return apl.incluirDisciplina(args);
    }

    public Disciplina alterarDisciplina(Disciplina args) throws Exception {
        return apl.alterarDisciplina(args);
    }

    public void apagarDisciplina(Disciplina disciplina) throws Exception {
        apl.apagarDisciplina(disciplina);
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
        map.put("tipo", CtrlCadastroCursoDisplina.SALVAR);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirEditarCurso(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCursoDisplina.EDITAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirConsultarCurso(Curso curso) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlCadastroCursoDisplina.CONSULTAR);
        map.put("obj", curso);
        Executions.createComponents("/pagformulariocurso.zul", null, map);
    }

    public void abrirIncluirDisciplina(Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCursoDisplina.SALVAR);
        map.put("curso", curso);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirEditarDisciplina(Disciplina disciplina, Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCursoDisplina.EDITAR);
        map.put("obj", disciplina);
        map.put("curso", curso);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirConsultarDisciplina(Disciplina disciplina, Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlCadastroCursoDisplina.CONSULTAR);
        map.put("obj", disciplina);
        map.put("curso", curso);
        Executions.createComponents("/pagformulariodisciplina.zul", null, map);
    }

    public void abrirEventosDisciplina(Curso curso) {
        Map map = new HashMap();
        map.put("obj", curso);
        Executions.createComponents("/pageventosdisciplina.zul", null, map);
    }
    
    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }
}

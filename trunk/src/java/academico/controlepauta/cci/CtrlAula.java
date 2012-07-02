/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controlepauta.cci;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.Aula;
import academico.controlepauta.cdp.Avaliacao;
import academico.controlepauta.cdp.Frequencia;
import academico.controlepauta.cgt.AplControlarAula;
import academico.controlepauta.cih.PagEventosChamada;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Administrador
 */
public class CtrlAula {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private AplControlarAula apl = AplControlarAula.getInstance();
    private static CtrlAula instance = null;

    public static CtrlAula getInstance() {
        if (instance == null) {
            instance = new CtrlAula();
        }
        return instance;
    }

    private CtrlAula() {
    }

   public Avaliacao incluirAvaliacao(ArrayList<Object> args) throws AcademicoException {

        return apl.incluirAvaliacao(args);
    }

    public Avaliacao alterarAvaliacao(Avaliacao avaliacao) throws Exception {
        return apl.alterarAvaliacao(avaliacao);
    }

    public void apagarAvaliacao(Avaliacao avaliacao) throws Exception {
        apl.apagarAvaliacao(avaliacao);
    }

    public List<Avaliacao> obterAvaliacoes() throws AcademicoException {
        return apl.obterAvaliacoes();
    }

    public void abrirIncluirAvaliacao(Turma turma) {
        Map map = new HashMap();
        map.put("tipo", CtrlAula.SALVAR);
        map.put("obj", turma);
        Executions.createComponents("/PagFormularioAvaliacao.zul", null, map);
    }

    public void abrirEditarAvaliacao(Avaliacao avaliacao) {
        Map map = new HashMap();
        map.put("tipo", CtrlAula.EDITAR);
        map.put("obj", avaliacao);
        Executions.createComponents("/PagFormularioAvaliacao.zul", null, map);
    }

    public void abrirConsultarAvaliacao(Avaliacao avaliacao) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlAula.CONSULTAR);
        map.put("obj", avaliacao);
        Executions.createComponents("/PagFormularioAvaliacao.zul", null, map);
    }

    public void abrirRegistroPontuacao(Avaliacao avaliacao) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlAula.CONSULTAR);
        map.put("obj", avaliacao);
        Executions.createComponents("/PagRegistroPontuacao.zul", null, map);
    }
    
    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }

    public void incluirResultado(Avaliacao obj, List<Object> notas, List<Object> observacoes) throws AcademicoException {
         apl.incluirResultado(obj, notas, observacoes, CtrlMatricula.getInstance().obter(obj.getTurma()));
    }
    
    public Aula incluirAula(ArrayList<Object> args) throws AcademicoException {

        return apl.incluirAula(args);
    }

    public Aula alterarAula(Aula aula) throws Exception {
        return apl.alterarAula(aula);
    }

    public void apagarAula(Aula aula) throws Exception {
        apl.apagarAula(aula);
    }

    public void apagarFrequencia(Frequencia frequencia) throws Exception {
        apl.apagarFrequencia(frequencia);
    }
    public List<Frequencia> obterFrequencias() throws AcademicoException {
        return apl.obterFrequencias();
    }
    
    public List<Frequencia> obterFrequencias(Turma t) throws AcademicoException {
        return apl.obterFrequencias(t);
    }
    
    public List<Frequencia> obterFrequencias(Aluno a) throws AcademicoException {
        return apl.obterFrequencias(a);
    }
    
     public List<Aula> obterAulas() throws AcademicoException {
        return apl.obterAulas();
    }

    public void abrirIncluirAula(Turma turma) {
        Map map = new HashMap();
        map.put("tipo", CtrlAula.SALVAR);
        map.put("obj2", turma);
        Executions.createComponents("/PagRegistroChamada.zul", null, map);
    }

    public void abrirEditarAula(Aula aula, Turma turma) {
        Map map = new HashMap();
        map.put("tipo", CtrlAula.EDITAR);
        map.put("obj2", turma);
        map.put("obj", aula);
        Executions.createComponents("/PagRegistroChamada.zul", null, map);
    }

    public void abrirConsultarAula(Aula aula, Turma turma) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlAula.CONSULTAR);
        map.put("obj2", turma);
        map.put("obj", aula);
        Executions.createComponents("/PagRegistroChamada.zul", null, map);
    }  
}

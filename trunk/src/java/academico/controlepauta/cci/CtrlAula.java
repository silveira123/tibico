/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controlepauta.cci;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgt.AplPrincipal;
import academico.controlepauta.cdp.*;
import academico.controlepauta.cgt.AplControlarAula;
import academico.controlepauta.cih.PagEventosAvaliacao;
import academico.controlepauta.cih.PagEventosChamada;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Administrador
 */
public class CtrlAula {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private PagEventosChamada pagEventosChamada;
    private PagEventosAvaliacao pagEventosAvaliacao;
    private AplControlarAula apl = AplControlarAula.getInstance();
    private AplPrincipal aplPrincipal = AplPrincipal.getInstance();

    public static CtrlAula getInstance() {
        CtrlAula instance = (CtrlAula) Executions.getCurrent().getSession().getAttribute("ctrlAula");
        if (instance == null) {
            instance = new CtrlAula();
            Executions.getCurrent().getSession().setAttribute("ctrlAula", instance);
        }
        return instance;
    }
    
    private CtrlAula() {
    }

    public void setPagEventosChamada(PagEventosChamada pagEventosChamada) {
        this.pagEventosChamada = pagEventosChamada;
    }

    public void setPagEventosAvaliacao(PagEventosAvaliacao pagEventosAvaliacao) {
        this.pagEventosAvaliacao = pagEventosAvaliacao;
    }

    public Avaliacao incluirAvaliacao(ArrayList<Object> args) {
        Avaliacao a = null;
        try {
            a = apl.incluirAvaliacao(args);
            pagEventosAvaliacao.addAvaliacao(a);
            pagEventosAvaliacao.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosAvaliacao.setMensagemAviso("error", "Erro ao cadastrar a avaliação");
            System.err.println(ex.getMessage());
        }
        return a;
    }

    public Avaliacao alterarAvaliacao(Avaliacao avaliacao) {
        Avaliacao a = null;
        try {
            a = apl.alterarAvaliacao(avaliacao);
            pagEventosAvaliacao.refreshAvaliacao(a);
            pagEventosAvaliacao.setMensagemAviso("success", "Cadastro editado com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosAvaliacao.setMensagemAviso("error", "Erro ao editar a avaliação");
            System.err.println(ex.getMessage());
        }
        return a;
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
    
    public Aula incluirAula(ArrayList<Object> args) {
        Aula a = null;
        try {
            a = apl.incluirAula(args);
            pagEventosChamada.addChamada(a);
            pagEventosChamada.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosChamada.setMensagemAviso("error", "Erro ao cadastrar a aula");
            System.err.println(ex.getMessage());
        }
        return a;
    }

    public Aula alterarAula(Aula aula, List<Frequencia> frequencia) {
        Aula a = null;
        try {
            a = apl.alterarAula(aula, frequencia);
            pagEventosChamada.refreshChamada(a);
            pagEventosChamada.setMensagemAviso("success", "Cadastro editado com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosChamada.setMensagemAviso("error", "Erro ao editar a aula");
            System.err.println(ex.getMessage());
        }
        return a;
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
    
    public List<Frequencia> obterFrequencias(Aluno a, Turma t) throws AcademicoException {
        return apl.obterFrequencias(a,t);
    }
    
     public List<Aula> obterAulas(Turma turma) throws AcademicoException {
        return apl.obterAulas(turma);
    }

    public void abrirAbout() {
        //Executions.sendRedirect("/PagInformacaoSobre.zul");
        Executions.createComponents("/PagInformacaoSobre.zul", null, null);
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
    
    public Component abrirEventosAvaliacao()
    {
        return Executions.createComponents("/pagEventosAvaliacao.zul", null, null);
    }
    
    public Component abrirEventosAvaliacao(Professor prof)
    {
        Map map = new HashMap();
        map.put("professor", prof);
        return Executions.createComponents("/pagEventosAvaliacao.zul", null, map);
    }
    
    public Component abrirEventosChamada()
    {
        return Executions.createComponents("/pagEventosChamada.zul", null, null);
    }
    
    public Component abrirEventosChamada(Professor prof)
    {
        Map map = new HashMap();
        map.put("professor", prof);
        return Executions.createComponents("/pagEventosChamada.zul", null, map);
    }
    
    public Component abrirPaginaPrincipal(Usuario usuario)
    {
        Map map = new HashMap();
        map.put("usuario", usuario);
        return Executions.createComponents("/PagPrincipal.zul", null, map);
    }
    
    public Component abrirPaginaPrincipal() {
        return Executions.createComponents("/PagPrincipal.zul", null, null);
    }

    public Aluno getAluno(String matricula) {
        return AplControlarAula.getInstance().obterAluno(matricula);
    }

    public Professor getProfessor(String CPF) {
        return AplControlarAula.getInstance().obterProfessor(CPF);
    }

    public List<Resultado> obterResultados(Avaliacao obj) {
        return AplControlarAula.getInstance().obterResultados(obj);
    }
    
	public boolean validarFaltas(Integer qtd, List<Frequencia> frequencia){
        int maior = 0;
        for (int i = 0; i < frequencia.size(); i++) {
            if(maior< frequencia.get(i).getNumFaltasAula()){
                maior = frequencia.get(i).getNumFaltasAula();
            }
            
        }
        if(qtd<maior){
            return false;
        }
        else return true;
    }
    public void atribuirResultado(Avaliacao a, Turma t) throws AcademicoException{
        AplControlarAula.getInstance().atribuirResultado(a, t);
    }

    public Usuario login(String login, String senha) throws AcademicoException {
        return aplPrincipal.login(login, senha);
    }

}

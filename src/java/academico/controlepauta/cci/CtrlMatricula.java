/*
 * CtrlMatricula.java 
 * Versão: 0.1 
 * Data de Criação : 28/06/2012, 08:44:48
 * Copyright (c) 2012 Fabrica de Software IFES.
 * Incubadora de Empresas IFES, sala 11
 * Rodovia ES-010 - Km 6,5 - Manguinhos, Serra, ES, 29164-321, Brasil.
 * All rights reserved.
 *
 * This software is the confidential and proprietary 
 * information of Fabrica de Software IFES. ("Confidential Information"). You 
 * shall not disclose such Confidential Information and 
 * shall use it only in accordance with the terms of the 
 * license agreement you entered into with Fabrica de Software IFES.
 */

package academico.controlepauta.cci;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cgt.AplControlarMatricula;
import academico.controlepauta.cgt.AplEmitirRelatorios;
import academico.controlepauta.cih.PagEventosMatricula;
import academico.util.Exceptions.AcademicoException;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

/**
 * Está classe controla as aplicações de controle de matricula e de emitir relatórios.
 * 
 * @author Eduardo Rigamonte
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public class CtrlMatricula {

    //Constantes:
    private AplControlarMatricula apl = AplControlarMatricula.getInstance();
    private AplEmitirRelatorios aplEmitirRelatorios = AplEmitirRelatorios.getInstance();
    //Variáveis de Classe:
    private PagEventosMatricula pagEventosMatricula;
    //Variáveis de Instância:
    //Contrutores:
    private CtrlMatricula() {
    }

    public static CtrlMatricula getInstance() {
        CtrlMatricula instance = (CtrlMatricula) Executions.getCurrent().getSession().getAttribute("ctrlMatricula");
        if (instance == null) {
            instance = new CtrlMatricula();
            Executions.getCurrent().getSession().setAttribute("ctrlMatricula", instance);
        }
        return instance;
    }
    /**
     * Faz a matricula de um aluno em uma turma.
     * <p/>
     * @param args Lista de objetos representando os atributos do objeto MatriculaTurma.
     * @return MatriculaTurma MatriculaTurma feita.
     * @throws AcademicoException é retornado caso aconteça um erro na criação da matricula.
     */
    public MatriculaTurma efetuarMatricula(ArrayList<Object> args) {
        MatriculaTurma m = null;
        try {
            m = apl.efetuarMatricula(args);
            CtrlAula.getInstance().atribuirResultado(m);
            pagEventosMatricula.addMatricula(m);
            pagEventosMatricula.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosMatricula.setMensagemAviso("error", "Erro ao cadastrar a matricula");
            System.err.println(ex.getMessage());
        }
        return m;
    }
    
    public void setPagEventosMatricula(PagEventosMatricula pagEventosMatricula) {
        this.pagEventosMatricula = pagEventosMatricula;
    }

    /**
     * Cancela (exclui) uma matricula de um aluno em uma turma.
     * <p/>
     * @param matriculaTurma Matricula que será cancelada.
     * @return void
     * @throws AcademicoException caso não seja possivel excluir a matricula.
     */
    public void cancelarMatricula(MatriculaTurma matriculaTurma) throws AcademicoException {
        apl.cancelarMatricula(matriculaTurma);
    }

    /**
     * Obtem todas as matriculas feitas por um determinado aluno na situação de MATRICULADO.
     * <p/>
     * @param aluno Aluno que terá as matriculas buscadas.
     * @return List<MatriculaTurma> Lista de MatriculaTurma.
     * @throws AcademicoException caso não seja possivel buscar as matriculas.
     */
    public List<MatriculaTurma> obterMatriculadas(Aluno aluno) throws AcademicoException {
        return apl.obterMatriculadas(aluno);
    }

    /**
     * Obtem o histórico do aluno.
     * <p/>
     * @param aluno Aluno que deverá
     * @return List<MatriculaTurma> Lista de MatriculaTurma referente ao histórico do aluno.
     * @throws AcademicoException caso não seja possivel possivel buscar as matriculas.
     */
    public List<MatriculaTurma> emitirHistorico(Aluno aluno) throws AcademicoException, Exception {
        return apl.emitirHistorico(aluno);
    }

    /**
     * Obtem o boletim do aluno em um calendário academico.
     * <p/>
     * @param aluno Aluno que terá o boletim buscado.
     * @param calendario Calendário que definirá qual boletim buscar.
     * @return List<MatriculaTurma> Lista de MatriculaTurma representado o boletim.
     * @throws AcademicoException Caso não consiga buscar o boletim.
     */
    public List<MatriculaTurma> emitirBoletim(Aluno aluno, Calendario calendario) throws AcademicoException, Exception {
        return apl.emitirBoletim(aluno, calendario);
    }
    
    /**
     * Obtem as turmas que o aluno pode se matricular.
     * <p/>
     * @param aluno Aluno que terá as turmas buscadas.
     * @return List<Turma> Lista de Turma que o aluno poderá se matricular.
     */
    public List<Turma> obterTurmasPossiveis(Aluno aluno) {
        return apl.obterTurmasPossiveis(aluno);
    }
    /**
     * Obtem os Calendários que o aluno teve uma matricula vinculada.
     * <p/>
     * @param aluno Aluno que terá as turmas buscadas.
     * @return List<Calendario> Lista de Calendario que o aluno teve uma matricula vinculada.
     */
    public List<Calendario> buscaCalendarios(Aluno a){
        return apl.buscaCalendarios(a);
    }
    
    public List<MatriculaTurma> obter(Turma t) throws AcademicoException
    {
        return apl.obter(t);
    }
    
    public List<Turma> obter(Calendario calendario) throws AcademicoException
    {
        return aplEmitirRelatorios.obterTurmas(calendario);
    }
    public List<Curso> obter() throws AcademicoException
    {
        return aplEmitirRelatorios.obterCursos();
    }
    
    public List<Calendario> obter(Curso curso) throws AcademicoException
    {
        return aplEmitirRelatorios.obterCalendarios(curso);
    }
    
    
    
    public void abrirMatricular(Aluno aluno) {
        Map map = new HashMap();
        map.put("aluno", aluno);
        Executions.createComponents("/PagFormularioMatricula.zul", null, map);
    }
    
    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }
    
    public Component abrirEventosMatricula()
    {
        return Executions.createComponents("/PagEventosMatricula.zul", null, null);
    }
    
    public Component abrirEventosMatricula(Aluno aluno)
    {
        Map map = new HashMap();
        map.put("aluno", aluno);
        return Executions.createComponents("/PagEventosMatricula.zul", null, map);
    }
    
    public Component abrirConsultaMatriculas()
    {
        return Executions.createComponents("/PagConsultaMatriculas.zul", null, null);
    }
    
    public Component abrirRelatorioBoletim()
    {
        return Executions.createComponents("/PagRelatorioBoletim.zul", null, null);
    }
    
    public Component abrirRelatorioBoletim(Aluno aluno)
    {
        Map map = new HashMap();
        map.put("aluno", aluno);
        return Executions.createComponents("/PagRelatorioBoletim.zul", null, map);
    }
    
    public Component abrirRelatorioHistorico()
    {
        return Executions.createComponents("/PagRelatorioHistorico.zul", null, null);
    }
    
    public Component abrirRelatorioHistorico(Aluno aluno)
    {
        Map map = new HashMap();
        map.put("aluno", aluno);
        return Executions.createComponents("/PagRelatorioHistorico.zul", null, map);
    }
    
    public Component abrirRelatorioResultados()
    {
        return Executions.createComponents("/PagRelatorioResultados.zul", null, null);
    }

    public void calculaNotaFinal(MatriculaTurma c) throws AcademicoException, Exception {
         apl.calcularNotaFinal(c);
    }

    public void calcularCoeficiente(Aluno obj) throws Exception {
        apl.calcularCoeficiente(obj);
    }

    public boolean verificaPeriodoMatricula(Curso curso) {
        return apl.verificaPeriodoMatricula(curso);
    }

    public boolean gerarResultados(Turma obj, Double media) throws BadElementException, MalformedURLException, IOException, DocumentException {
        return apl.gerarResultados(obj, media);
    }

    public boolean emitirBoletimPDF(Aluno obj, Calendario cal) {
        try {
            return apl.emitirBoletimPDF(obj, cal);
        } catch (AcademicoException ex) {
            Logger.getLogger(CtrlMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException ex) {
            Logger.getLogger(CtrlMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CtrlMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CtrlMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(CtrlMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CtrlMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean emitirHistoricoPDF(Aluno obj) throws AcademicoException, Exception {
        return apl.emitirHistoricoPDF(obj);
    }
}

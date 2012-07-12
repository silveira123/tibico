/*
 * CtrlPessoa.java 
 * Versão: 0.1 
 * Data de Criação : 14/06/2012, 13:47:23
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

package academico.controleinterno.cci;

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.controleinterno.cih.PagEventosAluno;
import academico.controleinterno.cih.PagEventosProfessor;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.pessoa.cdp.Bairro;
import academico.util.pessoa.cdp.Estado;
import academico.util.pessoa.cdp.Municipio;
import academico.util.pessoa.cdp.Pais;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;


/**
 * <<descrição da Classe>> 
 * 
 * @author Gabriel Quézid 
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
public class CtrlPessoa {

public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private PagEventosAluno pagEventosAluno;
    private PagEventosProfessor pagEventosProfessor;
    private AplCadastrarPessoa apl = AplCadastrarPessoa.getInstance();
    private static CtrlPessoa instance = null;

    public static CtrlPessoa getInstance() {
        if (instance == null) {
            instance = new CtrlPessoa();
        }
        return instance;
    }
    
    public CtrlPessoa() {
    }


    public void setPagEventosAluno(PagEventosAluno pagEventosAluno) {
        this.pagEventosAluno = pagEventosAluno;
    }

    public void setPagEventosProfessor(PagEventosProfessor pagEventosProfessor) {
        this.pagEventosProfessor = pagEventosProfessor;
    }
    
    /**
     * Inclui os dados de um Aluno no sistema
     * @param args
     * @return
     * @throws Exception 
     */
    public Aluno incluirAluno(ArrayList<Object> args) throws Exception {
        Aluno a = apl.incluirAluno(args);
        pagEventosAluno.addAluno(a);
        return a; 
    }

    /**
     * Altera os dados do Aluno no sitema
     * @param professor
     * @return
     * @throws Exception 
     */
    public Aluno alterarAluno(Aluno args) throws Exception {
        Aluno a = apl.alterarAluno(args);
        pagEventosAluno.refreshAluno(a);
        return a;
    }

    /**
     * Apaga os dados de um Aluno no sistema
     * @param aluno
     * @throws Exception 
     */
    public void apagarAluno(Aluno aluno) throws Exception {
        apl.apagarAluno(aluno);
    }

    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * @return 
     */
    public List<Aluno> obterAlunos() throws AcademicoException {
        return apl.obterAlunos();
    }
    
    /**
     * Obtém uma lista de todos os Paises cadastrados
     * @return 
     */
    public List<Pais> obterPaises() throws AcademicoException {
        return apl.obterPais();
    }
    
   public List<Curso> obterCurso() throws AcademicoException {
        return apl.obterCursos();
    }
    
    /**
     * Inclui os dados de um Professor no sistema
     * @param args
     * @return
     * @throws Exception 
     */
    public Professor incluirProfessor(ArrayList<Object> args) throws Exception {
        Professor p = apl.incluirProfessor(args);
        pagEventosProfessor.addProfessor(p);
        return p;
    }

    /**
     * Altera os dados do Professor no sitema
     * @param professor
     * @return
     * @throws Exception 
     */
    public Professor alterarProfessor(Professor args) throws Exception {
        Professor p = apl.alterarProfessor(args);
        pagEventosProfessor.refreshProfessor(p);
        return p;
    }

    /**
     * Apaga os dados de um Professor no sistema
     * @param professor
     * @throws Exception 
     */
    public void apagarProfessor(Professor professor) throws Exception {
        apl.apagarProfessor(professor);
    }

    /**
     * Obtém uma lista de todos os Professor cadastrados
     * @return 
     */
    public List<Professor> obterProfessor() throws AcademicoException {
        return apl.obterProfessor();
    }
    
    /**
     * Obtém uma lista com todas as Áreas de conhecimento
     * @return 
     */
    public List<AreaConhecimento> obterAreaConhecimento() throws AcademicoException {
        return apl.obterAreaConhecimentos();
    }
    
    /**
     * Obtém uma lista com todas as Estados a partir de um país
     * @return 
     */
    public List<Estado> obterEstados(Pais pais) {
        try {
            return apl.obterEstados(pais);
        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * Obtém uma lista com todas as Municipio a partir de um Estado
     * @return 
     */
    public List<Municipio> obterMunicipio(Estado estado){
        try {
            return apl.obterMunicipio(estado);
        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
    }
    
    /**
     * Obtém uma lista com todas as Bairro a partir de um Municipio
     * @return 
     */
    public List<Bairro> obterBairro(Municipio municipio){
        try {
            return apl.obterBairro(municipio);
        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
    }
    
    public void abrirIncluirAluno(Aluno aluno, Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.SALVAR);
        map.put("aluno", aluno);
        map.put("curso", curso);
        Executions.createComponents("/pagFormularioAluno.zul", null, map);
    }

    public void abrirEditarAluno(Aluno aluno) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.EDITAR);
        map.put("obj", aluno);
        Executions.createComponents("/pagFormularioAluno.zul", null, map);
    }

    public void abrirConsultarAluno(Aluno aluno) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlPessoa.CONSULTAR);
        map.put("obj", aluno);
        Executions.createComponents("/pagFormularioAluno.zul", null, map);
    }
    
    public void abrirEventosAluno(Aluno aluno) {
        Map map = new HashMap();
        map.put("obj", aluno);
        Executions.createComponents("/pagEventosAluno.zul", null, map);
    }

    public void abrirIncluirProfessor(Professor professor) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.SALVAR);
        map.put("professor", professor);
        Executions.createComponents("/pagFormularioProfessor.zul", null, map);
    }

    public void abrirEditarProfessor(Professor professor) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.EDITAR);
        map.put("obj", professor);
        Executions.createComponents("/pagFormularioProfessor.zul", null, map);
    }

    public void abrirConsultarProfessor(Professor professor) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlPessoa.CONSULTAR);
        map.put("obj", professor);
        Executions.createComponents("/pagFormularioProfessor.zul", null, map);
    }

    public void abrirEventosProfessor(Professor professor) {
        Map map = new HashMap();
        map.put("obj", professor);
        Executions.createComponents("/pagEventosProfessor.zul", null, map);
    }
    
    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }
 
    public List<Aluno> obterAlunosporTurma(Turma t)
    {
        return apl.obterAlunosporTurma(t);
    }
    
    public Component abrirEventosAluno()
    {
        return Executions.createComponents("/pagEventosAluno.zul", null, null);
    }
    
    public Component abrirEventosProfessor()
    {
        return Executions.createComponents("/pagEventosProfessor.zul", null, null);
    }
    
    public List<Professor> obterProfessor(Calendario c)
    {
        return apl.obterProfessor(c);
    }
}
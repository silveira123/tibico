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
import academico.controleinterno.cgt.AplCadastrarCalendarioSala;
import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.controleinterno.cih.PagEventosAluno;
import academico.controleinterno.cih.PagEventosProfessor;
import academico.controlepauta.cdp.Usuario;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.pessoa.cdp.*;
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
 * <p/>
 * @author Gabriel Quézid
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
public class CtrlPessoa {

    public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private PagEventosAluno pagEventosAluno = null;
    private PagEventosProfessor pagEventosProfessor = null;
    private AplCadastrarPessoa apl = AplCadastrarPessoa.getInstance();
    private AplCadastrarCalendarioSala aplCalendario = AplCadastrarCalendarioSala.getInstance();

    public static CtrlPessoa getInstance() {
        CtrlPessoa instance = (CtrlPessoa) Executions.getCurrent().getSession().getAttribute("ctrlPessoa");
        if (instance == null) {
            instance = new CtrlPessoa();
            Executions.getCurrent().getSession().setAttribute("ctrlPessoa", instance);
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
     * <p/>
     * @param args
     * @return
     * @throws Exception
     */
    public Aluno incluirAluno(ArrayList<Object> args) {
        Aluno a = null;
        try {
            a = apl.incluirAluno(args);
            pagEventosAluno.addAluno(a);
            pagEventosAluno.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosAluno.setMensagemAviso("error", "Erro ao cadastrar o aluno");
            System.err.println(ex.getMessage());
        }
        return a;
    }

    /**
     * Altera os dados do Aluno no sitema
     * <p/>
     * @param aluno
     * @return
     * @throws Exception
     */
    public Aluno alterarAluno(Aluno aluno) {
        Aluno a = null;
        try {
            a = apl.alterarAluno(aluno);
            if (pagEventosAluno != null) {
                pagEventosAluno.refreshAluno(a);
                pagEventosAluno.setMensagemAviso("success", "Cadastro editado com sucesso");
            }
        }
        catch (AcademicoException ex) {
            if (pagEventosAluno != null) {
                pagEventosAluno.setMensagemAviso("error", "Erro ao editar o aluno");
            }
            System.err.println(ex.getMessage());
        }
        pagEventosAluno = null;
        return a;
    }

    /**
     * Apaga os dados de um Aluno no sistema
     * <p/>
     * @param aluno
     * @throws Exception
     */
    public boolean apagarAluno(Aluno aluno) throws Exception {
        return apl.apagarAluno(aluno);
    }

    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * <p/>
     * @return
     */
    public List<Aluno> obterAlunos() throws AcademicoException {
        return apl.obterAlunos();
    }
    
    public List<Aluno> obterAlunosporCurso(Curso c) {
        return apl.obterAlunosporCurso(c);
    }
    
    /**
     * Obtém uma lista de todos os Alunos pesquisadoss
     * <p/>
     * @return
     */
    public List<Aluno> obterAlunosPesquisa(String pesquisa) throws AcademicoException {
        return apl.obterAlunosPesquisa(pesquisa);
    }

    /**
     * Obtém uma lista de todos os Paises cadastrados
     * <p/>
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
     * <p/>
     * @param args
     * @return
     * @throws Exception
     */
    public boolean incluirProfessor(ArrayList<Object> args) {
        Professor p = null;
        try {
            p = apl.incluirProfessor(args);
            if (p == null) {
                return false;
            }
            pagEventosProfessor.addProfessor(p);
            pagEventosProfessor.setMensagemAviso("success", "Cadastro feito com sucesso");
        }
        catch (AcademicoException ex) {
            pagEventosProfessor.setMensagemAviso("error", "Erro ao cadastrar o professor");
            System.err.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Altera os dados do Professor no sitema
     * <p/>
     * @param professor
     * @return
     * @throws Exception
     */
    public boolean alterarProfessor(Professor professor) {
        Professor p = null;
        try {
            p = apl.alterarProfessor(professor);
            if (p == null) {
                return false;
            }
            if (pagEventosProfessor != null) {
                pagEventosProfessor.refreshProfessor(p);
                pagEventosProfessor.setMensagemAviso("success", "Cadastro editado com sucesso");
            }
        }
        catch (AcademicoException ex) {
            if (pagEventosProfessor != null) {
                pagEventosProfessor.setMensagemAviso("error", "Erro ao editar o professor");
            }
            System.err.println(ex.getMessage());
        }
        pagEventosProfessor = null;
        return true;
    }

    /**
     * Apaga os dados de um Professor no sistema
     * <p/>
     * @param professor
     * @throws Exception
     */
    public boolean apagarProfessor(Professor professor) throws AcademicoException {
        return apl.apagarProfessor(professor);
    }

    /**
     * Obtém uma lista de todos os Professor cadastrados
     * <p/>
     * @return
     */
    public List<Professor> obterProfessor() throws AcademicoException {
        return apl.obterProfessor();
    }

    /**
     * Obtém uma lista de todos os Professores pesquisados
     * <p/>
     * @return
     */
    public List<Professor> obterProfessorPesquisa(String nome) throws AcademicoException {
        return apl.obterProfessorPesquisa(nome);
    }

    /**
     * Obtém uma lista com todas as Áreas de conhecimento
     * <p/>
     * @return
     */
    public List<AreaConhecimento> obterAreaConhecimento() throws AcademicoException {
        return apl.obterAreaConhecimentos();
    }

    /**
     * Obtém uma lista com todas as Estados a partir de um país
     * <p/>
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
     * <p/>
     * @return
     */
    public List<Municipio> obterMunicipio(Estado estado) {
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
     * <p/>
     * @return
     */
    public List<Bairro> obterBairro(Municipio municipio) {
        try {
            return apl.obterBairro(municipio);


        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Telefone> setTelefones(ArrayList<Object> listTelefones) {
        return apl.setTelefones(listTelefones);
    }

    public void abrirIncluirAluno(Aluno aluno, Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.SALVAR);
        map.put("aluno", aluno);
        map.put("curso", curso);
        Executions.createComponents("/PagFormularioAluno.zul", null, map);
    }

    public Component abrirEditarAluno(Aluno aluno, Curso curso) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.EDITAR);
        map.put("obj", aluno);
        map.put("curso", curso);
        return Executions.createComponents("/PagFormularioAluno.zul", null, map);
    }

    public void abrirConsultarAluno(Aluno aluno) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlPessoa.CONSULTAR);
        map.put("obj", aluno);
        Executions.createComponents("/PagFormularioAluno.zul", null, map);
    }

    public void abrirEventosAluno(Aluno aluno) {
        Map map = new HashMap();
        map.put("obj", aluno);
        Executions.createComponents("/PagEventosAluno.zul", null, map);
    }

    public void abrirIncluirProfessor(Professor professor) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.SALVAR);
        map.put("professor", professor);
        Executions.createComponents("/PagFormularioProfessor.zul", null, map);
    }

    public Component abrirEditarProfessor(Professor professor) {
        Map map = new HashMap();
        map.put("tipo", CtrlPessoa.EDITAR);
        map.put("obj", professor);
        return Executions.createComponents("/PagFormularioProfessor.zul", null, map);
    }

    public void abrirConsultarProfessor(Professor professor) {
        Map map = new HashMap();
        Object put = map.put("tipo", CtrlPessoa.CONSULTAR);
        map.put("obj", professor);
        Executions.createComponents("/PagFormularioProfessor.zul", null, map);
    }

    public void abrirEventosProfessor(Professor professor) {
        Map map = new HashMap();
        map.put("obj", professor);
        Executions.createComponents("/PagEventosProfessor.zul", null, map);
    }

    public void redirectPag(String url) {
        Executions.sendRedirect(url);
    }

    public List<Aluno> obterAlunosporTurma(Turma t) {
        return apl.obterAlunosporTurma(t);
    }

    public Component abrirEventosAluno() {
        return Executions.createComponents("/PagEventosAluno.zul", null, null);
    }

    public Component abrirEventosProfessor() {
        return Executions.createComponents("/PagEventosProfessor.zul", null, null);
    }

    public Endereco setEndereco(ArrayList<Object> listaEndereco) {
        return apl.setEndereco(listaEndereco);
    }

    public List<Calendario> obterCalendarios(Curso select) throws AcademicoException {
        return aplCalendario.obterCalendarios(select);
    }

    public List<Professor> obterProfessor(Calendario c) {
        return apl.obterProfessor(c);
    }

    public Usuario obterUsuario(Pessoa p) {
        return apl.obterUsuario(p);
    }

    public Usuario alterarUsuario(Usuario usuario) {
        try {
            return apl.alterarUsuario(usuario);


        }
        catch (AcademicoException ex) {
            Logger.getLogger(CtrlPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
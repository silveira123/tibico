/*
 * AplCadastrarPessoa.java 
 * Versão: 0.1 
 * Data de Criação : 13/06/2012, 15:55:16
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

package academico.controleinterno.cgt;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgd.AlunoDAOJPA;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import academico.util.pessoa.cdp.Endereco;
import academico.util.pessoa.cdp.Pais;
import academico.util.pessoa.cdp.Sexo;
import academico.util.pessoa.cdp.Telefone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.zkoss.zk.ui.util.GenericForwardComposer;


/**
 * Classe de aplicação responsável por realizar os casos de uso relativos ao Aluno e ao Professor 
 * 
 * @author Gabriel Quézid; Rodrigo Maia
 * @version 0.1
 * @see
 */
public class AplCadastrarPessoa {
    private DAO apDaoAluno = DAOFactory.obterDAO("JPA", Aluno.class);
    private DAO apDaoProfessor = DAOFactory.obterDAO("JPA", Professor.class);
//    private DAO apDaoGrauInstrucao = DAOFactory.obterDAO("JPA", GrauInstrucao.class);
    private DAO apDaoAreaConhecimento = DAOFactory.obterDAO("JPA", AreaConhecimento.class);
    private DAO apDaoCurso = DAOFactory.obterDAO("JPA", Curso.class);
    private DAO apDaoPais = DAOFactory.obterDAO("JPA", Pais.class);
    
    private static AplCadastrarPessoa instance = null;

    public static AplCadastrarPessoa getInstance() {
        if (instance == null) {
            instance = new AplCadastrarPessoa();
        }
        return instance;
    }
   
    /**
     * Inclui os dados de um Aluno no sistema
     * @param args
     * @return
     * @throws Exception 
     */
    public Aluno incluirAluno(ArrayList<Object> args) throws Exception {
        Aluno aluno = new Aluno();
        
        aluno.setNome((String) args.get(0));
        aluno.setSexo((Sexo) args.get(1));
        aluno.setDataNascimento((Calendar) args.get(2));
        aluno.setTelefone((ArrayList<Telefone>) args.get(3));
        aluno.setEmail((String) args.get(4));
        aluno.setCpf((Long) args.get(5));
        aluno.setIdentidade((String) args.get(6));
        aluno.setNomeMae((String) args.get(7));
        aluno.setNomePai((String) args.get(8));
        aluno.setEndereco((Endereco) args.get(9));
        aluno.setMatricula((Long) args.get(10)); 
        aluno.setCurso((Curso) args.get(11));
        
        //alert("BD Ok!");
        
        return (Aluno) apDaoAluno.salvar(aluno);
    }
    
    /**
     * Altera os dados do Aluno no sitema
     * @param aluno
     * @return
     * @throws Exception 
     */
    public Aluno alterarAluno(Aluno aluno) throws Exception {
        return (Aluno) apDaoAluno.salvar(aluno);
    }
    
    /**
     * Apaga os dados de um Aluno no sistema
     * @param aluno
     * @throws Exception 
     */
    public void apagarAluno(Aluno aluno) throws Exception {
        apDaoAluno.excluir(aluno);
    }

    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * @return 
     */
    public List<Aluno> obterAlunos() throws AcademicoException {
        return (List<Aluno>) apDaoAluno.obter(Aluno.class);
    }
    
    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * @return 
     */
    public List<Aluno> obterAlunosporTurma(Turma t)
    {
        return ((AlunoDAOJPA) apDaoAluno).obterAlunosporTurma(t);
    }
    
    /**
     * Inclui os dados de um Professor no sistema
     * @param args
     * @return
     * @throws Exception 
     */
    public Professor incluirProfessor(ArrayList<Object> args) throws Exception {
        Professor professor = new Professor();
        
        professor.setNome((String) args.get(0));
        professor.setSexo((Sexo) args.get(1));
        professor.setDataNascimento((Calendar) args.get(2));
        professor.setTelefone((ArrayList<Telefone>) args.get(3));
        professor.setEmail((String) args.get(4));
        professor.setCpf((Long) args.get(5));
        professor.setIdentidade((String) args.get(6));
        
        professor.setEndereco((Endereco) args.get(7));   
        
        professor.setGrauInstrucao((GrauInstrucao) args.get(8));

        professor.setAreaConhecimento((ArrayList<AreaConhecimento>) args.get(9));

        return (Professor) apDaoProfessor.salvar(professor);
    }
    
    /**
     * Altera os dados do Professor no sitema
     * @param professor
     * @return
     * @throws Exception 
     */
    public Professor alterarProfessor(Professor professor) throws Exception {
        return (Professor) apDaoProfessor.salvar(professor);
    }
    
    /**
     * Apaga os dados de um Professor no sistema
     * @param professor
     * @throws Exception 
     */
    public void apagarProfessor(Professor professor) throws Exception {
        apDaoProfessor.excluir(professor);
    }

    /**
     * Obtém uma lista de todos os Professor cadastrados
     * @return 
     */
    public List<Professor> obterProfessor() throws AcademicoException {
        return (List<Professor>) apDaoProfessor.obter(Professor.class);
    }
    
    /**
     * Obtém uma lista com todas as Áreas de conhecimento
     * @return 
     */
    public List<AreaConhecimento> obterAreaConhecimentos() throws AcademicoException {
        return (List<AreaConhecimento>) apDaoAreaConhecimento.obter(AreaConhecimento.class);
    }
    
    /**
     * Obtém uma lista com todas os cursos
     * @return 
     */
    public List<Curso> obterCursos() throws AcademicoException {
        return (List<Curso>) apDaoCurso.obter(Curso.class);
    }
    
    /**
     * Obtém uma lista com todas os paises
     * @return 
     */
    public List<Pais> obterPaises() throws AcademicoException {
        return (List<Pais>) apDaoPais.obter(Pais.class);
    }
}
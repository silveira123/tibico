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

import academico.controleinterno.cdp.*;
import academico.controleinterno.cgd.AlunoDAO;
import academico.controleinterno.cgd.AlunoDAOJPA;
import academico.controleinterno.cgd.ProfessorDAO;
import academico.controlepauta.cgt.AplCadastrarUsuario;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import academico.util.pessoa.cdp.*;
import academico.util.pessoa.cgd.BairroDAOJPA;
import academico.util.pessoa.cgd.EstadoDAOJPA;
import academico.util.pessoa.cgd.MunicipioDAOJPA;
import java.util.*;
import org.zkoss.zul.Messagebox;

/**
 * Classe de aplicação responsável por realizar os casos de uso relativos ao
 * Aluno e ao Professor
 * <p/>
 * @author Gabriel Quézid; Rodrigo Maia
 * @version 0.1
 * @see
 */
public class AplCadastrarPessoa {

    private DAO apDaoAluno = DAOFactory.obterDAO("JPA", Aluno.class);
    private DAO apDaoProfessor = DAOFactory.obterDAO("JPA", Professor.class);
    private DAO apDaoAreaConhecimento = DAOFactory.obterDAO("JPA", AreaConhecimento.class);
    private DAO apDaoPais = DAOFactory.obterDAO("JPA", Pais.class);
    private DAO apDaoEstado = DAOFactory.obterDAO("JPA", Estado.class);
    private DAO apDaoMunicipio = DAOFactory.obterDAO("JPA", Municipio.class);
    private DAO apDaoBairro = DAOFactory.obterDAO("JPA", Bairro.class);
    private DAO apDaoCurso = DAOFactory.obterDAO("JPA", Curso.class);
    private AplCadastrarCalendario aplCadastrarCalendario = AplCadastrarCalendario.getInstance();
    private static AplCadastrarPessoa instance = null;

    private AplCadastrarPessoa() {
        //TODO criar uma tabela para armazenar o sequencial e quando iniciar arrumar o hashmap
    }

    public static AplCadastrarPessoa getInstance() {
        if (instance == null) {
            instance = new AplCadastrarPessoa();
        }
        return instance;
    }

    /**
     * Inclui os dados de um Aluno no sistema
     * <p/>
     * @param args
     * @return
     * @throws Exception
     */
    public Aluno incluirAluno(ArrayList<Object> args) throws Exception {
        Aluno aluno = new Aluno();
        String matricula = this.gerarMatricula((Curso) args.get(10));
        if (matricula != null) {
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
            aluno.setCurso((Curso) args.get(10));
            aluno.setMatricula(matricula);
            return (Aluno) apDaoAluno.salvar(aluno);
        }

        // Privilegios...
        //1 = Admin, 2 = Func, 3 = Prof, 4 = Aluno
        AplCadastrarUsuario.getInstance().incluirUsuario(matricula, "1234", new Integer(4));
        //alert("BD Ok!");
        return (Aluno) apDaoAluno.salvar(aluno);
    }

    /**
     * Altera os dados do Aluno no sitema
     * <p/>
     * @param aluno
     * @return
     * @throws Exception
     */
    public Aluno alterarAluno(Aluno aluno) throws Exception {
        return (Aluno) apDaoAluno.salvar(aluno);
    }

    /**
     * Apaga os dados de um Aluno no sistema
     * <p/>
     * @param aluno
     * @throws Exception
     */
    public void apagarAluno(Aluno aluno) throws Exception {
        apDaoAluno.excluir(aluno);
    }

    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * <p/>
     * @return
     */
    public List<Aluno> obterAlunos() throws AcademicoException {
        return (List<Aluno>) apDaoAluno.obter(Aluno.class);
    }

    /**
     * Obtém uma lista de todos os Alunos cadastrados
     *
     * @return
     */
    public List<Aluno> obterAlunosporTurma(Turma t) {
        return ((AlunoDAOJPA) apDaoAluno).obterAlunosporTurma(t);
    }

    /**
     * Inclui os dados de um Professor no sistema
     * <p/>
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

        // Privilegios...
        //1 = Admin, 2 = Func, 3 = Prof, 4 = Aluno
        AplCadastrarUsuario.getInstance().incluirUsuario(args.get(5) + "", "1234", new Integer(3));
        return (Professor) apDaoProfessor.salvar(professor);
    }

    /**
     * Altera os dados do Professor no sitema
     * <p/>
     * @param professor
     * @return
     * @throws Exception
     */
    public Professor alterarProfessor(Professor professor) throws Exception {
        return (Professor) apDaoProfessor.salvar(professor);
    }

    /**
     * Apaga os dados de um Professor no sistema
     * <p/>
     * @param professor
     * @throws Exception
     */
    public void apagarProfessor(Professor professor) throws Exception {
        apDaoProfessor.excluir(professor);
    }

    /**
     * Obtém uma lista de todos os Professor cadastrados
     * <p/>
     * @return
     */
    public List<Professor> obterProfessor() throws AcademicoException {
        return (List<Professor>) apDaoProfessor.obter(Professor.class);
    }

    /**
     * Obtém uma lista com todas as Áreas de conhecimento
     * <p/>
     * @return
     */
    public List<AreaConhecimento> obterAreaConhecimentos() throws AcademicoException {
        return (List<AreaConhecimento>) apDaoAreaConhecimento.obter(AreaConhecimento.class);
    }

    /**
     * Obtém uma lista com todos os Países
     * <p/>
     * @return
     */
    public List<Pais> obterPais() throws AcademicoException {
        return (List<Pais>) apDaoPais.obter(Pais.class);
    }

    /**
     * Obtém uma lista com todos os Estados
     * <p/>
     * @return
     */
    public List<Estado> obterEstados(Pais pais) throws AcademicoException {
        return (List<Estado>) ((EstadoDAOJPA) apDaoEstado).obter(pais);
    }

    /**
     * Obtém uma lista com todos os Municipios
     * <p/>
     * @return
     */
    public List<Municipio> obterMunicipio(Estado estado) throws AcademicoException {
        return (List<Municipio>) ((MunicipioDAOJPA) apDaoMunicipio).obter(estado);
    }

    /**
     * Obtém uma lista com todos os Bairros
     * <p/>
     * @return
     */
    public List<Bairro> obterBairro(Municipio municipio) throws AcademicoException {
        return (List<Bairro>) ((BairroDAOJPA) apDaoBairro).obter(municipio);
    }

    /**
     * Obtém uma lista com todas os cursos
     * <p/>
     * @return
     */
    public List<Curso> obterCursos() throws AcademicoException {
        return (List<Curso>) apDaoCurso.obter(Curso.class);
    }

    private String gerarMatricula(Curso curso) throws AcademicoException {
        List<Calendario> calendarios = aplCadastrarCalendario.obterCalendarios(curso);
        Calendario c = null;
        String matricula = null;
        Integer sequencial;
        for (Calendario calendario : calendarios) {
            if (calendario.getDataInicioCA().before(Calendar.getInstance()) && calendario.getDataFimCA().after(Calendar.getInstance())) {
                c = calendario;
                break;
            }
        }
        if (c == null) {
            Messagebox.show("Cadastre um Calendário Primeiro");
        } else {
            matricula = c.getIdentificador() + curso.getSigla();
            sequencial = c.getSequencial();
            if (sequencial < 10) {
                matricula += "000" + sequencial;
            } else if (sequencial < 100) {
                matricula += "00" + sequencial;
            } else if (sequencial < 1000) {
                matricula += "0" + sequencial;
            }

            c.setSequencial(c.getSequencial() + 1);

        }

        return matricula;
    }

    public Aluno obterAluno(String matricula) {
        return ((AlunoDAO) apDaoAluno).obterAluno(matricula);
    }
    
    public Professor obterProfessor(String CPF) {
        return ((ProfessorDAO) apDaoProfessor).obterProfessor(CPF);
    }
}
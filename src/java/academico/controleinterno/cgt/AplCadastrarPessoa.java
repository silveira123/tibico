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
import academico.controleinterno.cgd.ProfessorDAO;
import academico.controlepauta.cdp.Usuario;
import academico.controlepauta.cgt.AplCadastrarUsuario;
import academico.controlepauta.cgt.AplControlarMatricula;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import academico.util.pessoa.cdp.*;
import academico.util.pessoa.cgd.BairroDAOJPA;
import academico.util.pessoa.cgd.EstadoDAOJPA;
import academico.util.pessoa.cgd.MunicipioDAOJPA;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Classe de aplicação responsável por realizar os casos de uso relativos ao Aluno e ao Professor
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
    private AplCadastrarCalendarioSala aplCadastrarCalendario = AplCadastrarCalendarioSala.getInstance();
    private static AplCadastrarPessoa instance = null;

    private AplCadastrarPessoa() {
    }

    public static AplCadastrarPessoa getInstance() {
        if (instance == null) {
            instance = new AplCadastrarPessoa();
        }
        return instance;
    }

    //////////////////// ALUNO ////////////////////
    /**
     * Inclui um novo aluno.
     */
    public Aluno incluirAluno(List<Object> args) throws AcademicoException {
        Aluno aluno = new Aluno();
        ArrayList<Telefone> listTelefones;
        Endereco e;

        String matricula = this.gerarMatricula((Curso) args.get(10));
        if (matricula != null) {
            aluno.setNome((String) args.get(0));
            aluno.setSexo((Sexo) args.get(1));
            aluno.setDataNascimento((Calendar) args.get(2));

            listTelefones = setTelefones((ArrayList<Object>) args.get(3));
            aluno.setTelefone(listTelefones);

            aluno.setEmail((String) args.get(4));
            aluno.setCpf((String) args.get(5));
            aluno.setIdentidade((String) args.get(6));
            aluno.setNomeMae((String) args.get(7));
            aluno.setNomePai((String) args.get(8));

            e = setEndereco((ArrayList<Object>) args.get(9));
            aluno.setEndereco(e);

            aluno.setCurso((Curso) args.get(10));
            aluno.setFoto((byte[]) args.get(11));
            aluno.setMatricula(matricula);
            aluno.setCoeficiente(0.0);
        }
        aluno = (Aluno) apDaoAluno.salvar(aluno);
        // Privilegios...
        //1 = Admin, 2 = Func, 3 = Prof, 4 = Aluno
        AplCadastrarUsuario.getInstance().incluirUsuario(matricula, "1234", new Integer(4), ((AlunoDAO) apDaoAluno).obterAluno(aluno.getId()));
        //alert("BD Ok!");
        return aluno;
    }

    /**
     * Altera os dados do Aluno no sitema
     * <p/>
     * @param aluno
     * @return
     * @throws Exception
     */
    public Aluno alterarAluno(Aluno aluno) throws AcademicoException {
        return (Aluno) apDaoAluno.salvar(aluno);
    }

    /**
     * Apaga os dados de um Aluno no sistema
     * <p/>
     * @param aluno
     * @throws Exception
     */
    public boolean apagarAluno(Aluno aluno) throws AcademicoException {
        if(!AplControlarMatricula.getInstance().obterMatriculadas(aluno).isEmpty())
            return false;
        if(!AplControlarMatricula.getInstance().emitirHistorico(aluno).isEmpty())
            return false;
        
        Usuario usuario = AplCadastrarUsuario.getInstance().obter(aluno);
        AplCadastrarUsuario.getInstance().apagarUsuario(usuario);
        apDaoAluno.excluir(aluno);
        return true;
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
     * Obtém uma lista de todos os Alunos pesquisados
     * <p/>
     * @return
     */
    public List<Aluno> obterAlunosPesquisa(String pesquisa) throws AcademicoException {
        return ((AlunoDAO) apDaoAluno).obterAluno(pesquisa);
    }
    
    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * <p/>
     * @return
     */
    public List<Aluno> obterAlunosporTurma(Turma t) {
        return ((AlunoDAO) apDaoAluno).obterAlunosporTurma(t);
    }
    
    public List<Aluno> obterAlunosporCurso(Curso c) {
        return ((AlunoDAO) apDaoAluno).obterAlunosporCurso(c);
    }

    //////////////////// PROFESSOR ////////////////////
    /**
     * Inclui um novo professor.
     */
    public Professor incluirProfessor(List<Object> args) throws AcademicoException {
        Professor professor = new Professor();
        ArrayList<Telefone> listTelefones;
        Endereco e;

        professor.setNome((String) args.get(0));
        professor.setSexo((Sexo) args.get(1));
        professor.setDataNascimento((Calendar) args.get(2));

        listTelefones = setTelefones((ArrayList<Object>) args.get(3));
        professor.setTelefone(listTelefones);

        professor.setEmail((String) args.get(4));
        professor.setCpf((String) args.get(5));
        professor.setIdentidade((String) args.get(6));

        e = setEndereco((ArrayList<Object>) args.get(7));
        professor.setEndereco(e);

        professor.setGrauInstrucao((GrauInstrucao) args.get(8));

        professor.setAreaConhecimento((ArrayList<AreaConhecimento>) args.get(9));

        professor.setFoto((byte[]) args.get(10));
        
        List<Professor> listProf = this.obterProfessor();
        //verifica se ja existe um cpf igual cadastrado
        for (Professor p : listProf) {
            if(p.getCpf().equals(professor.getCpf()))
                return null;
        }
                
        professor = (Professor) apDaoProfessor.salvar(professor);
        // Privilegios...
        //1 = Admin, 2 = Func, 3 = Prof, 4 = Aluno
        AplCadastrarUsuario.getInstance().incluirUsuario(professor.getCpf() + "", "1234", new Integer(3), ((ProfessorDAO) apDaoProfessor).obterProfessor(professor.getId()));
        return professor;
    }

    public ArrayList<Telefone> setTelefones(List<Object> listTelefones) {
        ArrayList<Telefone> lista = new ArrayList<Telefone>();
        Telefone tel;

        for (int i = 0; i < listTelefones.size(); i = i + 3) {
            tel = new Telefone();
            if (listTelefones.get(i) != null) {
                tel.setDdd((String) listTelefones.get(i));
                tel.setNumero((String) listTelefones.get(i + 1));
                tel.setTipo((TipoTel) listTelefones.get(i + 2));
            }
            else {
                tel.setDdd(null);
                tel.setNumero(null);
                tel.setTipo((TipoTel) listTelefones.get(i + 2));
            }
            lista.add(tel);
        }

        return lista;
    }

    public Endereco setEndereco(List<Object> listEndereco) {
        Endereco e = new Endereco();

        e.setLogradouro((String) listEndereco.get(0));
        e.setCep((Long) listEndereco.get(1));
        e.setNumero((Integer) (listEndereco.get(2)));
        e.setComplemento((String) listEndereco.get(3));
        e.setBairro((Bairro) listEndereco.get(4));

        return e;
    }

    /**
     * Altera os dados do Professor no sitema
     * <p/>
     * @param professor
     * @return
     * @throws Exception
     */
    public Professor alterarProfessor(Professor professor) throws AcademicoException {
        List<Professor> listProf = this.obterProfessor();
        //verifica se ja existe um cpf igual cadastrado e nao for o que ja estava cadastrado
        for (Professor p : listProf) {
            if(p.getCpf().equals(professor.getCpf()) && p.getId()!=professor.getId())
                return null;
        }
        
        return (Professor) apDaoProfessor.salvar(professor);
    }

    /**
     * Apaga os dados de um Professor no sistema
     * <p/>
     * @param professor
     * @throws Exception
     */
    public boolean apagarProfessor(Professor professor) throws AcademicoException {
        if(!AplControlarTurma.getInstance().obterTurmas(professor).isEmpty())
            return false;
        
        Usuario usuario = AplCadastrarUsuario.getInstance().obter(professor);
        AplCadastrarUsuario.getInstance().apagarUsuario(usuario);
        apDaoProfessor.excluir(professor);
        
        return true;
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
     * Obtém uma lista de todos os Professores pesquisados
     * <p/>
     * @return
     */
    public List<Professor> obterProfessorPesquisa(String nome) throws AcademicoException {
        return ((ProfessorDAO) apDaoProfessor).obterProfessor(nome);
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
            if (Calendario.estaEntre(calendario.getDataInicioCA(), calendario.getDataFimCA())) {
                c = calendario;
                break;
            }
        }

        matricula = c.getIdentificador() + curso.getSigla();
        sequencial = c.getSequencial();
        if (sequencial < 10) {
            matricula += "000" + sequencial;
        }
        else if (sequencial < 100) {
            matricula += "00" + sequencial;
        }
        else if (sequencial < 1000) {
            matricula += "0" + sequencial;
        }

        c.setSequencial(c.getSequencial() + 1);

        return matricula;
    }

    
    public List<Professor> obterProfessor(Calendario c) {
        return ((ProfessorDAO) apDaoProfessor).obterProfessor(c);
    }

    public Usuario obterUsuario(Pessoa p) {
        return AplCadastrarUsuario.getInstance().obter(p);
    }

    public Usuario alterarUsuario(Usuario usuario) throws AcademicoException {
        return AplCadastrarUsuario.getInstance().alterarUsuario(usuario);
    }
}
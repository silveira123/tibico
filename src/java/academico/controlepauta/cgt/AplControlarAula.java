/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controlepauta.cgt;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.controlepauta.cdp.*;
import academico.controlepauta.cgd.AulaDAO;
import academico.controlepauta.cgd.FrequenciaDAO;
import academico.controlepauta.cgd.MatriculaTurmaDAO;
import academico.controlepauta.cgd.ResultadoDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

/**
 * Turma
 *
 * @author pietrochrist
 */
public class AplControlarAula {

    private DAO apDaoAvaliacao = DAOFactory.obterDAO("JPA", Avaliacao.class);
    private DAO apDaoResultado = DAOFactory.obterDAO("JPA", Resultado.class);
    private DAO apDaoAula = DAOFactory.obterDAO("JPA", Aula.class);
    private DAO apDaoFrequencia = DAOFactory.obterDAO("JPA", Frequencia.class);
    private DAO apDaoMatriculaTurma = DAOFactory.obterDAO("JPA", MatriculaTurma.class);
    private AplControlarMatricula aplControlarMatricula = AplControlarMatricula.getInstance();

    private AplControlarAula() {
    }
    private static AplControlarAula instance = null;

    public static AplControlarAula getInstance() {
        if (instance == null) {
            instance = new AplControlarAula();
        }
        return instance;
    }

    public Avaliacao incluirAvaliacao(ArrayList<Object> args) throws AcademicoException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setTurma((Turma) args.get(0));
        avaliacao.setNome((String) args.get(1));
        avaliacao.setPeso((Integer) args.get(2));
        return (Avaliacao) apDaoAvaliacao.salvar(avaliacao);
    }

    public Avaliacao alterarAvaliacao(Avaliacao avaliacao) throws Exception {
        return (Avaliacao) apDaoAvaliacao.salvar(avaliacao);
    }

    public void apagarAvaliacao(Avaliacao avaliacao) throws Exception {
        apDaoAvaliacao.excluir(avaliacao);
    }

    public List<Avaliacao> obterAvaliacoes() throws AcademicoException {
        return (List<Avaliacao>) apDaoAvaliacao.obter(Avaliacao.class);
    }

    public void incluirResultado(Avaliacao obj, List<Object> notas, List<Object> observacoes, List<MatriculaTurma> matriculaturma) throws AcademicoException {
        List<Resultado> lista = apDaoResultado.obter(Resultado.class);
        boolean possui = false;
        int j = 0;

        for (int i = 0; i < notas.size(); i++) {
            Resultado resultado = new Resultado();
            resultado.setAvaliacao(obj);
            resultado.setObservacao((String) observacoes.get(i));
            resultado.setPontuacao((Double) notas.get(i));
            resultado.setMatriculaTurma(matriculaturma.get(i));

            apDaoResultado.salvar(resultado);

        }
    }

    public Aula incluirAula(ArrayList<Object> args) throws AcademicoException {
        Aula aula = new Aula();
        aula.setDia((Calendar) args.get(0));
        aula.setQuantidade((Integer) args.get(1));
        aula.setConteudo((String) args.get(2));
        aula.setTurma((Turma) args.get(3));
        aula.setFrequencia((List<Frequencia>) args.get(4));
        aplControlarMatricula.editarFrequencia(aula.getFrequencia());
        return (Aula) apDaoAula.salvar(aula);

    }

    public Aula alterarAula(Aula aula, List<Frequencia> frequencia) throws Exception {

        aplControlarMatricula.editarFrequencia(frequencia, aula.getFrequencia());
        return (Aula) apDaoAula.salvar(aula);

    }

    public void apagarAula(Aula aula) throws Exception {
        apDaoAula.excluir(aula);
    }

    public List<Frequencia> obterFrequencias() throws AcademicoException {
        return (List<Frequencia>) apDaoFrequencia.obter(Frequencia.class);
    }

    public List<Frequencia> obterFrequencias(Aluno a, Turma t) throws AcademicoException {
        return (List<Frequencia>) ((FrequenciaDAO) apDaoFrequencia).obterFrequencias(a, t);
    }

    public List<Resultado> obterResultados() throws AcademicoException {
        return (List<Resultado>) apDaoResultado.obter(Resultado.class);
    }

    public List<Resultado> obterResultados(Aluno a) throws AcademicoException {
        return (List<Resultado>) ((ResultadoDAO) apDaoResultado).obterResultados(a);
    }

    public List<Aula> obterAulas(Turma turma) throws AcademicoException {
        return (List<Aula>)((AulaDAO)apDaoAula).obter(turma); 
    }

    public List<Frequencia> obterFrequencias(Turma t) {
        return (List<Frequencia>) ((FrequenciaDAO) apDaoFrequencia).obterFrequencias(t);
    }

    public void apagarFrequencia(Frequencia frequencia) throws Exception {
        aplControlarMatricula.excluirFrequencia(frequencia);
        apDaoFrequencia.excluir(frequencia);

    }

    public Aluno obterAluno(String matricula) {
        return AplCadastrarPessoa.getInstance().obterAluno(matricula);
    }

    public Professor obterProfessor(String CPF) {
        return AplCadastrarPessoa.getInstance().obterProfessor(CPF);
    }

    public List<Resultado> obterResultados(Avaliacao obj) {
        return (List<Resultado>) ((ResultadoDAO) apDaoResultado).obterResultados(obj);
    }
    
    public void atribuirResultado(Avaliacao a, Turma t) throws AcademicoException {
        List<MatriculaTurma> mTurma = (List<MatriculaTurma>) ((MatriculaTurmaDAO)apDaoMatriculaTurma).obter(t);
        Resultado resultado = null;
        
        for(int i=0; i<mTurma.size(); i++){
            resultado = new Resultado(a, mTurma.get(i));
            apDaoResultado.salvar(resultado);           
        }
              
    }
}

/*
 * AplControlarMatriculaTurma.java 
 * Versão: 0.1 
 * Data de Criação : 27/06/2012, 13:39:17
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
package academico.controlepauta.cgt;

// imports devem ficar aqui!
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgd.DisciplinaDAO;
import academico.controleinterno.cgd.TurmaDAO;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.SituacaoAlunoTurma;
import academico.controlepauta.cgd.MatriculaTurmaDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe controla as funcões de manipulação de MatriculaTurma.
 * <p/>
 * @author erigamonte
 * @version
 * @see
 */
public class AplControlarMatricula {

    private DAO apDaoMatriculaTurma = DAOFactory.obterDAO("JPA", MatriculaTurma.class);

    private AplControlarMatricula() {
    }
    private static AplControlarMatricula instance = null;

    public static AplControlarMatricula getInstance() {
        if (instance == null) {
            instance = new AplControlarMatricula();
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
    public MatriculaTurma efetuarMatricula(ArrayList<Object> args) throws AcademicoException {
        MatriculaTurma matriculaTurma = new MatriculaTurma();

        Aluno aluno = (Aluno) args.get(0);
        matriculaTurma.setAluno(aluno);
        Turma turma = (Turma) args.get(1);
        matriculaTurma.setTurma(turma);

        return (MatriculaTurma) apDaoMatriculaTurma.salvar(matriculaTurma);
    }

    /**
     * Cancela (exclui) uma matricula de um aluno em uma turma.
     * <p/>
     * @param matriculaTurma Matricula que será cancelada.
     * @return void
     * @throws AcademicoException caso não seja possivel excluir a matricula.
     */
    public void cancelarMatricula(MatriculaTurma matriculaTurma) throws AcademicoException {
        apDaoMatriculaTurma.excluir(matriculaTurma);
    }

    /**
     * Obtem todas as matriculas feitas por um determinado aluno na situação de MATRICULADO.
     * <p/>
     * @param aluno Aluno que terá as matriculas buscadas.
     * @return List<MatriculaTurma> Lista de MatriculaTurma.
     * @throws AcademicoException caso não seja possivel buscar as matriculas.
     */
    public List<MatriculaTurma> obterMatriculadas(Aluno aluno) throws AcademicoException {
        return (List<MatriculaTurma>) ((MatriculaTurmaDAO) apDaoMatriculaTurma).obter(aluno, SituacaoAlunoTurma.MATRICULADO);
    }

    /**
     * Obtem o histórico do aluno.
     * <p/>
     * @param aluno Aluno que deverá
     * @return List<MatriculaTurma> Lista de MatriculaTurma referente ao histórico do aluno.
     * @throws AcademicoException caso não seja possivel possivel buscar as matriculas.
     */
    public List<MatriculaTurma> emitirHistorico(Aluno aluno) throws AcademicoException {
        return (List<MatriculaTurma>) ((MatriculaTurmaDAO) apDaoMatriculaTurma).obter(aluno, SituacaoAlunoTurma.APROVADO);
    }

    /**
     * Obtem o boletim do aluno em um calendário academico.
     * <p/>
     * @param aluno Aluno que terá o boletim buscado.
     * @param calendario Calendário que definirá qual boletim buscar.
     * @return List<MatriculaTurma> Lista de MatriculaTurma representado o boletim.
     * @throws AcademicoException Caso não consiga buscar o boletim.
     */
    public List<MatriculaTurma> emitirBoletim(Aluno aluno, Calendario calendario) throws AcademicoException {
        return (List<MatriculaTurma>) ((MatriculaTurmaDAO) apDaoMatriculaTurma).obter(aluno, calendario);
    }

    public List<MatriculaTurma> obter(Turma t) {
        return (List<MatriculaTurma>) ((MatriculaTurmaDAO) apDaoMatriculaTurma).obter(t);
    }

    public List<Turma> obterTurmasPossiveis(Aluno aluno) {
        //recupera as disciplinas que o aluno já está matriculado, cursando ou ja foi aprovado
        List<Disciplina> disciplinas = ((DisciplinaDAO) DAOFactory.obterDAO("JPA", Disciplina.class)).obterVinculadas(aluno);

        //pega as turmas do calendario atual do curso do aluno
        List<Turma> turmas = ((TurmaDAO) DAOFactory.obterDAO("JPA", Turma.class)).obterTurmasAtuais(aluno);

        //tira do array as disciplinas que o aluno já está vinculado
        for (int i = 0; i < turmas.size();) {
            if (disciplinas.contains(turmas.get(i).getDisciplina())) {
                turmas.remove(i);
            }
            else {
                i++;
            }
        }

        return turmas;
    }

    /**
     * Obtem todos os calendarios que o Aluno teve uma matricula vinculada.
     * <p/>
     * @param aluno Aluno que terá as matriculas buscadas.
     * @return List<Calendario> Lista de MatriculaTurma.
     * @throws AcademicoException caso não seja possivel buscar os calendarios.
     */
    public List<Calendario> buscaCalendarios(Aluno a) {
        return (List<Calendario>) ((MatriculaTurmaDAO) apDaoMatriculaTurma).obterCalendarios(a);
        
    }
}

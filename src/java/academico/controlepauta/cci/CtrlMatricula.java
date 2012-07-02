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
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cgt.AplControlarMatricula;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;


/**
 * Controladora das aplicações de controle de matricula e de emitir relatórios. 
 * 
 * @author erigamonte
 * @version 0.1
 * @see
 */
public class CtrlMatricula {

    //Constantes:
    private AplControlarMatricula apl = AplControlarMatricula.getInstance();
    //Variáveis de Classe:

    //Variáveis de Instância:
    
    //Contrutores:
    private CtrlMatricula() {
    }

    private static CtrlMatricula instance = null;

    public static CtrlMatricula getInstance() {
        if (instance == null) {
            instance = new CtrlMatricula();
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
        return apl.efetuarMatricula(args);
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
    public List<MatriculaTurma> emitirHistorico(Aluno aluno) throws AcademicoException {
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
    public List<MatriculaTurma> emitirBoletim(Aluno aluno, Calendario calendario) throws AcademicoException {
        return apl.emitirBoletim(aluno, calendario);
    }
    
    public List<MatriculaTurma> obter(Turma t) throws AcademicoException
    {
        return apl.obter(t);
    }

 }

/*
 * MatriculaTurmaDAO.java 
 * Versão: 0.1 
 * Data de Criação : 11/06/2012, 13:16:55
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

package academico.controlepauta.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.SituacaoAlunoTurma;
import academico.util.persistencia.DAO;
import java.util.List;


/**
 * Esta interface, faz herança com uma interface que faz referencia com o padrão DAO do tipo genérico
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public interface MatriculaTurmaDAO extends DAO<MatriculaTurma> {
   
     /**
     * Enunciado da função que obtém todas as matriculaTurma de um aluno
     * @param aluno
     * @return 
     */
    public List<MatriculaTurma> obter(Aluno aluno);
    
    /**
     * Enunciado da função que obtém todas as MatriculaTurma, através de um aluno com uma situação acadêmica especifica
     * @param aluno
     * @param situacao
     * @return 
     */
    public List<MatriculaTurma> obter(Aluno aluno, SituacaoAlunoTurma situacao);
    
    /**
     * Enunciado da função que obtém todas as MatriculaTurma, através de um aluno e calendário especifico
     * @param aluno
     * @param calendario
     * @return 
     */
    public List<MatriculaTurma> obter(Aluno aluno, Calendario calendario);
    
    /**
     * Enunciado da função que obtém todas as MatriculaTurma de uma turma
     * @param t
     * @return 
     */
    public List<MatriculaTurma> obter(Turma t);
    
    /**
     * Enunciado da função que obtém todos os calendários, através de um aluno
     * @param aluno
     * @return 
     */
    public List<Calendario> obterCalendarios(Aluno aluno);
    
    /**
     * Enunciado da função que obtém todas as matriculaTurma de um aluno, em turmas que já foram finalizadas
     * @param aluno
     * @return 
     */
    public List<MatriculaTurma> obterCursadas(Aluno aluno);

}

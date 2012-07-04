/*
 * MatriculaTurmaDAO.java 
 * Versão: _._ 
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
    public List<MatriculaTurma> obter(Aluno aluno);
    public List<MatriculaTurma> obter(Aluno aluno, SituacaoAlunoTurma situacao);
    public List<MatriculaTurma> obter(Aluno aluno, Calendario calendario);
    public List<MatriculaTurma> obter(Turma t);
    public List<Calendario> obterCalendarios(Aluno aluno);
    public List<MatriculaTurma> obterCursadas(Aluno aluno);
}

/*
 * AlunoDAO.java 
 * Versão: _._ 
 * Data de Criação : 13/06/2012, 15:44:12
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

package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Turma;
import academico.util.persistencia.DAO;
import java.util.List;


/**
 * Esta interface, faz herança com uma interface que faz referencia com o padrão DAO do tipo genérico
 * 
 * @author Fábrica de Software
 * @version
 * @see
 */
public interface AlunoDAO extends DAO<Aluno>{
    public List<Aluno> obterAlunosporTurma(Turma t);
    public Aluno obterAluno(Long id);
    public List<Aluno> obterAlunosporCurso(Curso c);
    public List<Aluno> obterAluno(String parametro);
}
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

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.ArrayList;
import java.util.List;


/**
 * <<descrição da Classe>> 
 * 
 * @author Gabriel Quézid; Rodrigo Maia
 * @version 0.1
 * @see
 */
public class CtrlPessoa {

public static final int SALVAR = 0;
    public static final int EDITAR = 1;
    public static final int CONSULTAR = 2;
    private AplCadastrarPessoa apl = AplCadastrarPessoa.getInstance();
    private static CtrlPessoa instance = null;

    public static CtrlPessoa getInstance() {
        if (instance == null) {
            instance = new CtrlPessoa();
        }
        return instance;
    }
    
    public CtrlPessoa() {
    }
    
    /**
     * Inclui os dados de um Aluno no sistema
     * @param args
     * @return
     * @throws Exception 
     */
    public Aluno incluirAluno(ArrayList<Object> args) throws Exception {
        return apl.incluirAluno(args);
    }

    /**
     * Altera os dados do Aluno no sitema
     * @param professor
     * @return
     * @throws Exception 
     */
    public Aluno alterarAluno(Aluno args) throws Exception {
        return apl.alterarAluno(args);
    }

    /**
     * Apaga os dados de um Aluno no sistema
     * @param aluno
     * @throws Exception 
     */
    public void apagarAluno(Aluno aluno) throws Exception {
        apl.apagarAluno(aluno);
    }

    /**
     * Obtém uma lista de todos os Alunos cadastrados
     * @return 
     */
    public List<Aluno> obterAlunos() {
        return apl.obterAlunos();
    }
    
    /**
     * Inclui os dados de um Professor no sistema
     * @param args
     * @return
     * @throws Exception 
     */
    public Professor incluirProfessor(ArrayList<Object> args) throws Exception {
        return apl.incluirProfessor(args);
    }

    /**
     * Altera os dados do Professor no sitema
     * @param professor
     * @return
     * @throws Exception 
     */
    public Professor alterarProfessor(Professor args) throws Exception {
        return apl.alterarProfessor(args);
    }

    /**
     * Apaga os dados de um Professor no sistema
     * @param professor
     * @throws Exception 
     */
    public void apagarProfessor(Professor professor) throws Exception {
        apl.apagarProfessor(professor);
    }

    /**
     * Obtém uma lista de todos os Professor cadastrados
     * @return 
     */
    public List<Professor> obterProfessor() {
        return apl.obterProfessor();
    }
    
    /**
     * Obtém uma lista com todas as Áreas de conhecimento
     * @return 
     */
    public List<AreaConhecimento> obterAreaConhecimento() {
        return apl.obterAreaConhecimentos();
    }
    
}
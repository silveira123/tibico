/*
 * SituacaoAlunoTurma.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:28:03
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

package academico.controlepauta.cdp;

/**
 * Este tipo enumerado descreve as possiveis sitação do aluno em uma turma
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see academico.controlepauta.cdp.SituacaoAlunoTurma.java
 */
public enum SituacaoAlunoTurma {
    MATRICULADO, CURSANDO, APROVADO, REPROVADONOTA, REPROVADOFALTA;
}
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

package academico.controleinterno.cdp;

/**
 * Este tipo enumerado descreve as possiveis sitação de um turma
 *
 * @author Pietro Dalmazio
 * @author Geann Valfré
 * @version 0.1
 * @see academico.controleinterno.cdp.EstadoTurma.java
 */
public enum EstadoTurma {
    ESPERA, MATRICULA_ABERTA, MATRICULA_FECHADA, EM_CURSO, ENCERRADA;
}
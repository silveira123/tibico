/*
 * AulaDAOJPA.java 
 * Versão: 0.1 
 * Data de Criação : 11/06/2012, 13:28:21
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

import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cdp.Aula;
import academico.util.persistencia.DAOJPA;
import java.util.List;


/**
 * Esta classe faz herança com DAOJPA e implementa AulaDAO
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public class AulaDAOJPA extends DAOJPA<Aula> implements AulaDAO{

    /**
     * Obtém todas as aulas de uma turma
     * @param turma
     * @return 
     */
    public List<Aula> obter(Turma turma) {
        List<Aula> aula = entityManager.createQuery("select a from Aula a where a.turma.id = ?1").setParameter(1, turma.getId()).getResultList();
         return aula;
    }

}
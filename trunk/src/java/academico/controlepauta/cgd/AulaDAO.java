/*
 * AulaDAO.java 
 * Versão: 0.1 
 * Data de Criação : 11/06/2012, 13:27:30
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
import academico.util.persistencia.DAO;
import java.util.List;


/**
 * Esta interface, faz herança com uma interface que faz referencia com o padrão DAO do tipo genérico
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public interface AulaDAO extends DAO<Aula>{
    
    /**
     * Enunciado da função que obtém todas as aulas de uma turma
     * @param turma
     * @return 
     */
    public List<Aula> obter(Turma turma);
}

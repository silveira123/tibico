/*
 * EnderecoDAOJPA.java 
 * Versão: 0.1 
 * Data de Criação : 29/05/2012
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
package academico.util.pessoa.cgd;

import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAOJPA;
import academico.util.pessoa.cdp.Estado;
import academico.util.pessoa.cdp.Pais;
import java.util.List;
import javax.persistence.Query;

/**
 * Esta classe faz herança com DAOJPA e implementa EstadoDAO
 * <p/>
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public class EstadoDAOJPA extends DAOJPA<Estado> implements EstadoDAO {

    public List<Estado> obter(Pais p) {
        Query query = entityManager.createQuery("Select pE from Estado pE where pE.pais.id = ?1 order by pE.nome ASC");
        query.setParameter( 1, p.getId());
        return query.getResultList();
    }
}

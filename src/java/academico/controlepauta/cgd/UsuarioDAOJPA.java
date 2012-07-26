/*
 * UsuarioDAOJPA.java 
 * Versão: 0.1 
 * Data de Criação : 11/06/2012
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

import academico.controlepauta.cdp.Usuario;
import academico.util.persistencia.DAOJPA;
import academico.util.pessoa.cdp.Pessoa;
import java.util.List;


/**
 * Esta classe faz herança com DAOJPA e implementa ResultadoDAO
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
public class UsuarioDAOJPA extends DAOJPA<Usuario> implements UsuarioDAO {
    
    /**
     * Obtém os dados do usuário através de um professor
     * @param p
     * @return 
     */
    public Usuario obterUsuario(Pessoa p) {
        List<Usuario> usuario = entityManager.createQuery("select u from Usuario u where u.pessoa.id = ?1").setParameter(1, p.getId()).getResultList();
        return usuario.get(0);
    }
    
}
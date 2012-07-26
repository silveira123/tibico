/*
 * UsuarioDAO.java 
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
import academico.util.persistencia.DAO;
import academico.util.pessoa.cdp.Pessoa;

public interface UsuarioDAO extends DAO<Usuario>
{
    /**
     * Enunciado da função que obtém os dados do usuário através de uma Pessoa
     * @param p
     * @return 
     */
    public Usuario obterUsuario(Pessoa p);
}


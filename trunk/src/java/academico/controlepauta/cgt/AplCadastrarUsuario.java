/*
 * AplCadastrarUsuario.java 
 * Versão: 0.1 
 * Data de Criação : 27/06/2012
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
package academico.controlepauta.cgt;

import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.controlepauta.cdp.Usuario;
import academico.controlepauta.cgd.UsuarioDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import academico.util.pessoa.cdp.Pessoa;
import java.util.List;

public class AplCadastrarUsuario {

    private DAO apDaoUsuario = DAOFactory.obterDAO("JPA", Usuario.class);
    private AplCadastrarPessoa aplCadastrarPessoa = AplCadastrarPessoa.getInstance();
    private static AplCadastrarUsuario instance = null;

    public static AplCadastrarUsuario getInstance() {
        if (instance == null) {
            instance = new AplCadastrarUsuario();
        }
        return instance;
    }

    public Usuario incluirUsuario(String nome, String senha, Integer privilegio, Pessoa p) throws AcademicoException {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenhaCriptografar(senha);
        usuario.setPrivilegio(privilegio);
        usuario.setPessoa(p);
        apDaoUsuario.salvar(usuario);
        return usuario;
    }

    public Usuario alterarUsuario(Usuario usuario) throws AcademicoException {
        usuario.setSenhaCriptografar(usuario.getSenha());
        return (Usuario) apDaoUsuario.salvar(usuario);
    }

    public boolean apagarUsuario(Usuario usuario) throws AcademicoException {
        if (usuario.getPrivilegio() != 0) {
            apDaoUsuario.excluir(usuario);
            return true;
        } else {
            return false;
        }
    }

    public List listarUsuarios() throws AcademicoException {
        return apDaoUsuario.obter(Usuario.class);
    }

    public Usuario validarUsuario(String nome, String senha) throws AcademicoException {
        List<Usuario> l = listarUsuarios();
        String senhaCrip = Usuario.gerarHashCode(senha);

        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getNome().toLowerCase().equals(nome.toLowerCase()) && l.get(i).getSenha().equals(senhaCrip)) {
                return l.get(i);
            }
        }
        return null;
    }

    public Usuario obter(Pessoa p) {
        return ((UsuarioDAO) apDaoUsuario).obterUsuario(p);
    }
}

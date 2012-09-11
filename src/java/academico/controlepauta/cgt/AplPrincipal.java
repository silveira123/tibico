/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controlepauta.cgt;

import academico.controlepauta.cdp.Usuario;
import academico.controlepauta.cgt.AplCadastrarUsuario;
import academico.util.Exceptions.AcademicoException;

public class AplPrincipal {

    private static AplPrincipal instance = null;

    private AplPrincipal() {
    }

    public static AplPrincipal getInstance() {
        if (instance == null) {
            instance = new AplPrincipal();
        }
        return instance;
    }

    private void conectarBD() {
    }

    private void desconectarBD() {
    }

    public Usuario login(String login, String senha) throws AcademicoException {
        return AplCadastrarUsuario.getInstance().validarUsuario(login, senha);
    }

    public void logout() {
    }
}

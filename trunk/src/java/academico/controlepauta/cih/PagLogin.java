/*
 * PagLogin.java 
 * Versão: 0.1 
 * Data de Criação : 15/06/2012
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

package academico.controlepauta.cih;

import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cci.CtrlCadastrarUsuario;
import academico.controlepauta.cdp.Usuario;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados.
 * A classe contém os dados do login, abrangendo a leitura e interpretação para a tela PagLogin.zul;
 * @author Pietro Crhist 
 * @author Geann Valfré
 */
public class PagLogin extends GenericForwardComposer {

    private Window loginWin;
    private Textbox nome;
    private Textbox senha;
    private Label msg;
    private CtrlCadastrarUsuario ctrl = CtrlCadastrarUsuario.getInstance();

    public void onCreate$loginWin(Event event) {
        if (Executions.getCurrent().getSession().getAttribute("usuario") != null) {
            CtrlAula.getInstance().abrirPaginaPrincipal();
            loginWin.detach();
        }
    }

    public void onClick$entrar(Event event) {
        try {
            if (ctrl.validarUsuario(nome.getValue(), senha.getValue())) {
                msg.setValue("");

                List<Usuario> lista = ctrl.listarUsuarios();
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getNome().equals(nome.getValue())) {
                        Executions.getCurrent().getSession().setAttribute("usuario", lista.get(i));
                        CtrlAula.getInstance().abrirPaginaPrincipal();
                        break;
                    }
                }

                loginWin.detach();
            }
            else {
                msg.setValue("Usuário ou Senha inválida!");
            }
        }
        catch (AcademicoException ex) {
            Logger.getLogger(PagLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
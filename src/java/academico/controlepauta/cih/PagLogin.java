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
import academico.controlepauta.cdp.Usuario;
import academico.util.Exceptions.AcademicoException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os dados do login, abrangendo a leitura e interpretação para a tela
 * PagLogin.zul;
 * 
 * @author Pietro Crhist
 * @author Geann Valfré
 */
public class PagLogin extends GenericForwardComposer {

    private Window loginWin;
    private Textbox nome;
    private Textbox senha;
    private Label msg;
    private Checkbox checkbox;
    private CtrlAula ctrl = CtrlAula.getInstance();

    public void onCreate$loginWin(Event event) {
        //se ja existir um usuario logado ele eh direcionado para a pagina principal
        if (Executions.getCurrent().getSession().getAttribute("usuario") != null) {
            CtrlAula.getInstance().abrirPaginaPrincipal();
            loginWin.detach();
        }

        Cookie[] c = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();

        for (int i = 0; c != null && i < c.length; i++) {
            if (c[i].getName().equals("loginTibico")) {
                nome.setText(c[i].getValue());
                if (!nome.getText().equals("")) {
                    checkbox.setChecked(true);
                }
                else {
                    checkbox.setChecked(false);
                }
            }
            else if (c[i].getName().equals("senhaTibico")) {
                senha.setText(c[i].getValue());
            }
        }
    }

    public void onClientInfo$loginWin(ClientInfoEvent event)
    {
        loginWin.setHeight(event.getDesktopHeight() + "px");
        loginWin.setWidth(event.getDesktopWidth() + "px");
    }
    
    public void onOK$loginWin(Event event) {
        onClick$entrar(event);
    }

    
    
    public void onClick$entrar(Event event) {
        Usuario usuario = null;
        try {
            usuario = ctrl.login(nome.getValue(), senha.getValue());
        }
        catch (AcademicoException ex) {
            System.err.println(ex.getMessage());
            msg.setValue("Error");
        }
                
        if (usuario != null) {
            msg.setValue("");

            Executions.getCurrent().getSession().setAttribute("usuario", usuario);
            CtrlAula.getInstance().abrirPaginaPrincipal();
            loginWin.detach();
        }
        else {
            msg.setValue("Usuário ou Senha inválida!");
        }

        if (checkbox.isChecked()) {
            ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(new Cookie("loginTibico", nome.getText()));
            ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(new Cookie("senhaTibico", senha.getText()));
        }
        else {
            Cookie[] c = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();

            for (int i = 0; c != null && i < c.length; i++) {
                if (c[i].getName().equals("loginTibico")) {
                    ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(new Cookie("loginTibico", ""));
                }
                else if (c[i].getName().equals("senhaTibico")) {
                    ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(new Cookie("senhaTibico", ""));
                }
            }
        }
    }
}
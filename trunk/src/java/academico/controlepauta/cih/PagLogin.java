package academico.controlepauta.cih;

import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cci.CtrlCadastrarUsuario;
import academico.controlepauta.cdp.Usuario;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class PagLogin extends GenericForwardComposer
{
	private Window loginWin;
	private Textbox nome;
	private Textbox senha;
	private Label msg;
	private CtrlCadastrarUsuario ctrl = CtrlCadastrarUsuario.getInstance();
	
	public void onCreate$loginWin(Event event)
	{
            loginWin.doHighlighted();
	}
	
	public void onClick$Entrar(Event event)
	{
            try {
                if (ctrl.validarUsuario(nome.getValue(), senha.getValue())) 
                {
                    msg.setValue("");
                    
                    List<Usuario> lista = ctrl.listarUsuarios();
                    for (int i = 0; i < lista.size(); i++) {
                        if(lista.get(i).getNome().equals(nome.getValue()))
                        {
                            CtrlAula.getInstance().abrirPaginaPrincipal(lista.get(i));
                            break;
                        }
                    }
                    
                    loginWin.detach();            
                } 
                else 
                {
                    msg.setValue("Usuário ou Senha inválida!");
                    Clients.evalJavaScript("loginFaild()");
                }
            } catch (AcademicoException ex) {
                Logger.getLogger(PagLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
package academico.controlepauta.cci;

import academico.controlepauta.cdp.Usuario;
import academico.controlepauta.cgt.AplCadastrarUsuario;
import academico.util.Exceptions.AcademicoException;
import java.util.List;

public class CtrlCadastrarUsuario 
{
	private AplCadastrarUsuario apl = AplCadastrarUsuario.getInstance();
        private static CtrlCadastrarUsuario instance = null;

        public static CtrlCadastrarUsuario getInstance() {
            if (instance == null) {
                instance = new CtrlCadastrarUsuario();
            }
            return instance;
        }
	
	public Usuario alterarUsuario(Usuario usuario, String nome, String senha, int privilegio) throws AcademicoException
	{
		return apl.alterarUsuario(usuario, nome, senha, privilegio);
	}
	
	public boolean apagarUsuario(Usuario usuario) throws AcademicoException 
	{
		return apl.apagarUsuario(usuario);
	}
	
	public List listarUsuarios() throws AcademicoException
	{
		return apl.listarUsuarios();
	}
	
	public boolean validarUsuario(String nome, String senha) throws AcademicoException
	{
		return apl.validarUsuario(nome, senha);
	}
}

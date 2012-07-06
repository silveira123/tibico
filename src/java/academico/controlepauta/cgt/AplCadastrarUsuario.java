package academico.controlepauta.cgt;

import academico.controlepauta.cdp.Usuario;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.List;

public class AplCadastrarUsuario 
{
        private DAO apDaoUsuario = DAOFactory.obterDAO("JPA", Usuario.class);
        private static AplCadastrarUsuario instance = null;

        public static AplCadastrarUsuario getInstance() {
            if (instance == null) {
                instance = new AplCadastrarUsuario();
            }
            return instance;
        }
        
	public Usuario incluirUsuario(String nome, String senha, Integer privilegio) throws AcademicoException
	{
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setPrivilegio(privilegio);
		apDaoUsuario.salvar(usuario);
        System.out.println("eaeee");
		return usuario;
	}
	
	public Usuario alterarUsuario(Usuario usuario, String nome, String senha, int privilegio) throws AcademicoException
	{
		usuario.setNome(nome);		
		usuario.setSenha(senha);		
		usuario.setPrivilegio(privilegio);		
		apDaoUsuario.salvar(usuario);
		return usuario;
	}
	
	public boolean apagarUsuario(Usuario usuario) throws AcademicoException 
	{
		if(usuario.getPrivilegio() != 0)
		{
			apDaoUsuario.excluir(usuario);
			return true;
		}
		else return false;
	}
	
	public List listarUsuarios() throws AcademicoException
	{
		return apDaoUsuario.obter(Usuario.class);
	}
	
	public boolean validarUsuario(String nome, String senha) throws AcademicoException
	{
		List<Usuario> l = listarUsuarios();
		
		for(int i=0; i < l.size() ;i++)
		{	if(l.get(i).getNome().equals(nome) && l.get(i).getSenha().equals(senha)) 
				return true;
		}
		return false;
	}
}

package academico.controlepauta.cgt;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.controlepauta.cdp.Usuario;
import academico.controlepauta.cgd.UsuarioDAO;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import academico.util.pessoa.cdp.Pessoa;
import java.util.List;

public class AplCadastrarUsuario 
{
    private DAO apDaoUsuario = DAOFactory.obterDAO("JPA", Usuario.class);
    private AplCadastrarPessoa aplCadastrarPessoa = AplCadastrarPessoa.getInstance();
    private static AplCadastrarUsuario instance = null;

    public static AplCadastrarUsuario getInstance() {
        if (instance == null) {
            instance = new AplCadastrarUsuario();
        }
        return instance;
    }
        
	public Usuario incluirUsuario(String nome, String senha, Integer privilegio, Pessoa p) throws AcademicoException
	{
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setPrivilegio(privilegio);
        usuario.setPessoa(p);
        apDaoUsuario.salvar(usuario);
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
	
	public Usuario validarUsuario(String nome, String senha) throws AcademicoException
	{
		List<Usuario> l = listarUsuarios();
		
		for(int i=0; i < l.size() ;i++)
		{	if(l.get(i).getNome().equals(nome) && l.get(i).getSenha().equals(senha)) 
				return l.get(i);
		}
		return null;
	}
    
    public Usuario obter(Aluno a) {
        return ((UsuarioDAO) apDaoUsuario).obterUsuario(a);
    }
    
    public Usuario obter(Professor p) {
        return ((UsuarioDAO) apDaoUsuario).obterUsuario(p);
    }
}

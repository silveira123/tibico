package academico.controlepauta.cgd;

import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Professor;
import academico.controlepauta.cdp.Usuario;
import academico.util.persistencia.DAO;

public interface UsuarioDAO extends DAO<Usuario>
{
    public Usuario obterUsuario(Aluno a); 
    public Usuario obterUsuario(Professor p);
}


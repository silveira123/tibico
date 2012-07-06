package academico.controlepauta.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.Entity;

@Entity
public class Usuario extends ObjetoPersistente
{
	private String nome;
	private String senha;
	private Integer privilegio;

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getSenha() 
	{
		return senha;
	}

	public void setSenha(String senha) 
	{
		this.senha = senha;
	}
	
	public Integer getPrivilegio() 
	{
		return privilegio;
	}

	public void setPrivilegio(Integer privilegio) 
	{
		this.privilegio = privilegio;
	}
}

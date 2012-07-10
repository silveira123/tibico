package academico.controlepauta.cdp;

import academico.util.persistencia.ObjetoPersistente;
import academico.util.pessoa.cdp.Pessoa;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Usuario extends ObjetoPersistente {

    private String nome;
    private String senha;
    private Integer privilegio;
    private Pessoa pessoa;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Integer privilegio) {
        this.privilegio = privilegio;
    }
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}

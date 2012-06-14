/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.pessoa.cdp;

import academico.util.persistencia.ObjetoPersistente;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Fábrica de Software
 */

@Entity
public class Pessoa extends ObjetoPersistente{
    private String nome;
    private Calendar dataNascimento;
    private long cpf;
    private String identidade;
    private String email;
    private Sexo sexo;
    private Endereco endereco;
    private List<Telefone> telefone;

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }
    
    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @OneToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Enumerated(EnumType.STRING)
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    @OneToMany(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= true)
    public List<Telefone> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }
    
}

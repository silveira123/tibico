/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.pessoa.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Fábrica de Software
 */
@Entity
public class Estado  extends ObjetoPersistente{
    private Pais pais;
    private String nome;
    private String sigla;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
}

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
public class Bairro extends ObjetoPersistente{
    private Municipio municipio;
    private String nome;
    
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}

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
public class Municipio extends ObjetoPersistente{
    private Estado estado;
    private String nome;
    
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(nullable= false)
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}

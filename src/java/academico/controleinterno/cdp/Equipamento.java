/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.Entity;

/**
 *
 * @author gmiranda
 */
@Entity
public class Equipamento extends ObjetoPersistente{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}

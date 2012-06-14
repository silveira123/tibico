/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.academico.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.Entity;

/**
 *
 * @author Fábrica de Software
 */
@Entity
public class GrandeAreaConhecimento extends ObjetoPersistente{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString()
    {
        return nome;
    }
}

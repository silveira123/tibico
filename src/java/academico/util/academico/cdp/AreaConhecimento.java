/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.academico.cdp;

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
public class AreaConhecimento extends ObjetoPersistente {

    private String nome;
    private GrandeAreaConhecimento gAreaConhecimento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public GrandeAreaConhecimento getgAreaConhecimento() {
        return gAreaConhecimento;
    }

    public void setgAreaConhecimento(GrandeAreaConhecimento gAreaConhecimento) {
        this.gAreaConhecimento = gAreaConhecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.pessoa.cdp;

import academico.util.persistencia.ObjetoPersistente;
import javax.persistence.Entity;

/**
 *
 * @author Fábrica de Software
 */
@Entity
public class Telefone extends ObjetoPersistente{
    private int numero;
    private int ddd;
    private int ddi;

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getDdi() {
        return ddi;
    }

    public void setDdi(int ddi) {
        this.ddi = ddi;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Fabrica de Software
 */
@MappedSuperclass
public abstract class ObjetoPersistente implements Serializable {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ObjetoPersistente) {
            ObjetoPersistente o = (ObjetoPersistente) obj;
            if (this.id != null && this.id == o.id) {
                return true;
            }
        }
        return false;
    }
}

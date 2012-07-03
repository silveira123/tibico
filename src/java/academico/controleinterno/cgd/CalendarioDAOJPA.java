/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

public class CalendarioDAOJPA extends DAOJPA<Calendario> implements CalendarioDAO 
{
        public List<Calendario> obter(Curso c) {
        Query query = entityManager.createQuery("Select pd from Calendario pd where pd.curso.id = ?1" );
        query.setParameter( 1, c.getId());
        return query.getResultList();
    }
}

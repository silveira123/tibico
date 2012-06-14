/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.persistencia.DAOJPA;
import java.util.List;
import javax.persistence.Query;

public class DisciplinaDAOJPA extends DAOJPA<Disciplina> implements DisciplinaDAO {

    public List<Disciplina> obter(Curso c) {
        Query query = entityManager.createQuery("Select pd from Disciplina pd where pd.curso.id = ?1" );
        query.setParameter( 1, c.getId());
        return query.getResultList();
    }
}

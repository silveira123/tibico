/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgd;

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.util.persistencia.DAOJPA;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;

public class CalendarioDAOJPA extends DAOJPA<Calendario> implements CalendarioDAO 
{
        public List<Calendario> obter(Curso c) {
        Query query = entityManager.createQuery("Select pd from Calendario pd where pd.curso.id = ?1" );
        query.setParameter( 1, c.getId());
        return query.getResultList();
    }

    public boolean verificarPeriodoMatricula(Curso curso) {
        Query query = entityManager.createQuery("Select pd from Calendario pd where pd.curso.id = ?1" 
                + "AND (?2 - pd.dataInicioPM >= 0 "
                + "AND ?2 - pd.dataFimPM <= 0)"
                );
        query.setParameter( 1, curso.getId());
        query.setParameter(2, Calendar.getInstance());
        List<Calendario> calendario = query.getResultList();
        if(calendario.size()>0)return true;
        else return false;
    }
}

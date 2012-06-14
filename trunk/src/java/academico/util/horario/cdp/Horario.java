/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.horario.cdp;

import academico.util.persistencia.ObjetoPersistente;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;

/**
 *
 * @author Fábrica de Software
 */
@Entity
public class Horario extends ObjetoPersistente{
    private Calendar horarioInicio;
    private Calendar horarioFim;
    private DiaSemana dia;
    
    @Enumerated(EnumType.STRING)
    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Calendar horarioFim) {
        this.horarioFim = horarioFim;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Calendar horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    
}

/*
 * Horario.java 
 * Versão: 0.1 
 * Data de Criação : 29/05/2012, 15:10:01
 * Copyright (c) 2012 Fabrica de Software IFES.
 * Incubadora de Empresas IFES, sala 11
 * Rodovia ES-010 - Km 6,5 - Manguinhos, Serra, ES, 29164-321, Brasil.
 * All rights reserved.
 *
 * This software is the confidential and proprietary 
 * information of Fabrica de Software IFES. ("Confidential Information"). You 
 * shall not disclose such Confidential Information and 
 * shall use it only in accordance with the terms of the 
 * license agreement you entered into with Fabrica de Software IFES.
 */

package academico.util.horario.cdp;

import academico.util.persistencia.ObjetoPersistente;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;

/**
 * Esta classe descrevem os horários de inicio, fim e o dia da semana que uma turma tem aula
 *
 * @author Gabriel Quézid
 * @version 0.1
 * @see
 */
@Entity
public class Horario extends ObjetoPersistente{
    private Calendar horarioInicio;
    private Calendar horarioFim;
    private DiaSemana dia;
    
    /**
     * Obtém o dia de Horario
     */
    @Enumerated(EnumType.STRING)
    public DiaSemana getDia() {
        return dia;
    }

    /**
     * Altera o valor de dia em Horario
     */
    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    /**
     * Obtém o horário final (horarioFinal) de Horario
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getHorarioFim() {
        return horarioFim;
    }

    /**
     * Altera o horário final (horárioFinal) de Horario 
     */
    public void setHorarioFim(Calendar horarioFim) {
        this.horarioFim = horarioFim;
    }

    /**
     * Obtém o horário de inicio (horarioInicio) de Horário
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getHorarioInicio() {
        return horarioInicio;
    }

    /**
     * Altera o horário de inicio (horarioInicio) de Horario
     */
    public void setHorarioInicio(Calendar horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    
}

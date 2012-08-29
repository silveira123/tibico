/*
 * Aluno.java 
 * Versão: 0.1 
 * Data de Criação : 05/06/2012, 16:18:24
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
package academico.controleinterno.cdp;

import academico.util.persistencia.ObjetoPersistente;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.*;

/**
 * Esta classe descreve as informações de um aluno, a classe possui a associação com um Curso e faz herança com Pessoa, do pacote de utilitários, contemplando informações necessárias para o registro
 * do aluno.
 * <p/>
 * @author Gabriel Quézid
 * @version 0.1
 * @see academico.controleinterno.cdp.Aluno
 */
@Entity
public class Calendario extends ObjetoPersistente {

    private String identificador;
    private String duracao;
    private Calendar dataInicioCA;
    private Calendar dataFimCA;
    private Calendar dataInicioPL;
    private Calendar dataFimPL;
    private Calendar dataInicioPM;
    private Calendar dataFimPM;
    private Curso curso;
    private int sequencial;
    private SituacaoCalendario situacao;

    public Calendario() {
        situacao = SituacaoCalendario.ABERTO;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDataFimCA() {
        return dataFimCA;
    }

    public void setDataFimCA(Calendar dataFimCA) {
        this.dataFimCA = dataFimCA;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDataFimPL() {
        return dataFimPL;
    }

    public void setDataFimPL(Calendar dataFimPL) {
        this.dataFimPL = dataFimPL;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDataFimPM() {
        return dataFimPM;
    }

    public void setDataFimPM(Calendar dataFimPM) {
        this.dataFimPM = dataFimPM;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDataInicioCA() {
        return dataInicioCA;
    }

    public void setDataInicioCA(Calendar dataInicioCA) {
        this.dataInicioCA = dataInicioCA;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDataInicioPL() {
        return dataInicioPL;
    }

    public void setDataInicioPL(Calendar dataInicioPL) {
        this.dataInicioPL = dataInicioPL;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDataInicioPM() {
        return dataInicioPM;
    }

    public void setDataInicioPM(Calendar dataInicioPM) {
        this.dataInicioPM = dataInicioPM;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getSequencial() {
        return sequencial;
    }

    public void setSequencial(int sequencial) {
        this.sequencial = sequencial;
    }

    @Enumerated(EnumType.STRING)
    public SituacaoCalendario getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoCalendario situacao) {
        this.situacao = situacao;
    }

    public boolean ehAtual() {
        if (Calendar.getInstance().after(dataInicioCA) && Calendar.getInstance().before(dataFimCA)) {
            return true;
        }
        return false;
    }

    public static boolean estaEntre(Calendar inicio, Calendar fim) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ini = sdf.format(inicio.getTime());
        String f = sdf.format(fim.getTime());
        String hoje = sdf.format(Calendar.getInstance().getTime());
        
        if((inicio.before(Calendar.getInstance()) || ini.equals(hoje)) && (fim.after(Calendar.getInstance()) || f.equals(hoje)))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return this.identificador;
    }
}
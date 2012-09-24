/*
 * PagRegistroChamada.java 
 * Versão: 0.1 
 * Data de Criação : 03/07/2012, 13:40:23
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
package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.Aula;
import academico.controlepauta.cdp.Frequencia;
import academico.controlepauta.cdp.MatriculaTurma;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os dados do registro, abrangendo a leitura e interpretação para a tela
 * PagRegistroChamada.zul
 *
 * @author Pietro Crhist
 * @author Geann Valfré
 */
public class PagRegistroChamada extends GenericForwardComposer {

    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private Window winRegistroChamada;
    private Textbox nomeTurma, conteudo;
    private Datebox data;
    private Intbox qtdAulas;
    private Listbox listbox;
    private Button salvar;
    private Button fechar;
    private Aula obj;
    private Turma obj2;
    private List<Intbox> faltas = new ArrayList<Intbox>();
    private List<MatriculaTurma> matriculaturmas;
    private List<Frequencia> frequencias;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj2 = (Turma) arg.get("obj2");
        if (obj2 != null) {
            nomeTurma.setValue(obj2.toString());
            nomeTurma.setDisabled(true);

            String before = pegarDatas(obj2.getCalendario().getDataInicioPL().getTime());
            String after = pegarDatas(obj2.getCalendario().getDataFimPL().getTime());
            data.setConstraint("between " + before + " and " + after);
            matriculaturmas = ctrlMatricula.obter(obj2);//recebe as matriculas da turma

            for (int i = 0; i < matriculaturmas.size(); i++) {//percorre a lista das matriculas para adicionar na tela
                //cria uma linha de matricula, onde o primeiro elemento dessa linha será uma matricula
                Listitem linha = new Listitem(matriculaturmas.get(i).getAluno().getMatricula(), matriculaturmas);
                //proximo campo é nome do aluno
                linha.appendChild(new Listcell(matriculaturmas.get(i).getAluno().toString()));
                //lista frequencias recebe as frequencias de um determinado aluno em uma turma
                frequencias = ctrl.obterFrequencias(matriculaturmas.get(i).getAluno(), obj2);

                Integer totalFaltas = 0;
                //vai pegar todas as falas do aluno e ir somando
                for (int j = 0; j < frequencias.size(); j++) {
                    totalFaltas += frequencias.get(j).getNumFaltasAula();
                }
                //adiciona o numero de faltas do aluno
                linha.appendChild(new Listcell(totalFaltas.toString()));
                //cria um campo intbox para ser preenchido com o numero de faltas naquela aula
                Listcell listcell = new Listcell();
                Intbox t1 = new Intbox();
                t1.setValue(0);
                t1.setWidth("40%");
                faltas.add(t1);
                t1.setParent(listcell);
                linha.appendChild(listcell);

                linha.setParent(listbox);

            }
        }
    }

    public void onCreate$winRegistroChamada() {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winRegistroChamada.detach();
        }
        else {
            MODO = (Integer) arg.get("tipo");

            if (MODO != CtrlAula.SALVAR) {
                obj = (Aula) arg.get("obj");

                preencherTela();
                if (MODO == CtrlAula.CONSULTAR) {
                    this.salvar.setVisible(false);
                    bloquearTela();
                }
            }
        }
    }

    public void onClick$salvar(Event event) {

        String msg = valido();
        if (msg.trim().equals("")) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            if (MODO == CtrlAula.SALVAR) {
                ArrayList<Object> args = new ArrayList<Object>();
                cal.setTime(data.getValue());
                args.add(cal);
                args.add(qtdAulas.getValue());
                args.add(conteudo.getValue());
                args.add(obj2);

                frequencias = new ArrayList<Frequencia>();
                for (int i = 0; i < faltas.size(); i++) {
                    Frequencia f = new Frequencia();
                    f.setNumFaltasAula(faltas.get(i).getValue());
                    f.setMatriculaTurma(matriculaturmas.get(i));
                    frequencias.add(f);
                }
                boolean a = ctrl.validarFaltas((Integer) args.get(1), frequencias);
                if (a == true) {
                    args.add(frequencias);
                    ctrl.incluirAula(args);
                    winRegistroChamada.onClose();
                }
                else {
                    Messagebox.show("Numero de faltas irregular!");
                }


            }
            else {
                List<Frequencia> frequencia = new ArrayList<Frequencia>();
                List<Frequencia> frequenciaValidar = new ArrayList<Frequencia>();
                for (int i = 0; i < obj.getFrequencia().size(); i++) {
                    Frequencia t = null;
                    try {
                        t = (Frequencia) ((Frequencia) obj.getFrequencia().get(i)).clone();
                    }
                    catch (CloneNotSupportedException ex) {
                        System.err.println(ex);
                    }
                    frequencia.add(t);
                    try {
                        frequenciaValidar.add((Frequencia) t.clone());
                    }
                    catch (CloneNotSupportedException ex) {
                        Logger.getLogger(PagRegistroChamada.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                inserirFrequencias(frequenciaValidar);

                boolean a = ctrl.validarFaltas(qtdAulas.getValue(), frequenciaValidar);
                if (a == true) {
                    cal.setTime(data.getValue());
                    obj.setDia(cal);
                    obj.setTurma(obj2);
                    obj.setQuantidade(qtdAulas.getValue());
                    obj.setConteudo(conteudo.getValue());

                    inserirFrequencias(obj.getFrequencia());
                    ctrl.alterarAula(obj, frequencia);

                    winRegistroChamada.onClose();
                }
                else {
                    Messagebox.show("Numero de faltas irregular!");
                }
            }
        }
        else {
            Messagebox.show(msg, "Infome:", 0, Messagebox.EXCLAMATION);
        }

    }

    public void inserirFrequencias(List<Frequencia> frequencia) {
        for (int i = 0; i < faltas.size(); i++) {
            if (i < frequencia.size() && frequencia.get(i).getNumFaltasAula() == faltas.get(i).getValue()) {
                continue;
            }
            else if (i < frequencia.size()) {
                frequencia.get(i).setNumFaltasAula(faltas.get(i).getValue());
            }
            else {
                Frequencia f = new Frequencia();
                f.setNumFaltasAula(faltas.get(i).getValue());
                f.setMatriculaTurma(matriculaturmas.get(i));
                frequencia.add(f);
            }
        }
    }

    public void onClick$voltar(Event event) {
        winRegistroChamada.onClose();
    }

    public void bloquearTela() {
        nomeTurma.setDisabled(true);
        conteudo.setDisabled(true);
        data.setDisabled(true);
        qtdAulas.setDisabled(true);
        listbox.setDisabled(true);

        for (int i = 0; i < faltas.size(); i++) {
            faltas.get(i).setDisabled(true);
        }
    }

    private void preencherTela() {
        data.setValue(obj.getDia().getTime());
        qtdAulas.setValue(obj.getQuantidade());
        conteudo.setValue(obj.getConteudo());

        for (int i = 0; i < matriculaturmas.size(); i++) {
            for (int j = 0; j < obj.getFrequencia().size(); j++) {
                if (matriculaturmas.get(i).getAluno().equals(obj.getFrequencia().get(j).getMatriculaTurma().getAluno())) {
                    faltas.get(i).setValue(obj.getFrequencia().get(j).getNumFaltasAula());
                }
            }


        }
    }

    public String pegarDatas(Date data) {
        List<String> lista = new ArrayList<String>();
        Date datas = data;
        Integer ano, mes, dia;
        ano = datas.getYear() + 1900;
        mes = datas.getMonth() + 1;
        dia = datas.getDate();
        lista.add(ano.toString());
        if (mes < 10) {
            lista.add("0" + mes.toString());
        }
        else {
            lista.add(mes.toString());
        }
        if (dia < 10) {
            lista.add("0" + dia.toString());
        }
        else {
            lista.add(dia.toString());
        }
        String resultado = lista.get(0) + lista.get(1) + lista.get(2);
        return resultado;
    }

    private String valido() {
        String msg = "";

        if (data.getValue() == null) {
            msg += "- Data\n";
        }
        if (qtdAulas.getText().trim().equals("")) {
            msg += "- Quantidade de Aulas\n";
        }
        if (conteudo.getText().trim().equals("")) {
            msg += "- Conteudo\n";
        }

        for (int i = 0; i < faltas.size(); i++) {
            if (faltas.get(i).getValue() == null) {
                msg += "- Todos os campos Faltas\n";
                break;
            }
        }

        return msg;
    }

}

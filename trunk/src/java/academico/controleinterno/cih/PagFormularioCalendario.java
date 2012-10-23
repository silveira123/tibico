/*
 * PagFormularioCalendario.java 
 * Versão: 0.1 
 * Data de Criação : 22/06/2012
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
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os dados formulário, abrangendo a leitura e interpretação para a tela
 * PagFormularioCalendario.zul
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 */
public class PagFormularioCalendario extends GenericForwardComposer {

    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private CtrlCurso ctrlCurso = CtrlCurso.getInstance();
    private Window winFormularioCalendario;
    private Textbox identificador, duracao;
    private Datebox dataInicioCA, dataFimCA, dataInicioPL, dataFimPL, dataInicioPM, dataFimPM;
    private Combobox curso;
    private Calendario obj;
    private Button salvar, voltar;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Curso> listaCurso = ctrlCurso.obterCursos();
        curso.setModel(new ListModelList(listaCurso, true));
        curso.setReadonly(true);
        curso.setDisabled(true);

    }

    public void onChange$dataInicioCA(Event event) {
        Date obj2 = dataInicioCA.getValue();
        String before = pegarDatas(obj2);
        dataFimCA.setConstraint("after " + before);
        dataInicioPL.setConstraint("after " + before);
        dataFimPL.setConstraint("after " + before);
        dataInicioPM.setConstraint("after " + before);
        dataFimPM.setConstraint("after " + before);
    }

    public void onChange$dataFimCA(Event event) {
        Date obj2 = dataInicioCA.getValue();
        Date obj3 = dataFimCA.getValue();
        String before = pegarDatas(obj2);
        String after = pegarDatas(obj3);
        dataInicioPM.setConstraint("between " + before + " and " + after);
        dataFimPM.setConstraint("between " + before + " and " + after);
        dataInicioPL.setConstraint("between " + before + " and " + after);
        dataFimPL.setConstraint("between " + before + " and " + after);
    }

    public void onCreate$winFormularioCalendario() {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioCalendario.detach();
        }
        else {

            MODO = (Integer) arg.get("tipo");

            if (MODO != ctrl.SALVAR) {
                obj = (Calendario) arg.get("obj");
                preencherTela();
                if (MODO == ctrl.CONSULTAR) {
                    this.salvar.setVisible(false);
                    bloquearTela();
                }
            }
            else {
                Curso obj2 = (Curso) arg.get("obj");
                List<Comboitem> cursos = curso.getItems();
                for (int i = 0; i < cursos.size(); i++) {
                    // verificando qual a area de conhecimento cadastrado
                    if (obj2.equals(cursos.get(i).getValue())) {
                        curso.setSelectedItem(cursos.get(i));
                    }
                }
            }
        }
    }

    private void preencherTela() {
        List<Comboitem> a = curso.getItems();
        for (int i = 0; i < a.size(); i++) {
            // verificando qual a area de conhecimento cadastrado
            if (a.get(i).getValue() == obj.getCurso()) {
                curso.setSelectedItem(a.get(i));
            }
        }

        identificador.setValue(obj.getIdentificador());
        duracao.setValue(obj.getDuracao());
        dataInicioCA.setValue(obj.getDataInicioCA().getTime());
        dataFimCA.setValue(obj.getDataFimCA().getTime());
        dataInicioPL.setValue(obj.getDataInicioPL().getTime());
        dataFimPL.setValue(obj.getDataFimPL().getTime());
        dataInicioPM.setValue(obj.getDataInicioPM().getTime());
        dataFimPM.setValue(obj.getDataFimPM().getTime());
    }

    private void bloquearTela() {
        curso.setDisabled(true);
        identificador.setDisabled(true);
        duracao.setDisabled(true);
        dataInicioCA.setDisabled(true);
        dataFimCA.setDisabled(true);
        dataInicioPL.setDisabled(true);
        dataFimPL.setDisabled(true);
        dataInicioPM.setDisabled(true);
        dataFimPM.setDisabled(true);
    }

    public void onClick$salvar(Event event) {
        Calendar inicioCA = Calendar.getInstance();
        Calendar fimCA = Calendar.getInstance();
        Calendar inicioPL = Calendar.getInstance();
        Calendar fimPL = Calendar.getInstance();
        Calendar inicioPM = Calendar.getInstance();
        Calendar fimPM = Calendar.getInstance();

        inicioCA.setTime(dataInicioCA.getValue());
        fimCA.setTime(dataFimCA.getValue());
        inicioPL.setTime(dataInicioPL.getValue());
        fimPL.setTime(dataFimPL.getValue());
        inicioPM.setTime(dataInicioPM.getValue());
        fimPM.setTime(dataFimPM.getValue());

        String msg = valido();

        int result = validar(inicioCA, fimCA, inicioPL, fimPL, inicioPM, fimPM);
        msg += imprimeValidacao(result);

        if (msg.trim().equals("") && result >= 8) {
            if (MODO == ctrl.SALVAR) {
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(curso.getSelectedItem().getValue());
                args.add(identificador.getValue());
                args.add(duracao.getValue());
                args.add(inicioCA);
                args.add(fimCA);
                args.add(inicioPL);
                args.add(fimPL);
                args.add(inicioPM);
                args.add(fimPM);

                ctrl.incluirCalendario(args);
            }
            else {
                obj.setCurso((Curso) curso.getSelectedItem().getValue());
                obj.setIdentificador(identificador.getValue());
                obj.setDuracao(duracao.getValue());

                obj.setDataInicioCA(inicioCA);
                obj.setDataFimCA(fimCA);

                obj.setDataInicioPL(inicioPL);
                obj.setDataFimPL(fimPL);

                obj.setDataInicioPM(inicioPM);
                obj.setDataFimPM(fimPM);

                ctrl.alterarCalendario(obj);
            }
            winFormularioCalendario.onClose();
        }
        else {
            Messagebox.show(msg, "Informe:", 0, Messagebox.EXCLAMATION);
        }
    }

    public void onClick$voltar(Event event) {
        winFormularioCalendario.onClose();
    }

    public void limparCampos() {
        curso.setSelectedItem(null);
        identificador.setValue("");
        duracao.setValue("");
        dataInicioCA.setValue(null);
        dataFimCA.setValue(null);
        dataInicioPL.setValue(null);
        dataFimPL.setValue(null);
        dataInicioPM.setValue(null);
        dataFimPM.setValue(null);
    }

    public int validar(Calendar inicioCA, Calendar fimCA, Calendar inicioPL, Calendar fimPL, Calendar inicioPM, Calendar fimPM) {
        if (inicioCA.after(fimCA)) {
            return 1; //Caso o InicioCA seja depois do fim CA ele retorna
        }
        else if (inicioPL.after(fimPL)) {
            return 2;//Caso o inicioPL seja depois do fimPL ele retorna
        }
        else if (inicioPL.before(inicioCA)) {
            return 3;//Caso o inicioPL seja antes do inicioCA ele retorna
        }
        else if (fimPL.after(fimCA)) {
            return 4;//Caso o fimPL seja depois do fimCA ele retorna
        }
        else if (inicioPM.after(fimPM)) {
            return 5;//Caso o inicioPM seja depois do fimPM ele retorna
        }
        else if (inicioPM.before(inicioCA)) {
            return 6;//Caso o inicioPM seja antes do inicioCA ele retorna
        }
        else if (fimPM.after(fimCA)) {
            return 7;//Caso o fimPM seja depois do fimCA ele retorna
        }
        else {
            return 8;
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

    public String imprimeValidacao(int result) {
        if (result == 1) {
            return "- Inicio do Calendário Acadêmico não pode ser depois do FIM\n";
        }
        else if (result == 2) {
            return "- Inicio do Período Letivo não pode ser depois do FIM\n";
        }
        else if (result == 3) {
            return "- Inicio do Período Letivo não pode ser antes do Inicio do Calendário Acadêmico\n";
        }
        else if (result == 4) {
            return "- Fim do Período Letivo não pode ser depois do Fim do Calendário Acadêmico\n";
        }
        else if (result == 5) {
            return "- Inicio do Período Matrícula não pode ser depois do FIM\n";
        }
        else if (result == 6) {
            return "- Inicio do Período Matrícula não pode ser antes do Inicio do Calendário Acadêmico\n";
        }
        else if (result == 7) {
            return "- Fim do Período Matrícula não pode ser antes do Fim do Calendário Acadêmico\n";
        }

        return "";
    }

    private String valido() {
        String msg = "";

        if (curso.getSelectedItem() == null) {
            msg += "- Curso\n";
        }
        if (identificador.getText().trim().equals("")) {
            msg += "- Identificador\n";
        }
        if (duracao.getText().trim().equals("")) {
            msg += "- Duração\n";
        }

        return msg;
    }

}

/*
 * PagFormularioDisciplina.java 
 * Versão: 0.1 
 * Data de Criação : 22/06/2012, 10:33:15
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
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.Exceptions.AcademicoException;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os dados formulário, abrangendo a leitura e interpretação para a tela
 * PagFormularioDisciplina.zul
 * <p/>
 * @author Geann Valfré
 * @author Eduardo Rigamonte
 */
public class PagFormularioDisciplina extends GenericForwardComposer {

    private CtrlCurso ctrl = CtrlCurso.getInstance();
    private Window winFormularioDisciplina;
    private Textbox nomeDisciplina;
    private Intbox cargaHoraria, creditos, periodo;
    private Combobox cursoCombo;
    private Listbox listPreRequisitos, listAreaConhecimento;
    private Disciplina obj;
    private Curso obj2;
    private Button salvarDisciplina;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);


        cursoCombo.setDisabled(true);



        // Populando o list de AreaConhecimento
        List<AreaConhecimento> areaConhecimentos = ctrl.obterAreaConhecimento();
        if (areaConhecimentos != null) {
            listAreaConhecimento.setModel(new ListModelList(areaConhecimentos, true));
        }
        ((ListModelList) listAreaConhecimento.getModel()).setMultiple(true);
    }

    public void onCreate$winFormularioDisciplina() throws AcademicoException {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioDisciplina.detach();
        }
        else {
            MODO = (Integer) arg.get("tipo");

            if (MODO != CtrlCurso.SALVAR) {
                obj = (Disciplina) arg.get("obj");
                preencherTela();
                if (MODO == CtrlCurso.CONSULTAR) {
                    this.salvarDisciplina.setVisible(false);
                    bloquearTela();
                }
            }
            else {
                obj2 = (Curso) arg.get("obj");
                List<Comboitem> curso = cursoCombo.getItems();
                for (int i = 0; i < curso.size(); i++) {
                    // verificando qual a area de conhecimento cadastrado
                    if (obj2.equals(curso.get(i).getValue())) {
                        cursoCombo.setSelectedItem(curso.get(i));
                    }
                }
            }
        }
    }

    private void preencherTela() {

        // retornado a lista de areadeconhecimento
        List<Comboitem> curso = cursoCombo.getItems();
        for (int i = 0; i < curso.size(); i++) {
            // verificando qual a area de conhecimento cadastrado
            if (obj.getCurso().equals(curso.get(i).getValue())) {
                cursoCombo.setSelectedItem(curso.get(i));
            }
        }

        nomeDisciplina.setText(obj.getNome());
        cargaHoraria.setValue(obj.getCargaHoraria());
        creditos.setValue(obj.getNumCreditos());
        periodo.setValue(obj.getPeriodoCorrespondente());


        List<Disciplina> disciplinas = ctrl.obterDisciplinas(obj.getCurso());
        if (disciplinas != null) {
            listPreRequisitos.setModel(new ListModelList(disciplinas, true));
        }
        ((ListModelList) listPreRequisitos.getModel()).setMultiple(true);

        if (((ListModelList) listPreRequisitos.getModel()).contains(obj)) {
            ((ListModelList) listPreRequisitos.getModel()).remove(obj);
        }

        if (listAreaConhecimento.getModel() != null) {
            setSelecionadosList(listAreaConhecimento, obj.getAreaConhecimento());
        }
        if (listPreRequisitos.getModel() != null) {
            setSelecionadosList(listPreRequisitos, obj.getPrerequisito());
        }
    }

    public void setSelecionadosList(Listbox listbox, List selects) {
        ListModel model = listbox.getModel();
        ((Selectable) model).setSelection(selects);
    }

    public ArrayList getSelecionadosList(Listbox listbox) {
        ListModel model = listbox.getModel();
        return new ArrayList(((Selectable) model).getSelection());
    }

    private void bloquearTela() {
        nomeDisciplina.setDisabled(true);
        cargaHoraria.setDisabled(true);
        creditos.setDisabled(true);
        periodo.setDisabled(true);

        List<Listitem> listItemsPreRequisito = listPreRequisitos.getItems();
        listPreRequisitos.setCheckmark(false);
        for (int i = 0; i < listItemsPreRequisito.size(); i++) {
            listItemsPreRequisito.get(i).setDisabled(true);
        }

        List<Listitem> listAreaConhecimentos = listAreaConhecimento.getItems();
        listAreaConhecimento.setCheckmark(false);
        for (int i = 0; i < listAreaConhecimentos.size(); i++) {
            listAreaConhecimentos.get(i).setDisabled(true);
        }
        cursoCombo.setDisabled(true);
    }

    public void onBlur$periodo() {
        Comboitem cursoItem = cursoCombo.getSelectedItem();
        if (cursoItem != null) {
            List<Disciplina> disciplinas = new ArrayList<Disciplina>();
            listPreRequisitos.setModel(new ListModelList(disciplinas, true));
            
            disciplinas = ctrl.obterCandidatosPrerequisito((Curso)cursoItem.getValue(), periodo.getValue());
            if (disciplinas != null) {
                listPreRequisitos.setModel(new ListModelList(disciplinas, true));
            }
            ((ListModelList) listPreRequisitos.getModel()).setMultiple(true);
        }
    }

    public void onClick$salvarDisciplina(Event event) {

        Disciplina d = null;
        String msg = valido();
        if (msg.trim().equals("")) {
            if (MODO == CtrlCurso.EDITAR) {

                obj.setNome(nomeDisciplina.getText());
                obj.setCargaHoraria(cargaHoraria.getValue());
                obj.setNumCreditos(creditos.getValue());
                obj.setPeriodoCorrespondente(periodo.getValue());
                Curso c = cursoCombo.getSelectedItem().getValue();
                obj.setCurso(c);
                ArrayList<Disciplina> auxP = getSelecionadosList(listPreRequisitos);
                obj.setPrerequisito(auxP);
                ArrayList<AreaConhecimento> auxAC = getSelecionadosList(listAreaConhecimento);
                obj.setAreaConhecimento(auxAC);
                d = ctrl.alterarDisciplina(obj);

            }
            else {
                ArrayList<Object> list = new ArrayList<Object>();

                list.add(nomeDisciplina.getText());
                list.add(cargaHoraria.getValue());
                list.add(creditos.getValue());
                list.add(periodo.getValue());
                ArrayList<Disciplina> auxP = getSelecionadosList(listPreRequisitos);
                list.add(auxP);
                Curso c = cursoCombo.getSelectedItem().getValue();
                list.add(c);
                ArrayList<AreaConhecimento> auxAC = getSelecionadosList(listAreaConhecimento);
                list.add(auxAC);
                d = ctrl.incluirDisciplina(list);

                limparCampos();
            }
            winFormularioDisciplina.onClose();
        }
        else {
            Messagebox.show(msg, "Informe", 0, Messagebox.EXCLAMATION);
        }
    }

    public void onClick$voltar(Event event) {
        winFormularioDisciplina.onClose();
    }

    public void limparCampos() {
        nomeDisciplina.setText("");
        cargaHoraria.setText("");
        creditos.setText("");
        periodo.setText("");
        listPreRequisitos.clearSelection();
        listAreaConhecimento.clearSelection();
    }

    private String valido() {
        String msg = "";

        if (nomeDisciplina.getText().trim().equals("")) {
            msg += "- Nome\n";
        }
        if (getSelecionadosList(listAreaConhecimento).isEmpty()) {
            msg += "- Área de Conhecimento\n";
        }
        if (cargaHoraria.getValue() == null) {
            msg += "- Carga Horária\n";
        }
        if (creditos.getValue() == null) {
            msg += "- Créditos\n";
        }
        if (periodo.getValue() == null) {
            msg += "- Periodo Correspondente\n";
        }

        return msg;
    }
}

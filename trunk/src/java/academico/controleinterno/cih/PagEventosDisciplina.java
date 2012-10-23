/*
 * PagEventosDisciplina.java 
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
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os eventos da tela PagEventosDisciplina.zul
 * <p/>
 * @author Eduardo Rigamonte
 * @author Geann Valfré
 */
public class PagEventosDisciplina extends GenericForwardComposer {

    private CtrlCurso ctrl = CtrlCurso.getInstance();
    private Window winEventosDisciplina;
    private Listbox listDisciplina;
    private Curso curso;
    private Combobox cursoCombo;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosDisciplina(this);
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);
    }

    public void onCreate$winEventosDisciplina(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winEventosDisciplina.detach();
        }
    }

    public void onSelect$cursoCombo(Event event) throws AcademicoException {
        Curso select = (Curso) cursoCombo.getSelectedItem().getValue();

        //limpando o listbox antes de add as novas linhas
        while (listDisciplina.getItemCount() > 0) {
            listDisciplina.removeItemAt(0);
        }

        List<Disciplina> disciplinas = ctrl.obterDisciplinas(select);
        if (disciplinas != null) {

            for (int i = 0; i < disciplinas.size(); i++) {
                Disciplina c = disciplinas.get(i);
                Listitem linha = new Listitem(disciplinas.get(i).toString(), c);

                linha.appendChild(new Listcell(disciplinas.get(i).getCurso().toString()));
                linha.appendChild(new Listcell(disciplinas.get(i).getCargaHoraria() + ""));

                linha.setParent(listDisciplina);
            }
        }
    }

    public void addDisciplina(Disciplina c) {
        Listitem linha = new Listitem(c.toString(), c);
        linha.appendChild(new Listcell(c.getCurso().toString()));
        linha.appendChild(new Listcell(c.getCargaHoraria() + ""));
        linha.setParent(listDisciplina);
    }

    public void refreshDisciplina(Disciplina c) {
        for (int i = 0; i < listDisciplina.getItemCount(); i++) {
            if (listDisciplina.getItemAtIndex(i).getValue() == c) {
                listDisciplina.getItemAtIndex(i).getChildren().clear();
                listDisciplina.getItemAtIndex(i).appendChild(new Listcell(c.toString()));
                listDisciplina.getItemAtIndex(i).appendChild(new Listcell(c.getCurso().toString()));
                listDisciplina.getItemAtIndex(i).appendChild(new Listcell(c.getCargaHoraria() + ""));
                break;
            }
        }
    }

    public void onClick$excluirDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            try {
                Disciplina d = listitem.getValue();
                if (ctrl.apagarDisciplina(d)) {
                    listDisciplina.removeItemAt(listDisciplina.getSelectedIndex());
                    setMensagemAviso("success", "Disciplina excluida com sucesso");
                }
                else {
                    setMensagemAviso("error", "Não foi possivel excluir a disciplina");
                }
            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir a disciplina");
            }
        }
        else {
            setMensagemAviso("info", "Selecione uma disciplina");
        }
    }

    public void onClick$incluirDisciplina(Event event) {
        Comboitem comboitem = cursoCombo.getSelectedItem();
        if (comboitem != null) {
            Curso c = comboitem.getValue();
            ctrl.abrirIncluirDisciplina(c);
        }
    }

    public void onClick$alterarDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            Disciplina d = listitem.getValue();
            ctrl.abrirEditarDisciplina(d);
        }
    }

    public void onClick$consultarDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            Disciplina d = listitem.getValue();
            ctrl.abrirConsultarDisciplina(d);
        }
    }

    public void setMensagemAviso(String tipo, String mensagem) {
        boxInformacao.setClass(tipo);
        boxInformacao.setVisible(true);
        msg.setValue(mensagem);
    }

    public void onClick$boxInformacao(Event event) {
        boxInformacao.setVisible(false);
    }
}
/*
 * PagEventosTurma.java 
 * Versão: 0.1 
 * Data de Criação : 28/06/2012
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

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Turma;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os eventos da tela PagEventosTurma.zul
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 * @author Gabriel Quézid
 */
public class PagEventosTurma extends GenericForwardComposer {

    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winFormularioTurma;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listbox;
    private int tipo;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosTurma(this);
        tipo = (Integer) arg.get("class");

        List<Turma> listaTurma = ctrl.obterTurma();
        if (tipo == 1) {
            for (int i = 0; i < listaTurma.size(); i++) {
                Turma t = listaTurma.get(i);
                Listitem linha = new Listitem(t.getDisciplina().getCurso().toString(), t);
                linha.appendChild(new Listcell(t.getDisciplina().toString()));
                linha.appendChild(new Listcell(t.getCalendario().toString()));
                if (t.getProfessor() != null) {
                    linha.appendChild(new Listcell(t.getProfessor().toString()));
                }
                linha.setParent(listbox);
            }
        }
        else {
            for (int i = 0; i < listaTurma.size(); i++) {
                Turma t = listaTurma.get(i);

                if (t.getProfessor() == null) {
                    Listitem linha = new Listitem(t.getDisciplina().getCurso().toString(), t);
                    linha.appendChild(new Listcell(t.getDisciplina().toString()));
                    linha.appendChild(new Listcell(t.getCalendario().toString()));
                    linha.setParent(listbox);
                }
            }
        }
    }

    public void addTurma(Turma t) {
        Listitem linha = new Listitem(t.getDisciplina().getCurso().toString(), t);
        linha.appendChild(new Listcell(t.getDisciplina().toString()));
        linha.appendChild(new Listcell(t.getCalendario().toString()));
        if (t.getProfessor() != null) {
            linha.appendChild(new Listcell(t.getProfessor().toString()));
        }

        linha.setParent(listbox);
    }

    public void refreshTurma(Turma t) {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if (listbox.getItemAtIndex(i).getValue() == t) {
                listbox.getItemAtIndex(i).getChildren().clear();
                listbox.getItemAtIndex(i).appendChild(new Listcell(t.getDisciplina().getCurso().toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(t.getDisciplina().toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(t.getCalendario().toString()));
                if (t.getProfessor() != null) {
                    listbox.getItemAtIndex(i).appendChild(new Listcell(t.getProfessor().toString()));
                }
                break;
            }
        }
    }

    public void onClick$excluir(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Turma t = listitem.getValue();
                ctrl.apagarTurma(t);
                listbox.removeItemAt(listbox.getSelectedIndex());
                setMensagemAviso("success", "Turma excluida com sucesso");
            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir a turma");
            }
        }
        else {
            setMensagemAviso("info", "Selecione uma turma");
        }
    }

    public void onClick$incluir(Event event) {
        ctrl.abrirIncluirTurma();
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Turma t = listitem.getValue();
            ctrl.abrirEditarTurma(t, tipo);
        }
    }

    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Turma t = listitem.getValue();
            ctrl.abrirConsultarTurma(t);
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
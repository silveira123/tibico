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
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
    private Window winEventosTurma;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listbox;
    private Integer tipo;
    private Div boxInformacao;
    private Label msg;
    private Textbox pesquisarNome;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosTurma(this);
        tipo = (Integer) arg.get("class");
        carregarTurma();
    }

    public void carregarTurma() {

        if (tipo != null) {

            while (listbox.getItemCount() > 0) {
                listbox.removeItemAt(0);
            }

            List<Turma> listaTurma = null;
            try {
                listaTurma = ctrl.obterTurma();
            }
            catch (AcademicoException ex) {
                Logger.getLogger(PagEventosTurma.class.getName()).log(Level.SEVERE, null, ex);
            }

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
    }

    public void onCreate$winEventosTurma(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winEventosTurma.detach();
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
                if (ctrl.apagarTurma(t)) {
                    listbox.removeItemAt(listbox.getSelectedIndex());
                    setMensagemAviso("success", "Turma excluida com sucesso");
                } else setMensagemAviso("error", "Não foi possivel excluir a turma, já possui alunos vinculados");
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

    public void onBlur$pesquisarNome(Event event) {
        if (pesquisarNome.getText().trim().equals("")) {
            carregarTurma();
        }
    }

    public void onChange$pesquisarNome(Event event) {
        if (pesquisarNome.getText().trim().equals("")) {
            carregarTurma();
        }
        else {
            onOK$pesquisarNome(event);
        }
    }

    public void onOK$pesquisarNome(Event event) {
        while (listbox.getItemCount() > 0) {
            listbox.removeItemAt(0);
        }
        System.out.println(pesquisarNome.getText());
        if (!pesquisarNome.getText().trim().equals("")) {
            List<Turma> listaTurmas = null;
            try {
                listaTurmas = ctrl.obterTurmaPesquisa(pesquisarNome.getText());
            }
            catch (AcademicoException ex) {
                Logger.getLogger(PagEventosAluno.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (listaTurmas != null && !listaTurmas.isEmpty()) {
                for (int i = 0; i < listaTurmas.size(); i++) {
                    Turma t = listaTurmas.get(i);
                    Listitem linha = new Listitem(listaTurmas.get(i).getDisciplina().getCurso().toString(), t);

                    linha.appendChild(new Listcell(listaTurmas.get(i).getDisciplina().toString()));

                    linha.appendChild(new Listcell(listaTurmas.get(i).getCalendario().toString()));

                    linha.appendChild(new Listcell(listaTurmas.get(i).getProfessor().toString()));

                    linha.setParent(listbox);
                }
            }
            else {
                setMensagemAviso("info", "Nenhuma turma encontrada!");
            }
        }

    }
}
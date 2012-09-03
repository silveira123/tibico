/*
 * PagEventosAvaliacao.java 
 * Versão: 0.1 
 * Data de Criação : 15/06/2012
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

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cdp.Avaliacao;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para
 * leitura e interpretação de dados. A classe contém os eventos da tela
 * PagEventosAvaliacao.zul
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 * @author Gabriel Quézid
 */
public class PagEventosAvaliacao extends GenericForwardComposer {

    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlLetivo ctrlTurma = CtrlLetivo.getInstance();
    private Window winEventosAvaliacao;
    private Combobox nome;
    private Listbox listbox;
    private Professor obj;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosAvaliacao(this);

        List<Turma> listaTurma = new ArrayList<Turma>();
        obj = (Professor) arg.get("professor");
        if (obj != null) {
            listaTurma = ctrlTurma.obterTurmasAtivas(obj);
        } else {
            listaTurma = ctrlTurma.obterTurmasAtivas();
        }
        nome.setModel(new ListModelList(listaTurma, true));
    }

    public void onCreate$winEventosAvaliacao(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winEventosAvaliacao.detach();
        }
    }

    public void onSelect$nome(Event event) {
        try {
            if (nome.getSelectedItem() != null) {
                Turma t = nome.getSelectedItem().getValue();

                if (t != null) {
                    List<Avaliacao> listaAvaliacao = ctrl.obterAvaliacoes();

                    while (listbox.getItemCount() > 0) {
                        listbox.removeItemAt(0);
                    }

                    for (int i = 0; i < listaAvaliacao.size(); i++) {
                        Avaliacao a = listaAvaliacao.get(i);
                        if (a.getTurma().equals(t)) {
                            Listitem linha = new Listitem(a.toString(), a);
                            linha.appendChild(new Listcell(a.getPeso() + ""));

                            linha.setParent(listbox);
                        }
                    }
                }
            }else {
                while (listbox.getItemCount() > 0) {
                        listbox.removeItemAt(0);
                    }
                setMensagemAviso("info", "Turma não encontrada");
            }
        } catch (AcademicoException ex) {
            Logger.getLogger(PagEventosAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addAvaliacao(Avaliacao a) {
        Listitem linha = new Listitem(a.toString(), a);
        linha.appendChild(new Listcell(a.getPeso() + ""));
        linha.setParent(listbox);
    }

    public void refreshAvaliacao(Avaliacao a) {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if (listbox.getItemAtIndex(i).getValue() == a) {
                listbox.getItemAtIndex(i).getChildren().clear();
                listbox.getItemAtIndex(i).appendChild(new Listcell(a.toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(a.getPeso() + ""));
                break;
            }
        }
    }

    public void onClick$excluir(Event event) {

        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Avaliacao c = listitem.getValue();
                ctrl.apagarAvaliacao(c);
                listbox.removeItemAt(listbox.getSelectedIndex());
                setMensagemAviso("success", "Avaliação excluida com sucesso");
            } catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir a avaliação");
            }
        } else {
            setMensagemAviso("info", "Selecione uma avaliação");
        }
    }

    public void onClick$incluir(Event event) {
        Comboitem comboitem = nome.getSelectedItem();
        if (comboitem != null) {
            Turma t = comboitem.getValue();
            ctrl.abrirIncluirAvaliacao(t);
        } else {
            setMensagemAviso("info", "Selecione uma turma");
        }
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirEditarAvaliacao(c);
        } else {
            setMensagemAviso("info", "Selecione uma avaliação");
        }
    }

    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirConsultarAvaliacao(c);
        } else {
            setMensagemAviso("info", "Selecione uma avaliação");
        }
    }

    public void onClick$inserirPontuacao(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirRegistroPontuacao(c);
        } else {
            setMensagemAviso("info", "Selecione uma avaliação");
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
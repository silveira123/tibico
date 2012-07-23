/*
 * PagEventosProfessor.java 
 * Versão: 0.1 
 * Data de Criação : 25/06/2012, 12:13:11
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

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Professor;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os eventos da tela PagEventosProfessor.zul
 * <p/>
 * @author Gabriel Quézid
 * @author Rodrigo Maia
 */
public class PagEventosProfessor extends GenericForwardComposer {

    private CtrlPessoa ctrl = CtrlPessoa.getInstance();
    private Window winDadosProfessor;
    private Listbox listProfessor;
    //private Listitem itens;
    private ListModelList list; // the model of the listProfessor
    private Professor p;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosProfessor(this);
        List<Professor> listaProfessores = ctrl.obterProfessor();

        if (listaProfessores != null) {
            for (int i = 0; i < listaProfessores.size(); i++) {
                Professor p = listaProfessores.get(i);
                Listitem linha = new Listitem(listaProfessores.get(i).toString(), p);

                linha.appendChild(new Listcell(listaProfessores.get(i).getGrauInstrucao().toString()));

                linha.setParent(listProfessor);
            }
        }
    }

    public void onCreate$winDadosProfessor(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winDadosProfessor.detach();
        }
    }

    public void addProfessor(Professor p) {
        Listitem linha = new Listitem(p.toString(), p);
        linha.appendChild(new Listcell(p.getGrauInstrucao().toString()));
        linha.setParent(listProfessor);
    }

    public void refreshProfessor(Professor p) {
        for (int i = 0; i < listProfessor.getItemCount(); i++) {
            if (listProfessor.getItemAtIndex(i).getValue() == p) {
                listProfessor.getItemAtIndex(i).getChildren().clear();
                listProfessor.getItemAtIndex(i).appendChild(new Listcell(p.toString()));
                listProfessor.getItemAtIndex(i).appendChild(new Listcell(p.getGrauInstrucao().toString()));
                break;
            }
        }
    }

    public void onClick$excluirProfessor(Event event) {
        Listitem listitem = listProfessor.getSelectedItem();
        if (listitem != null) {
            try {
                ctrl.apagarProfessor((Professor) listitem.getValue());
                listProfessor.removeItemAt(listProfessor.getSelectedIndex());
                setMensagemAviso("success", "Professor excluido com sucesso");
            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir o professor");
            }
        }
        else {
            setMensagemAviso("info", "Selecione um professor");
        }

    }

    public void onClick$incluirProfessor(Event event) {
        ctrl.abrirIncluirProfessor(p);
    }

    public void onClick$alterarProfessor(Event event) {
        Listitem listitem = listProfessor.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirEditarProfessor((Professor) listitem.getValue());
        }
    }

    public void onClick$consultarProfessor(Event event) {
        Listitem listitem = listProfessor.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirConsultarProfessor((Professor) listitem.getValue());
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
/*
 * PagEventosCurso.java 
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
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os eventos da tela PagEventosCurso.zul
 * <p/>
 * @author Eduardo Rigamonte
 * @author Geann Valfré
 */
public class PagEventosCurso extends GenericForwardComposer {

    private CtrlCurso ctrl = CtrlCurso.getInstance();
    private Window winEventosCurso;
    private Listbox listCurso;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosCurso(this);
        List<Curso> cursos = ctrl.obterCursos();
        if (cursos != null) {
            for (int i = 0; i < cursos.size(); i++) {
                Curso c = cursos.get(i);
                Listitem linha = new Listitem(cursos.get(i).toString(), c);

                linha.appendChild(new Listcell(cursos.get(i).getGrauInstrucao().toString()));
                linha.appendChild(new Listcell(cursos.get(i).getDuracao() + ""));

                linha.setParent(listCurso);
            }
        }

    }

    public void onCreate$winEventosCurso(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winEventosCurso.detach();
        }
    }

    public void addCurso(Curso c) {
        Listitem linha = new Listitem(c.getNome(), c);
        linha.appendChild(new Listcell(c.getGrauInstrucao().toString()));
        linha.appendChild(new Listcell(c.getDuracao() + ""));
        linha.setParent(listCurso);
    }

    public void refreshCurso(Curso c) {
        for (int i = 0; i < listCurso.getItemCount(); i++) {
            if (listCurso.getItemAtIndex(i).getValue() == c) {
                listCurso.getItemAtIndex(i).getChildren().clear();
                listCurso.getItemAtIndex(i).appendChild(new Listcell(c.getNome()));
                listCurso.getItemAtIndex(i).appendChild(new Listcell(c.getGrauInstrucao().toString()));
                listCurso.getItemAtIndex(i).appendChild(new Listcell(c.getDuracao() + ""));
                break;
            }
        }
    }

    public void onClick$excluirCurso(Event event) {
        Listitem listitem = listCurso.getSelectedItem();
        if (listitem != null) {
            try {
                Curso c = listitem.getValue();
                if (ctrl.apagarCurso(c)) {
                    listCurso.removeItemAt(listCurso.getSelectedIndex());
                    setMensagemAviso("success", "Curso excluido com sucesso");
                } else setMensagemAviso("error", "Não foi possivel excluir o curso, já possui vinculos");
            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir o curso");
            }
        }
        else {
            setMensagemAviso("info", "Selecione um curso");
        }
    }

    public void onClick$incluirCurso(Event event) {
        ctrl.abrirIncluirCurso();
    }

    public void onClick$alterarCurso(Event event) {
        Listitem listitem = listCurso.getSelectedItem();
        if (listitem != null) {
            Curso c = listitem.getValue();
            ctrl.abrirEditarCurso(c);
        }
    }

    public void onClick$consultarCurso(Event event) {
        Listitem listitem = listCurso.getSelectedItem();
        if (listitem != null) {
            Curso c = listitem.getValue();
            ctrl.abrirConsultarCurso(c);
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
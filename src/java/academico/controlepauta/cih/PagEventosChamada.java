/*
 * PagEventosChamada.java 
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
import academico.controlepauta.cdp.Aula;
import academico.controlepauta.cdp.Avaliacao;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados.
 * A classe contém os eventos da tela PagEventosChamada.zul
 * @author 
 */
public class PagEventosChamada extends GenericForwardComposer {

    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlLetivo ctrlTurma = CtrlLetivo.getInstance();
    private Window winEventosAvaliacao;
    private Combobox nome;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Menuitem inserirPontuacao;
    private Listbox listbox;
    private Professor obj;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        ctrl.setPagEventosChamada(this);
        List<Turma> listaTurma = new ArrayList<Turma>();
        obj = (Professor) arg.get("professor");
        if (obj != null) {
            listaTurma = ctrlTurma.obterTurma(obj);
        } else {
            listaTurma = ctrlTurma.obterTurma();
        }

        nome.setModel(new ListModelList(listaTurma, true));
        nome.setReadonly(true);
    }

    public void onSelect$nome(Event event) {
        try {
            Turma t = nome.getSelectedItem().getValue();
            List<Aula> listaAula = ctrl.obterAulas(t);

            while (listbox.getItemCount() > 0) {
                listbox.removeItemAt(0);
            }

            for (int i = 0; i < listaAula.size(); i++) {
                Aula a = listaAula.get(i);
                if (a.getTurma() == t) {
                    Listitem linha = new Listitem(a.getDia().getTime().getDate() + "/" + (a.getDia().getTime().getMonth() + 1)
                            + "/" + (a.getDia().getTime().getYear() + 1900), a);
                    linha.appendChild(new Listcell(a.getConteudo()));
                    linha.appendChild(new Listcell(a.getQuantidade() + ""));

                    linha.setParent(listbox);
                }
            }
        } catch (AcademicoException ex) {
            Logger.getLogger(PagEventosChamada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addChamada(Aula a) {
        
            Listitem linha = new Listitem(a.getDia().getTime().getDate() + "/" + (a.getDia().getTime().getMonth() + 1)
                    + "/" + (a.getDia().getTime().getYear() + 1900), a);
            linha.appendChild(new Listcell(a.getConteudo()));
            linha.appendChild(new Listcell(a.getQuantidade() + ""));
            linha.setParent(listbox);
        
    }

    public void refreshChamada(Aula a) {
        
            for (int i = 0; i < listbox.getItemCount(); i++) {
                if (listbox.getItemAtIndex(i).getValue() == a) {
                    listbox.getItemAtIndex(i).getChildren().clear();
                    listbox.getItemAtIndex(i).appendChild(new Listcell(a.getDia().getTime().getDate() + "/" + (a.getDia().getTime().getMonth() + 1)
                            + "/" + (a.getDia().getTime().getYear() + 1900)));
                    listbox.getItemAtIndex(i).appendChild(new Listcell(a.getConteudo()));
                    listbox.getItemAtIndex(i).appendChild(new Listcell(a.getQuantidade() + ""));
                    break;
                }
            }
        
    }

    public void onClick$excluir(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Aula c = listitem.getValue();
                ctrl.apagarAula(c);

                for (int i = 0; i < c.getFrequencia().size(); i++) {
                    ctrl.apagarFrequencia(c.getFrequencia().get(i));
                }

                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                Messagebox.show("Não foi possivel excluir a Aula");
            }
        } else {
            Messagebox.show("Selecione uma Aula");
        }
    }

    public void onClick$incluir(Event event) {
        Comboitem comboitem = nome.getSelectedItem();
        if (comboitem != null) {
            Turma t = comboitem.getValue();
            ctrl.abrirIncluirAula(t);
        }
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        Comboitem comboitem = nome.getSelectedItem();

        if (listitem != null && comboitem != null) {
            Aula c = listitem.getValue();
            Turma t = comboitem.getValue();
            ctrl.abrirEditarAula(c, t);
        }
    }

    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        Comboitem comboitem = nome.getSelectedItem();

        if (listitem != null && comboitem != null) {
            Aula c = listitem.getValue();
            Turma t = comboitem.getValue();
            ctrl.abrirConsultarAula(c, t);
        }
    }

    public void onClick$inserirPontuacao(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirRegistroPontuacao(c);
        }
    }
}
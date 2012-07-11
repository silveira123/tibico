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

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados.
 * A classe contém os eventos da tela PagEventosCalendario.zul
 * @author Pietro Crhist
 * @author Geann Valfré
 */
public class PagEventosCalendario extends GenericForwardComposer {
    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winEventosCalendario;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listbox;
    private Combobox cursoCombo;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosCalendario(this);
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);
    }
    
    public void onSelect$cursoCombo(Event event) throws AcademicoException
    {
        Curso select = (Curso) cursoCombo.getSelectedItem().getValue();
        List<Calendario> listaCalendario = ctrl.obterCalendarios(select); 
        for (int i = 0; i < listaCalendario.size(); i++) {
            Calendario c = listaCalendario.get(i);
            Listitem linha = new Listitem(c.toString(), c);
                
            linha.appendChild(new Listcell(c.getDataInicioCA().getTime().getDate() + "/" +
                    (c.getDataInicioCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));
            linha.appendChild(new Listcell(c.getDataFimCA().getTime().getDate() + "/" +
                    (c.getDataFimCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));

            linha.setParent(listbox);
        }
    
    }
    public void addCalendario(Calendario c)
    {
        Listitem linha = new Listitem(c.toString(), c);
        linha.appendChild(new Listcell(c.getDataInicioCA().getTime().getDate() + "/" +
                    (c.getDataInicioCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));
        linha.appendChild(new Listcell(c.getDataFimCA().getTime().getDate() + "/" +
                    (c.getDataFimCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));
        linha.setParent(listbox);
    }
    
    public void refreshCalendario(Calendario c)
    {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if(listbox.getItemAtIndex(i).getValue() == c)
            {
                listbox.getItemAtIndex(i).getChildren().clear();      
                listbox.getItemAtIndex(i).appendChild(new Listcell(c.toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(c.getDataInicioCA().getTime().getDate() + "/" +
                            (c.getDataInicioCA().getTime().getMonth() + 1) + "/" + 
                            (c.getDataFimCA().getTime().getYear() + 1900)));
                listbox.getItemAtIndex(i).appendChild(new Listcell(c.getDataFimCA().getTime().getDate() + "/" +
                            (c.getDataFimCA().getTime().getMonth() + 1) + "/" + 
                            (c.getDataFimCA().getTime().getYear() + 1900)));
                break;
            }
        }
    }
    public void onClick$excluir(Event event) { 
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Calendario c = listitem.getValue();
                ctrl.apagarCalendario(c);
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                Messagebox.show("Não foi possivel excluir o calendario");
            }
        } else {
            Messagebox.show("Selecione um calendario");
        }
    }

    public void onClick$incluir(Event event) {
        Comboitem item = cursoCombo.getSelectedItem();
        if(item!=null){
            Curso c = item.getValue();
            ctrl.abrirIncluirCalendario(c);
        }
        
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Calendario c = listitem.getValue();
            ctrl.abrirEditarCalendario(c);
        }
    }
    
    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Calendario c = listitem.getValue();
            ctrl.abrirConsultarCalendario(c);
        }
    }
}
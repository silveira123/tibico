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

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import java.util.List;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados.
 * A classe contém os eventos da tela PagEventosCurso.zul
 * @author Eduardo Rigamonte
 * @author Geann Valfré
 */
public class PagEventosCurso extends GenericForwardComposer {

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
    private Window winEventosCurso;
    private Listbox listCurso;
   
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

    public void addCurso(Curso c)
    {
        Listitem linha = new Listitem(c.getNome(), c);
        linha.appendChild(new Listcell(c.getGrauInstrucao().toString()));
        linha.appendChild(new Listcell(c.getDuracao() + ""));
        linha.setParent(listCurso);
    }
    
    public void refreshCurso(Curso c)
    {
        for (int i = 0; i < listCurso.getItemCount(); i++) {
            if(listCurso.getItemAtIndex(i).getValue() == c)
            {
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
                ctrl.apagarCurso(c);
                listCurso.removeItemAt(listCurso.getSelectedIndex());
            }
            catch (Exception e) {
                Messagebox.show("Não foi possivel excluir o curso");
            }
        }
        else {
            Messagebox.show("Selecione um curso");
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
    
    
}
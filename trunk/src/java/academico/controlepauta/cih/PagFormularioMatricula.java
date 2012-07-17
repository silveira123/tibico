/*
 * PagFormularioMatricula.java 
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
package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os dados formulário, abrangendo a leitura e interpretação para a tela
 * PagFormularioMatricula.zul
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 */
public class PagFormularioMatricula extends GenericForwardComposer {

    private CtrlLetivo ctrlLetivo = CtrlLetivo.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private Window winFormularioMatricula;
    private Listbox left;
    private Listbox right;
    private Textbox nomeAluno;
    private Aluno obj;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        nomeAluno.setReadonly(true);
    }

    public void onCreate$winFormularioMatricula() {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioMatricula.detach();
        }
        else {
            obj = (Aluno) arg.get("aluno");
            if (obj != null) {
                nomeAluno.setValue(obj.toString());
                List<Turma> turma = ctrlMatricula.obterTurmasPossiveis(obj);
                left.setModel(new ListModelList(turma, true));
                ((ListModelList) left.getModel()).setMultiple(true);
            }
        }
    }

    public void onClick$voltar(Event event) {
        winFormularioMatricula.onClose();
    }

    public void onClick$salvarMatricula(Event event) {
        List<Listitem> listItems = right.getItems();

        for (int i = 0; i < listItems.size(); i++) {
            ArrayList<Object> listMT = new ArrayList<Object>();
            listMT.add(obj);
            Turma t = (Turma) listItems.get(i).getValue();
            listMT.add(t);

            ctrlMatricula.efetuarMatricula(listMT);

        }
        winFormularioMatricula.onClose();
    }

}
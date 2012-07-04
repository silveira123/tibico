/*
 * PagEventosMatricula.java 
 * Versão: 0.1 
 * Data de Criação : 28/06/2012, 09:09:04
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

// imports devem ficar aqui!
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Está classe contem os eventos da tela PagEventosMatricula.zul
 * <p/>
 * @author erigamonte
 * @version 0.1
 * @see
 */
public class PagEventosMatricula extends GenericForwardComposer {

    //Constantes:
    //Variáveis de Classe:
    //Variáveis de Instância:
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private Window winEventosMatricula;
    private Menuitem matricular;
    private Menuitem desmatricular;
    private Listbox listbox;
    private Combobox nomeAluno;
    //Métodos Estáticos:

    //Métodos de Instância:
    /**
     * <<descrição do método>>
     * <p/>
     * @param <<nome do parâmetro>> <<descrição do parâmetro>>
     * @param ...
     * @return <<descrição do retorno>>
     * @throws <<Exception gerada e o motivo>>
     */
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        List<Aluno> alunos = ctrlPessoa.obterAlunos();
        nomeAluno.setModel(new ListModelList(alunos, true));
        nomeAluno.setReadonly(true);
    }

    public void onSelect$nomeAluno(Event event) {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            listbox.removeItemAt(0);
        }
        Aluno select = (Aluno) nomeAluno.getSelectedItem().getValue();
        listbox.renderAll();
        try {
            List<MatriculaTurma> matTurma = ctrlMatricula.obterMatriculadas(select);

            for (int i = 0; i < matTurma.size(); i++) {
                MatriculaTurma c = matTurma.get(i);
                Listitem linha = new Listitem(matTurma.get(i).toString(), c);

                linha.appendChild(new Listcell(matTurma.get(i).getTurma().getDisciplina().getPeriodoCorrespondente().toString()));
                linha.appendChild(new Listcell(matTurma.get(i).getTurma().getProfessor() + ""));

                linha.setParent(listbox);
            }
        }
        catch (AcademicoException ex) {
            Messagebox.show("Erro ao obter matriculas");
        }
    }

    public void onClick$matricular(Event event) {
        Comboitem comboitem = nomeAluno.getSelectedItem();
        if (comboitem != null) {
            Aluno a = comboitem.getValue();
            ctrlMatricula.abrirMatricular(a);
        }
    }

    public void onClick$desmatricular(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                MatriculaTurma mt = listitem.getValue();
                ctrlMatricula.cancelarMatricula(mt);
                listbox.removeItemAt(listbox.getSelectedIndex());
            }
            catch (Exception e) {
                Messagebox.show("Não foi possivel desmatricular");
            }
        }
        else {
            Messagebox.show("Selecione uma matricula");
        }
    }
}
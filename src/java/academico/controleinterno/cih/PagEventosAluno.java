/*
 * PagEventosAluno.java 
 * Versão: 0.1 
 * Data de Criação : 20/06/2012, 13:04:06
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
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os eventos da tela PagEventosAluno.zul
 * <p/>
 * @author Gabriel Quézid
 * @author Rodrigo Maia
 */
public class PagEventosAluno extends GenericForwardComposer {

    private CtrlPessoa ctrl = CtrlPessoa.getInstance();
    private CtrlCadastroCurso ctrlCurso = CtrlCadastroCurso.getInstance();
    private Window winDadosAluno;
    private Listbox listAluno;
    private ListModelList list; // the model of the listProfessor
    private Combobox curso;
    private Aluno a;
    private Curso select;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosAluno(this);

        a = (Aluno) arg.get("obj");

        List<Curso> cursos = ctrlCurso.obterCursos();
        curso.setModel(new ListModelList(cursos, true));

    }

    public void onSelect$curso(Event event) throws AcademicoException {
        select = (Curso) curso.getSelectedItem().getValue();

        List<Aluno> listaAlunos = ctrl.obterAlunos();

        if (listaAlunos != null) {
            for (int i = 0; i < listaAlunos.size(); i++) {
                Aluno a = listaAlunos.get(i);
                if (a.getCurso() == select) {
                    Listitem linha = new Listitem(listaAlunos.get(i).toString(), a);

                    linha.appendChild(new Listcell(listaAlunos.get(i).getCurso().toString()));

                    linha.setParent(listAluno);
                }
            }
        }
    }

    public void addAluno(Aluno a) {
        Listitem linha = new Listitem(a.toString(), a);
        linha.appendChild(new Listcell(a.getCurso().toString()));
        linha.setParent(listAluno);
    }

    public void refreshAluno(Aluno a) {
        for (int i = 0; i < listAluno.getItemCount(); i++) {
            if (listAluno.getItemAtIndex(i).getValue() == a) {
                listAluno.getItemAtIndex(i).getChildren().clear();
                listAluno.getItemAtIndex(i).appendChild(new Listcell(a.toString()));
                listAluno.getItemAtIndex(i).appendChild(new Listcell(a.getCurso().toString()));
                break;
            }
        }
    }

    public void onClick$excluirAluno(Event event) {
        Listitem listitem = listAluno.getSelectedItem();
        if (listitem != null) {
            try {
                ctrl.apagarAluno((Aluno) listitem.getValue());
                listAluno.removeItemAt(listAluno.getSelectedIndex());
                setMensagemAviso("success", "Aluno excluido com sucesso");
            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir o aluno");
            }
        }
        else {
            setMensagemAviso("info", "Selecione um aluno");
        }

    }

    public void onClick$incluirAluno(Event event) {
        ctrl.abrirIncluirAluno(a, select);
    }

    public void onClick$alterarAluno(Event event) {
        Listitem listitem = listAluno.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirEditarAluno((Aluno) listitem.getValue(), select);
        }
    }

    public void onClick$consultarAluno(Event event) {
        Listitem listitem = listAluno.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirConsultarAluno((Aluno) listitem.getValue());
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

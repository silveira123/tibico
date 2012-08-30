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

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os eventos da tela PagEventosMatricula.zul
 * <p/>
 * @author Eduardo Rigamonte
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
    private Aluno obj;
    private int permissao;
    private Div boxInformacao;
    private Label msg;
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
        ctrlMatricula.setPagEventosMatricula(this);
        List<Aluno> alunos = new ArrayList<Aluno>();
        obj = (Aluno) arg.get("aluno");
        if (obj != null) {
            alunos.add(obj);
            nomeAluno.setModel(new ListModelList(alunos, true));
            ((ListModelList) nomeAluno.getModel()).addToSelection(obj);
            nomeAluno.setDisabled(true);
            onSelect$nomeAluno(null);
            permissao = 1;
        }
        else {
            alunos = ctrlPessoa.obterAlunos();
            nomeAluno.setModel(new ListModelList(alunos, true));
            permissao = 0;
        }
    }

    public void onCreate$winEventosMatricula(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winEventosMatricula.detach();
        }
    }

    public void addMatricula(MatriculaTurma matTurma) {
        Listitem linha = new Listitem(matTurma.toString(), matTurma);
        linha.appendChild(new Listcell(matTurma.getTurma().getDisciplina().getPeriodoCorrespondente().toString()));
        linha.appendChild(new Listcell(matTurma.getTurma().getProfessor() + ""));
        linha.setParent(listbox);

    }

    public void onClick$nomeAluno(Event event) {

        while (listbox.getItemCount() > 0) {
            listbox.removeItemAt(0);
        }
        Aluno select = null;
    }

    public void onSelect$nomeAluno(Event event) {

        while (listbox.getItemCount() > 0) {
            listbox.removeItemAt(0);
        }

        Aluno select = null;
        if (nomeAluno.getSelectedItem() == null) {
            select = obj;
            ((ListModelList) nomeAluno.getModel()).addToSelection(obj);

        }
        else {
            select = (Aluno) nomeAluno.getSelectedItem().getValue();
        }
        if (select != null) {
            listbox.renderAll();
            try {
                List<MatriculaTurma> matTurma = ctrlMatricula.obterMatriculadas(select);

                for (int i = 0; i < matTurma.size(); i++) {
                    MatriculaTurma c = matTurma.get(i);
                    Listitem linha = new Listitem(matTurma.get(i).toString(), c);

                    linha.appendChild(new Listcell(matTurma.get(i).getTurma().getDisciplina().getPeriodoCorrespondente().toString()));
                    if (matTurma.get(i).getTurma().getProfessor() == null) {
                        linha.appendChild(new Listcell(""));
                    }
                    else {
                        linha.appendChild(new Listcell(matTurma.get(i).getTurma().getProfessor().toString()));
                    }
                    linha.setParent(listbox);
                }
            }
            catch (AcademicoException ex) {
                setMensagemAviso("error", "Erro ao obter matriculas");
            }
        }
    }

    public void onClick$matricular(Event event) {
        Comboitem comboitem = nomeAluno.getSelectedItem();
        if (comboitem != null) {
            Aluno a = comboitem.getValue();
            if (permissao == 1) {
                if (ctrlMatricula.verificaPeriodoMatricula(a.getCurso())) {
                    ctrlMatricula.abrirMatricular(a);
                }
                else {
                    setMensagemAviso("error", "Não está no período de matrícula");
                }
            }
            else {
                ctrlMatricula.abrirMatricular(a);
            }
        }
    }

    public void onClick$desmatricular(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                if (permissao == 1) {
                    if (ctrlMatricula.verificaPeriodoMatricula(((Aluno) nomeAluno.getSelectedItem().getValue()).getCurso())) {
                        MatriculaTurma mt = listitem.getValue();
                        ctrlMatricula.cancelarMatricula(mt);
                        listbox.removeItemAt(listbox.getSelectedIndex());
                    }
                    else {
                        setMensagemAviso("error", "Não está no período de matrícula");
                    }
                }
                else {
                    MatriculaTurma mt = listitem.getValue();
                    ctrlMatricula.cancelarMatricula(mt);
                    listbox.removeItemAt(listbox.getSelectedIndex());
                }

            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir a matricula");
            }
        }
        else {
            setMensagemAviso("info", "Selecione uma matricula");
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
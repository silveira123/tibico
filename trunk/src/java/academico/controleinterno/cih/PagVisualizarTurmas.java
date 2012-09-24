/*
 * PagEventosTurma.java 
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
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
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
 * PagEventosTurma.zul
 *
 * @author Pietro Christ
 * @author Geann Valfré
 */
public class PagVisualizarTurmas extends GenericForwardComposer {

    private CtrlLetivo ctrlLetivo = CtrlLetivo.getInstance();
    private CtrlCurso ctrlCadastroCurso = CtrlCurso.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Window winVisualizarTurmas;
    private Listbox listbox;
    private Combobox curso;
    private Combobox calendarioAcademico;
    private Combobox professor;
    private Curso c;
    private Professor obj;
    private Div boxInformacao;
    private Label msg;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Professor) arg.get("professor");
        if (obj != null) {
            List<Turma> turmas = ctrlLetivo.obterTurma(obj);
            List<Curso> cursos = new ArrayList<Curso>();
            for (Turma turma : turmas) {
                if (!cursos.contains(turma.getDisciplina().getCurso())) {
                    cursos.add(turma.getDisciplina().getCurso());
                }
            }
            curso.setModel(new ListModelList(cursos));
            List<Professor> professores = new ArrayList<Professor>();
            professores.add(obj);
            professor.setModel(new ListModelList(professores, true));
            ((ListModelList) professor.getModel()).addToSelection(obj);
            professor.setDisabled(true);
        } else {
            curso.setModel(new ListModelList(ctrlCadastroCurso.obterCursos()));
        }
        ctrlLetivo.setPagVisualizarTurmas(this);

    }
    
    public void onCreate$winVisualizarTurmas(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winVisualizarTurmas.detach();
        }
    }

    public void refreshTurma(Turma t) throws AcademicoException {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if (listbox.getItemAtIndex(i).getValue() == t) {
                listbox.getItemAtIndex(i).getChildren().clear();
                listbox.getItemAtIndex(i).appendChild(new Listcell(t.getProfessor().toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(t.toString()));
                List<MatriculaTurma> mat = CtrlMatricula.getInstance().obter(t);
                listbox.getItemAtIndex(i).appendChild(new Listcell(mat.size() + ""));
                listbox.getItemAtIndex(i).appendChild(new Listcell(t.getEstadoTurma().toString()));
                break;
            }
        }
    }

    public void onSelect$listbox(Event event) {
        ctrlLetivo.abrirFechamentoTurmas((Turma) listbox.getSelectedItem().getValue());
        listbox.setSelectedItem(null);
        
    }

    public void onSelect$curso(Event event) throws AcademicoException {
        c = curso.getSelectedItem().getValue();
        calendarioAcademico.setSelectedItem(null);
        while (calendarioAcademico.getItemCount() > 0) {
            calendarioAcademico.removeItemAt(0);
        }

        if (obj == null) {
            professor.setSelectedItem(null);
            while (professor.getItemCount() > 0) {
                professor.removeItemAt(0);
            }
        }
        while (listbox.getItemCount() > 0) {
            listbox.removeItemAt(0);
        }

        if (c != null && obj == null)  {
            calendarioAcademico.setModel(new ListModelList(ctrlLetivo.obterCalendarios(c)));
        } else if (c != null) {
            List<Calendario> calendarios = ctrlLetivo.obterCalendarios(c);
            List<Turma> turmas = ctrlLetivo.obterTurma(obj,c);
            List<Calendario> cal = new ArrayList<Calendario>();
            for (int i = 0; i < turmas.size();i++) {
                cal.add(turmas.get(i).getCalendario());
            }
            for (int i = 0; i < calendarios.size(); i++) {
                if (!cal.contains(calendarios.get(i))) {
                    calendarios.remove(calendarios.get(i));
                    i--;
                }
            }

            calendarioAcademico.setModel(new ListModelList(calendarios));
        }

    }

    public void onSelect$calendarioAcademico(Event event) throws AcademicoException {
        if (obj == null) {
            Calendario cal = calendarioAcademico.getSelectedItem().getValue();

            if (obj == null) {
                professor.setSelectedItem(null);
                while (professor.getItemCount() > 0) {
                    professor.removeItemAt(0);
                }
            }
            while (listbox.getItemCount() > 0) {
                listbox.removeItemAt(0);
            }

            if (cal != null) {
                professor.setModel(new ListModelList(ctrlPessoa.obterProfessor(cal)));
            }
        } else {
            onSelect$professor(null);
        }
    }

    public void onSelect$professor(Event event) throws AcademicoException {
        Professor p = (Professor) professor.getSelectedItem().getValue();
        if (p != null) {
            List<Turma> turmas = null;

            while (listbox.getItemCount() > 0) {
                listbox.removeItemAt(0);
            }

            try {
                turmas = ctrlLetivo.obterTurma(p, c);
            } catch (AcademicoException ex) {
                Logger.getLogger(PagVisualizarTurmas.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (turmas != null) {
                for (int i = 0; i < turmas.size(); i++) {
                    Turma t = turmas.get(i);
                    Listitem linha = new Listitem(turmas.get(i).getProfessor().toString(), t);
                    linha.appendChild(new Listcell(turmas.get(i).toString()));

                    List<MatriculaTurma> mat = CtrlMatricula.getInstance().obter(t);

                    linha.appendChild(new Listcell(mat.size() + ""));
                    if (turmas.get(i).getEstadoTurma() != null) {
                        linha.appendChild(new Listcell(turmas.get(i).getEstadoTurma().toString()));
                    } else {
                        linha.appendChild(new Listcell(""));
                    }
                    linha.setParent(listbox);
                }
            }
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
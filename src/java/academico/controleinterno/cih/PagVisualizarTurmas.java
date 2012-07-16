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


import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.*;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
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
 * A classe contém os eventos da tela PagEventosTurma.zul
 * @author Pietro Christ 
 * @author Geann Valfré
 */
public class PagVisualizarTurmas extends GenericForwardComposer {
    private CtrlLetivo ctrlLetivo = CtrlLetivo.getInstance();
    private CtrlCadastroCurso ctrlCadastroCurso = CtrlCadastroCurso.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Window winVisualizarTurmas;
    private Listbox listbox;
    private Combobox curso;
    private Combobox calendarioAcademico;
    private Combobox professor;
    private Button fecharTurmas;
    private Curso c;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrlLetivo.setPagVisualizarTurmas(this);
        curso.setModel(new ListModelList(ctrlCadastroCurso.obterCursos()));
 
    }
    
    public void refreshTurma(Turma t) throws AcademicoException {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if(listbox.getItemAtIndex(i).getValue() == t)
            {
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
    
    public void onSelect$listbox(Event event){
        ctrlLetivo.abrirFechamentoTurmas((Turma)listbox.getSelectedItem().getValue());
    }
     
    public void onSelect$curso(Event event){
        c = curso.getSelectedItem().getValue();
        calendarioAcademico.setSelectedItem(null);
        while(calendarioAcademico.getItemCount() > 0)
            calendarioAcademico.removeItemAt(0);
        
        professor.setSelectedItem(null);
        while(professor.getItemCount() > 0)
            professor.removeItemAt(0);
        
        while(listbox.getItemCount() > 0)
            listbox.removeItemAt(0);

        if(c != null)
            calendarioAcademico.setModel(new ListModelList(ctrlLetivo.obterCalendarios(c)));
    }
    
    public void onSelect$calendarioAcademico(Event event){
        Calendario cal = calendarioAcademico.getSelectedItem().getValue();
        
        professor.setSelectedItem(null);
        while(professor.getItemCount() > 0)
            professor.removeItemAt(0);
        
        while(listbox.getItemCount() > 0)
            listbox.removeItemAt(0);
        
        if(cal != null)
        { 
            professor.setModel(new ListModelList(ctrlPessoa.obterProfessor(cal)));
        }
    }
    
     public void onSelect$professor(Event event) throws AcademicoException{
         Professor p = (Professor) professor.getSelectedItem().getValue();
         if(p != null){
            List<Turma> turmas = null;
             
            while(listbox.getItemCount() > 0)
                listbox.removeItemAt(0);
            
            try {
                turmas = ctrlLetivo.obterTurma(p);
            } catch (AcademicoException ex) {
                Logger.getLogger(PagVisualizarTurmas.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (turmas != null){     
                for (int i = 0; i < turmas.size(); i++) {
                    Turma t = turmas.get(i);
                    Listitem linha = new Listitem(turmas.get(i).getProfessor().toString(), t);
                    linha.appendChild(new Listcell(turmas.get(i).toString()));
                    
                    List<MatriculaTurma> mat = CtrlMatricula.getInstance().obter(t);
                    
                    linha.appendChild(new Listcell(mat.size() + ""));
                    if(turmas.get(i).getEstadoTurma() != null)
                        linha.appendChild(new Listcell(turmas.get(i).getEstadoTurma().toString()));
                    else{
                        linha.appendChild(new Listcell(""));
                    }
                    linha.setParent(listbox);
                }
            }         
         } 
     } 

     public void onClick$fecharTurmas(Event event) throws Exception{
         ArrayList<Turma> turmas = new ArrayList<Turma>();
         
         for (int i = 0; i < listbox.getItemCount(); i++) 
             if(listbox.getItemAtIndex(i).isSelected())
                 turmas.add((Turma)listbox.getItemAtIndex(i).getValue());
         
        if(turmas.size() > 0)
        {
            if(ctrlLetivo.verificarPeriodoLetivo(c))
            {
                for (int i = 0; i < turmas.size(); i++) {
                    turmas.get(i).setEstadoTurma(EstadoTurma.ENCERRADA);
                    ctrlLetivo.fecharTurma(turmas.get(i));
                }
            }
            else
                Messagebox.show("Está fora do periodo Letivo");
        }
     
     }     
}
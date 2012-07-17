/*
 * PagRelatorioBoletim.java 
 * Versão: _._ 
 * Data de Criação : 03/07/2012, 13:40:23
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

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;


/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados;
 * A classe contém os eventos da tela PagRelatorioResultados.zul
 * @author Eduardo Rigamonte
 */
public class PagRelatorioResultados extends GenericForwardComposer{
    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private Combobox curso;
    private Combobox calendarioAcademico;
    private Combobox turma;
    private Label media;
    private Grid matriculas;
    private Window winResultados;
    private Curso objCurso;
    private Calendario objCalendario;
    private Turma objTurma;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (objCurso==null)
            curso.setModel(new ListModelList(ctrlMatricula.obter()));
        
        curso.setReadonly(true);
        calendarioAcademico.setReadonly(true);
        turma.setReadonly(true);
    }
    
    public void onCreate$winResultados(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winResultados.detach();
        }
    }
    
    public void onSelect$curso(Event event) throws AcademicoException {
        if(objCalendario==null)
            calendarioAcademico.setModel(new ListModelList(ctrlMatricula.obter((Curso)curso.getSelectedItem().getValue())));
        if(matriculas.getRows()!=null)matriculas.removeChild(matriculas.getRows());
        turma.setValue("");
        calendarioAcademico.setValue("");
        media.setValue("");
        
    }
    public void onSelect$calendarioAcademico(Event event) throws AcademicoException {
        if(objTurma==null)
            turma.setModel(new ListModelList(ctrlMatricula.obter((Calendario)calendarioAcademico.getSelectedItem().getValue())));
        if(matriculas.getRows()!=null)matriculas.removeChild(matriculas.getRows());
        turma.setValue("");
        media.setValue("");
    }
    
    public void onSelect$turma(Event event) {
        if(matriculas.getRows()!=null)matriculas.removeChild(matriculas.getRows());
        media.setValue("");
        Turma t = turma.getSelectedItem().getValue();
        double soma= 0;
        int contador=0;
        try {
            List<MatriculaTurma> matTurma = ctrlMatricula.obter(t);
            Rows linhas = new Rows();
            for (int i = 0; i < matTurma.size(); i++) {              
                MatriculaTurma c = matTurma.get(i);
                Row linha = new Row();
                
                ctrlMatricula.calculaNotaFinal(c);
                
                linha.appendChild(new Label(c.getAluno().getMatricula()));
                linha.appendChild(new Label(c.getAluno().getNome()));
                linha.appendChild(new Label(c.getPercentualPresenca().toString()));
                //TODO if fimdoperiodo() else "-"
                linha.appendChild(new Label(c.getResultadoFinal().toString()));
                linha.appendChild(new Label(c.getSituacaoAluno().toString()));
                
                linha.setParent(linhas);
                soma+=c.getResultadoFinal();
                contador ++;
            }
            linhas.setParent(matriculas);
            media.setValue((soma/contador)+"");
            
        } catch (Exception ex) {
            Logger.getLogger(PagRelatorioResultados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
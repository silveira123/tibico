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
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

/**
 * <<descrição da Classe>>
 * <p/>
 * @author erigamonte
 * @version
 * @see
 */
public class PagRelatorioHistorico extends GenericForwardComposer {

    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Textbox matricula;
    private Combobox nome;
    private Label curso;
    private Label coeficiente;
    private Grid disciplinas;
    private Aluno obj;
    private Window winHistorico;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Aluno) arg.get("aluno");
        if (obj != null) {
            List<Aluno>alunos = new ArrayList<Aluno>();
            alunos.add(obj);
            nome.setModel(new ListModelList(alunos, true));
            matricula.setValue(obj.getMatricula().toString());
            ((ListModelList) nome.getModel()).addToSelection(obj);
            nome.setDisabled(true);
            matricula.setDisabled(true);
            adicionaDisciplinas(obj);
        }
        else {
            nome.setModel(new ListModelList(ctrlPessoa.obterAlunos()));
        }
        nome.setReadonly(true);
        matricula.setReadonly(true);
    }

    public void onSelect$nome(Event event) throws Exception {
        obj = nome.getSelectedItem().getValue();
        matricula.setValue(obj.getMatricula().toString());
        adicionaDisciplinas(obj);
    }
    /**
     * Função para adicionar no grid as turmas cursadas pelo aluno
     * <p/>
     * @param aluno aluno correspondente
     * @return void
     */
    public void adicionaDisciplinas(Aluno aluno) throws Exception {
        if(disciplinas.getRows()!=null)disciplinas.removeChild(disciplinas.getRows());
        try {
            List<MatriculaTurma> matTurma = ctrlMatricula.emitirHistorico(obj);
            curso.setValue(obj.getCurso().toString());
            coeficiente.setValue(obj.getCoeficiente().toString());
            Rows linhas = new Rows();
            for (int i = 0; i < matTurma.size(); i++) {
                MatriculaTurma c = matTurma.get(i);
                Row linha = new Row();
                
                ctrlMatricula.calculaNotaFinal(c);
                
                linha.appendChild(new Label(c.getTurma().getDisciplina().toString()));
                linha.appendChild(new Label(c.getPercentualPresenca().toString()));
                //TODO if do fim do periodo
                linha.appendChild(new Label(c.getResultadoFinal().toString()));
                linha.appendChild(new Label(c.getSituacaoAluno().toString()));

                linha.setParent(linhas);
            }
            linhas.setParent(disciplinas);
        }
        catch (AcademicoException ex) {
            Messagebox.show("Erro ao obter matriculas");
        }
    }  
}
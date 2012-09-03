/*
 * PagRelatorioBoletim.java 
 * Versão: 0.1 
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

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para
 * leitura e interpretação de dados; A classe contém os eventos da tela
 * PagRelatorioHistorico.zul
 *
 * @author Eduardo Rigamonte
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
    private Div boxInformacao;
    private Label msg;
    private Button gerarPdf;
    private List<MatriculaTurma> matTurma;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Aluno) arg.get("aluno");


        matricula.setReadonly(true);
        gerarPdf.setDisabled(true);

        if (obj != null) {
            List<Aluno> alunos = new ArrayList<Aluno>();
            alunos.add(obj);
            nome.setModel(new ListModelList(alunos, true));
            matricula.setValue(obj.getMatricula().toString());
            ((ListModelList) nome.getModel()).addToSelection(obj);
            nome.setDisabled(true);
            matricula.setDisabled(true);
            adicionaDisciplinas(obj);
        } else {
            nome.setModel(new ListModelList(ctrlPessoa.obterAlunos()));
        }
    }

    public void onCreate$winHistorico(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winHistorico.detach();
        }
    }

    public void onSelect$nome(Event event) throws Exception {
        if (nome.getSelectedItem() != null) {
            obj = nome.getSelectedItem().getValue();
            if (obj != null) {
                matricula.setValue(obj.getMatricula().toString());
                adicionaDisciplinas(obj);
            }
        }else setMensagemAviso("info", "Aluno não encontrado");
        gerarPdf.setDisabled(false);
    }

    /**
     * Função para adicionar no grid as turmas cursadas pelo aluno
     * <p/>
     * @param aluno aluno correspondente
     * @return void
     */
    public void adicionaDisciplinas(Aluno aluno) throws Exception {
        if (disciplinas.getRows() != null) {
            disciplinas.removeChild(disciplinas.getRows());
        }
        try {
            matTurma = ctrlMatricula.emitirHistorico(obj);
            curso.setValue(obj.getCurso().toString());
            if (obj.getCoeficiente() != null) {
                coeficiente.setValue(obj.getCoeficiente().toString());
            }
            Rows linhas = new Rows();
            for (int i = 0; i < matTurma.size(); i++) {
                MatriculaTurma c = matTurma.get(i);
                Row linha = new Row();

                ctrlMatricula.calculaNotaFinal(c);

                linha.appendChild(new Label(c.getTurma().getDisciplina().toString()));
                linha.appendChild(new Label(c.toDecimalFormat()));
                linha.appendChild(new Label(c.getResultadoFinal().toString()));
                linha.appendChild(new Label(c.getSituacaoAluno().toString()));

                linha.setParent(linhas);

            }
            gerarPdf.setDisabled(false);
            linhas.setParent(disciplinas);
        } catch (AcademicoException ex) {
            setMensagemAviso("error", "Erro ao obter matriculas");
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

    public void onClick$gerarPdf(Event event) throws BadElementException, MalformedURLException, IOException, DocumentException, AcademicoException, Exception {
        if (!ctrlMatricula.emitirHistoricoPDF(obj)) {
            setMensagemAviso("error", "Não existem disciplinas encerradas");
        }

    }
}
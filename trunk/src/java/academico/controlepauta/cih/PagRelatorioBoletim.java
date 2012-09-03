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
import academico.controleinterno.cdp.Calendario;
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
 * leitura e interpretação de dados. A classe contém os eventos da tela
 * PagRelatorioBoletim.zul
 * <p/>
 * @author Eduardo Rigamonte
 */
public class PagRelatorioBoletim extends GenericForwardComposer {

    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Textbox matricula;
    private Combobox nome;
    private Combobox calendario;
    private Label curso;
    private Label coeficiente;
    private Grid disciplinas;
    private Aluno obj;
    private Window winBoletim;
    private Div boxInformacao;
    private Label msg;
    private Button gerarPdf;
    private List<MatriculaTurma> matTurma;
    private Calendario cal;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Aluno) arg.get("aluno");
        if (obj != null) {
            List<Aluno> alunos = new ArrayList<Aluno>();
            alunos.add(obj);
            nome.setModel(new ListModelList(alunos, true));
            matricula.setValue(obj.getMatricula().toString());
            ((ListModelList) nome.getModel()).addToSelection(obj);
            calendario.setModel(new ListModelList(buscaCalendarios(obj)));
            nome.setDisabled(true);
            matricula.setDisabled(true);
        } else {
            nome.setModel(new ListModelList(ctrlPessoa.obterAlunos()));
        }
        matricula.setReadonly(true);
        calendario.setReadonly(true);
        gerarPdf.setDisabled(true);
    }

    public void onCreate$winBoletim(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winBoletim.detach();
        }
    }

    public void onSelect$nome(Event event) {
        if (nome.getSelectedItem() != null) {
            obj = nome.getSelectedItem().getValue();
            if (obj != null) {
                matricula.setValue(obj.getMatricula().toString());
                calendario.setModel(new ListModelList(buscaCalendarios(obj)));
                calendario.setValue("");
                if (disciplinas.getRows() != null) {
                    disciplinas.removeChild(disciplinas.getRows());
                }
            }
        } else {
            calendario.setValue("");
            if (disciplinas.getRows() != null) {
                disciplinas.removeChild(disciplinas.getRows());
            }
            setMensagemAviso("info", "Aluno não encontrado");

        }
        gerarPdf.setDisabled(true);
    }

    public void onSelect$calendario(Event event) throws Exception {
        if (disciplinas.getRows() != null) {
            disciplinas.removeChild(disciplinas.getRows());
        }

        cal = calendario.getSelectedItem().getValue();
        try {
            matTurma = ctrlMatricula.emitirBoletim(obj, cal);
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
            linhas.setParent(disciplinas);
            gerarPdf.setDisabled(false);
        } catch (AcademicoException ex) {
            setMensagemAviso("error", "Erro ao obter matriculas");
        }
    }

    /**
     * <<descrição do método>>
     * <p/>
     * @param <<nome do parâmetro>> <<descrição do parâmetro>>
     * @param ...
     * @return <<descrição do retorno>>
     * @throws <<Exception gerada e o motivo>>
     */
    //Busca os calendarios que o aluno tem algum vinculo de matricula
    public List<Calendario> buscaCalendarios(Aluno a) {
        return ctrlMatricula.buscaCalendarios(a);
    }

    public void setMensagemAviso(String tipo, String mensagem) {
        boxInformacao.setClass(tipo);
        boxInformacao.setVisible(true);
        msg.setValue(mensagem);
    }

    public void onClick$boxInformacao(Event event) {
        boxInformacao.setVisible(false);
    }

    public void onClick$gerarPdf(Event event) throws BadElementException, MalformedURLException, IOException, DocumentException {
        cal = calendario.getSelectedItem().getValue();
        if (!ctrlMatricula.emitirBoletimPDF(obj, cal)) {
            setMensagemAviso("error", "Não existem disciplinas associadas ao aluno nesse calendário");
        }

    }
}
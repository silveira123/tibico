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

import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.SituacaoAlunoTurma;
import academico.util.Exceptions.AcademicoException;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os eventos da tela PagRelatorioResultados.zul
 * <p/>
 * @author Eduardo Rigamonte
 */
public class PagRelatorioResultados extends GenericForwardComposer {

    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private Combobox curso;
    private Combobox calendarioAcademico;
    private Combobox turma;
    private Label media;
    private Grid matriculas;
    private Window winResultados;
    private Curso objCurso;
    private Turma objTurma;
    private Button gerarGrafico;
    private Div boxInformacao;
    private Label msg;
    private Button gerarPdf;
    private List<MatriculaTurma> matTurma;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (objCurso == null) {
            curso.setModel(new ListModelList(ctrlMatricula.obter()));
        }

        curso.setReadonly(true);
        calendarioAcademico.setReadonly(true);
        turma.setReadonly(true);
        gerarPdf.setDisabled(true);
    }

    public void onCreate$winResultados(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winResultados.detach();
        }
    }

    public void onSelect$curso(Event event) throws AcademicoException {
        gerarGrafico.setDisabled(true);
        gerarPdf.setDisabled(true);

        calendarioAcademico.setModel(new ListModelList(ctrlMatricula.obter((Curso) curso.getSelectedItem().getValue())));
        if (matriculas.getRows() != null) {
            matriculas.removeChild(matriculas.getRows());
        }
        turma.setValue("");
        calendarioAcademico.setValue("");
        media.setValue("");

    }

    public void onSelect$calendarioAcademico(Event event) throws AcademicoException {
        gerarGrafico.setDisabled(true);
        gerarPdf.setDisabled(true);

        turma.setModel(new ListModelList(ctrlMatricula.obter((Calendario) calendarioAcademico.getSelectedItem().getValue())));
        if (matriculas.getRows() != null) {
            matriculas.removeChild(matriculas.getRows());
        }
        turma.setValue("");
        media.setValue("");
    }

    public void onClick$gerarGrafico(Event event) {
        double apr = 0, rep = 0;
        Window win = new Window();
        List row = matriculas.getRows().getChildren();

        for (int i = 0; i < row.size(); i++) {
            MatriculaTurma m = ((Row) row.get(i)).getValue();
            if (m.getSituacaoAluno() == SituacaoAlunoTurma.APROVADO) {
                apr++;
            }
            else if (m.getSituacaoAluno() == SituacaoAlunoTurma.REPROVADONOTA) {
                rep++;
            }
            else if (m.getSituacaoAluno() == SituacaoAlunoTurma.REPROVADOFALTA) {
                rep++;
            }
        }

        Chart chart = new Chart();
        chart.setTitle("Gráfico de Aprovados e Reprovados");
        chart.setWidth("550");
        chart.setType("pie");
        chart.setThreeD(true);
        chart.setPaneColor("#ffffff");
        chart.setFgAlpha(128);

        PieModel model = new SimplePieModel();
        model.setValue("Aprovados", new Double((apr / row.size()) * 100));
        model.setValue("Reprovados", new Double((rep / row.size()) * 100));
        chart.setModel(model);
        chart.setParent(win);

        win.setWidth("40%");
        win.setHeight("52%");
        win.setMode(Window.Mode.POPUP);
        win.setPosition("center,center");
        win.setVisible(true);
        win.setParent(winResultados);
    }

    public void onSelect$turma(Event event) {
        if (matriculas.getRows() != null) {
            matriculas.removeChild(matriculas.getRows());
        }
        media.setValue("");
        
        objTurma = turma.getSelectedItem().getValue();
        double soma = 0;
        int contador = 0;
        try {
            matTurma = ctrlMatricula.obter(objTurma);
            Rows linhas = new Rows();
            for (int i = 0; i < matTurma.size(); i++) {
                MatriculaTurma c = matTurma.get(i);
                Row linha = new Row();
                linha.setValue(c);
                ctrlMatricula.calculaNotaFinal(c);

                linha.appendChild(new Label(c.getAluno().getMatricula()));
                linha.appendChild(new Label(c.getAluno().getNome()));
                linha.appendChild(new Label(c.toDecimalFormat()));
                linha.appendChild(new Label(c.getResultadoFinal().toString()));
                linha.appendChild(new Label(c.getSituacaoAluno().toString()));

                linha.setParent(linhas);
                soma += c.getResultadoFinal();
                contador++;
            }
            linhas.setParent(matriculas);
            media.setValue((soma / contador) + "");
            gerarPdf.setDisabled(false);
            gerarGrafico.setDisabled(false);

        }
        catch (Exception ex) {
            Logger.getLogger(PagRelatorioResultados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$gerarPdf(Event event) throws BadElementException, MalformedURLException, IOException, DocumentException {

        if (!ctrlMatricula.gerarResultados(objTurma, Double.parseDouble(media.getValue()))) {
            setMensagemAviso("error", "Não existem matriculas para está turma");
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
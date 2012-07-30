/*
 * PagFormularioCurso.java 
 * Versão: 0.1 
 * Data de Criação : 22/06/2012, 10:33:15
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
import academico.controleinterno.cdp.Curso;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.academico.cdp.GrauInstrucao;
import academico.util.academico.cdp.Regime;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os dados formulário, abrangendo a leitura e interpretação para a tela
 * PagFormularioCurso.zul
 * <p/>
 * @author Geann Valfré
 * @author Eduardo Rigamonte
 */
public class PagFormularioCurso extends GenericForwardComposer {

    private CtrlCurso ctrl = CtrlCurso.getInstance();
    private Window winFormularioCurso;
    private Textbox nomeCurso, descricao;
    private Intbox duracao;
    private Combobox grandeArea, grauInstrucao, regime;
    private Curso obj;
    private Button salvarCurso;
    private int MODO;
    private Textbox sigla;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        GrauInstrucao vetGrauInstrucao[] = GrauInstrucao.values();
        List<GrauInstrucao> data = new ArrayList<GrauInstrucao>();

        Regime vetRegime[] = Regime.values();
        List<Regime> data2 = new ArrayList<Regime>();

        List<GrandeAreaConhecimento> vetGrandeAreaConhecimentos = ctrl.obterGrandeAreaConhecimento();

        for (int i = 0; i < vetGrauInstrucao.length; i++) {
            data.add(vetGrauInstrucao[i]);
        }

        for (int i = 0; i < vetRegime.length; i++) {
            data2.add(vetRegime[i]);
        }

        regime.setModel(new ListModelList(data2, true));
        grauInstrucao.setModel(new ListModelList(data, true));
        grandeArea.setModel(new ListModelList(vetGrandeAreaConhecimentos, true));

        grandeArea.setReadonly(true);
        grauInstrucao.setReadonly(true);
        regime.setReadonly(true);
    }

    public void onCreate$winFormularioCurso() {
        
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioCurso.detach();
        }
        else {
            MODO = (Integer) arg.get("tipo");

            if (MODO != ctrl.SALVAR) {
                obj = (Curso) arg.get("obj");
                preencherTela();
                if (MODO == ctrl.CONSULTAR) {
                    this.salvarCurso.setVisible(false);
                    bloquearTela();
                }
            }
        }
    }

    private void preencherTela() {
        nomeCurso.setText(obj.getNome());
        descricao.setText(obj.getDescricao());
        duracao.setValue(obj.getDuracao());
        sigla.setText(obj.getSigla());

        List<Comboitem> a = grauInstrucao.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            // verificando qual o grau de instrução ta cadastrado
            if (a.get(i).getValue() == obj.getGrauInstrucao()) {
                grauInstrucao.setSelectedItem(a.get(i));
            }
        }

        a = grandeArea.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            // verificando qual grande area de conhecimento ta cadastrado
            if (a.get(i).getValue() == obj.getGrandeAreaConhecimento()) {
                grandeArea.setSelectedItem(a.get(i));
            }
        }

        a = regime.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            // verificando qual o regimeta cadastrado
            if (a.get(i).getValue() == obj.getRegime()) {
                regime.setSelectedItem(a.get(i));
            }
        }
    }

    private void bloquearTela() {
        nomeCurso.setDisabled(true);
        descricao.setDisabled(true);
        sigla.setDisabled(true);
        duracao.setDisabled(true);
        grauInstrucao.setDisabled(true);
        grandeArea.setDisabled(true);
        regime.setDisabled(true);
    }

    public void onClick$salvarCurso(Event event) {

        Curso c = null;
        String msg = valido();
        if (msg.trim().equals("")) {
            if (MODO == ctrl.EDITAR) {
                obj.setNome(nomeCurso.getText());
                obj.setDuracao(duracao.getValue());
                obj.setDescricao(descricao.getText());
                obj.setGrauInstrucao(GrauInstrucao.valueOf(grauInstrucao.getText()));
                obj.setGrandeAreaConhecimento((GrandeAreaConhecimento) grandeArea.getSelectedItem().getValue());
                obj.setRegime(Regime.valueOf(regime.getText()));
                obj.setSigla(sigla.getText());
                c = ctrl.alterarCurso(obj);
                if (c != null) {
                    winFormularioCurso.onClose();
                }
                else {
                    Messagebox.show("Duração deve ser maior que 0!");
                }
            }
            else {
                ArrayList<Object> list = new ArrayList<Object>();
                list.add(nomeCurso.getText());
                list.add(duracao.getValue());
                list.add(descricao.getText());
                list.add(GrauInstrucao.valueOf(grauInstrucao.getText()));
                list.add(grandeArea.getSelectedItem().getValue());
                list.add(Regime.valueOf(regime.getText()));
                list.add(sigla.getText());
                c = ctrl.incluirCurso(list);
                if (c != null) {
                    winFormularioCurso.onClose();
                }
                else {
                    Messagebox.show("Duração deve ser maior que 0!");
                }
            }

        }
        else {
            Messagebox.show(msg, "Informe:", 0, Messagebox.EXCLAMATION);
        }
    }

    public void onClick$voltar(Event event) {
        winFormularioCurso.onClose();
    }

    public void limparCampos() {
        nomeCurso.setText("");
        descricao.setText("");
        duracao.setText("");
        grauInstrucao.setText("");
        grandeArea.setText("");
        regime.setText("");
    }

    private String valido() {
        String msg = "";

        if (nomeCurso.getText().trim().equals("")) {
            msg += "- Nome\n";
        }
        if (sigla.getText().trim().equals("")) {
            msg += "- Sigla\n";
        }
        if (grandeArea.getSelectedItem() == null) {
            msg += "- Grande Área de Conhecimento\n";
        }
        if (grauInstrucao.getSelectedItem() == null) {
            msg += "- Grau de Instrução\n";
        }
        if (duracao.getValue() == null) {
            msg += "- Duração\n";
        }

        return msg;
    }

}

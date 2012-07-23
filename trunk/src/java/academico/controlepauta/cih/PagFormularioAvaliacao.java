/*
 * PagFormularioAvaliacao.java 
 * Versão: 0.1 
 * Data de Criação : 15/06/2012
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

import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cdp.Avaliacao;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os dados do formulário, abrangendo a leitura e interpretação para a tela
 * PagFormularioAvaliacao.zul
 *
 * @author Pietro Crhist
 * @author Geann Valfré
 * @author Gabriel Quézid
 */
public class PagFormularioAvaliacao extends GenericForwardComposer {

    private CtrlAula ctrl = CtrlAula.getInstance();
    private Window winFormularioAvaliacao;
    private Textbox turma, nomeAvaliacao;
    private Intbox peso;
    private Avaliacao obj;
    private Turma obj2;
    private Button salvar, voltar;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        turma.setDisabled(true);
    }

    public void onCreate$winFormularioAvaliacao() {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioAvaliacao.detach();
        }
        else {
            MODO = (Integer) arg.get("tipo");

            if (MODO != ctrl.SALVAR) {
                obj = (Avaliacao) arg.get("obj");

                preencherTela();
                if (MODO == ctrl.CONSULTAR) {
                    this.salvar.setVisible(false);
                    bloquearTela();
                }
            }
            else {
                obj2 = (Turma) arg.get("obj");
                turma.setValue(obj2.toString());
            }
        }
    }

    private void preencherTela() {
        turma.setValue(obj.getTurma().toString());
        nomeAvaliacao.setValue(obj.getNome());
        peso.setValue(obj.getPeso());
    }

    private void bloquearTela() {
        turma.setDisabled(true);
        nomeAvaliacao.setDisabled(true);
        peso.setDisabled(true);
    }

    public void onClick$salvar(Event event) {
        try {
            String msg = valido();
            if (msg.trim().equals("")) {
                if (MODO == ctrl.SALVAR) {
                    ArrayList<Object> args = new ArrayList<Object>();
                    args.add(obj2);
                    args.add(nomeAvaliacao.getValue());
                    args.add(peso.getValue());

                    Avaliacao a = ctrl.incluirAvaliacao(args);

                    // Instancia um resultado, com nota NULL, para cada Aluno
                    ctrl.atribuirResultado(a, obj2);

                    limparCampos();
                }
                else {
                    obj.setNome(nomeAvaliacao.getValue());
                    obj.setPeso(peso.getValue());

                    ctrl.alterarAvaliacao(obj);
                }
                winFormularioAvaliacao.onClose();
            }
            else {
                Messagebox.show(msg, "Informe: ", 0, Messagebox.EXCLAMATION);
            }
        }
        catch (AcademicoException ex) {
            Logger.getLogger(PagFormularioAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            Logger.getLogger(PagFormularioAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$voltar(Event event) {
        winFormularioAvaliacao.onClose();
    }

    public void limparCampos() {
        turma.setValue("");
        nomeAvaliacao.setValue("");
        peso.setValue(null);
    }

    private String valido() {
        String msg = "";

        if (nomeAvaliacao.getText().trim().equals("")) {
            msg += "- Nome da Avaliação\n";
        }
        if (peso.getValue() == null) {
            msg += "- Peso da Avaliação\n";
        }

        return msg;
    }

}

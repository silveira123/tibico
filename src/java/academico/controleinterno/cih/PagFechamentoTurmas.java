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

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.SituacaoTurma;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.Usuario;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os eventos da tela PagEventosTurma.zul
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 * @author Gabriel Quézid
 */
public class PagFechamentoTurmas extends GenericForwardComposer {

    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winFechamentoTurmas;
    private Grid matriculas;
    private Turma obj;
    private Button abrirTurma, fecharTurma;
    private Usuario user;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Turma) arg.get("turma");
        if (obj != null) {
            if(obj.getEstadoTurma().equals(SituacaoTurma.CURSANDO))
            {
                abrirTurma.setDisabled(true);
                fecharTurma.setDisabled(false);
            }
            else if(obj.getEstadoTurma().equals(SituacaoTurma.ENCERRADA))
            {
                fecharTurma.setDisabled(true);
                abrirTurma.setDisabled(false);
            }
                
            List<MatriculaTurma> matTurma = ctrlMatricula.obter(obj);
            Rows linhas = new Rows();

            for (int i = 0; i < matTurma.size(); i++) {
                MatriculaTurma c = matTurma.get(i);
                Row linha = new Row();

                ctrlMatricula.calculaNotaFinal(c);

                linha.appendChild(new Label(c.getAluno().getMatricula()));
                linha.appendChild(new Label(c.getAluno().getNome()));
                linha.appendChild(new Label(c.toDecimalFormat()));
                linha.appendChild(new Label(c.getResultadoFinal().toString()));
                linha.appendChild(new Label(c.getSituacaoAluno().toString()));

                linha.setParent(linhas);
            }
            linhas.setParent(matriculas);
        }
    }

    public void onCreate$winFechamentoTurmas(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFechamentoTurmas.detach();
        }
        else {
            user = (Usuario)Executions.getCurrent().getSession().getAttribute("usuario");
            if (user.getPrivilegio() != 1) {
                abrirTurma.setVisible(false);
            }
        }
    }

    public void onClick$fecharTurma(Event event) throws Exception {
        if (ctrl.verificarPeriodoLetivo(obj.getDisciplina().getCurso(), obj)) {
            obj.setEstadoTurma(SituacaoTurma.ENCERRADA);
            ctrl.alterarVisualizarTurma(obj);
            winFechamentoTurmas.onClose();
        }
        else {
            Messagebox.show("Está fora do periodo Letivo");
        }
    }

    public void onClick$abrirTurma(Event event) throws Exception {
        if (ctrl.verificarPeriodoLetivo(obj.getDisciplina().getCurso(), obj)) {
            obj.setEstadoTurma(SituacaoTurma.CURSANDO);
            ctrl.alterarVisualizarTurma(obj);
            winFechamentoTurmas.onClose();
        }
        else {
            Messagebox.show("Está fora do periodo Letivo");
        }
    }

    public void onClick$voltar(Event event) throws Exception {
        winFechamentoTurmas.onClose();
    }

}
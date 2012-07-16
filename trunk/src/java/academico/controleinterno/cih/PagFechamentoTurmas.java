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
import academico.controleinterno.cdp.EstadoTurma;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import java.util.List;
import org.zkoss.zk.ui.Component;
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
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Turma) arg.get("turma");
        List<MatriculaTurma> matTurma = ctrlMatricula.obter(obj);
        Rows linhas = new Rows();
        
        for (int i = 0; i < matTurma.size(); i++) {              
            MatriculaTurma c = matTurma.get(i);
            Row linha = new Row();

            ctrlMatricula.calculaNotaFinal(c);

            linha.appendChild(new Label(c.getAluno().getMatricula()));
            linha.appendChild(new Label(c.getAluno().getNome()));
            linha.appendChild(new Label(c.getPercentualPresenca().toString()));
            linha.appendChild(new Label(c.getResultadoFinal().toString()));
            linha.appendChild(new Label(c.getSituacaoAluno().toString()));

            linha.setParent(linhas);
        }
        linhas.setParent(matriculas);
    }
    
    public void onClick$fecharTurma(Event event) throws Exception
    {
        if(ctrl.verificarPeriodoLetivo(obj.getDisciplina().getCurso()))
        {
            obj.setEstadoTurma(EstadoTurma.ENCERRADA);
            ctrl.fecharTurma(obj);
            winFechamentoTurmas.onClose();
        }
        else
            Messagebox.show("Está fora do periodo Letivo");
    }
    
    public void onClick$voltar(Event event) throws Exception
    {
       winFechamentoTurmas.onClose();
    }
}
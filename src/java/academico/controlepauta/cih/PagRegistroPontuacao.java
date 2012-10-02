/*
 * PagRegistroPontuacao.java 
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

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cdp.Avaliacao;
import academico.controlepauta.cdp.Resultado;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados. A classe contém os dados do registro, abrangendo a leitura e interpretação para a tela
 * PagRegistroPontuacao.zul
 *
 * @author Gabriel Quézid
 */
public class PagRegistroPontuacao extends GenericForwardComposer {

    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Window winRegistroPontuacao;
    private Textbox nome;
    private Textbox nomeAvaliacao;
    private Intbox peso;
    private Listbox listbox;
    private Button salvar;
    private Button voltar;
    private Avaliacao obj;
    private List<Doublebox> notas = new ArrayList<Doublebox>();
    private List<Textbox> observacoes = new ArrayList<Textbox>();
    private List<Aluno> aluno;
    private List<Resultado> resultados;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Avaliacao) arg.get("obj");
        if (obj != null) {
            resultados = ctrl.obterResultados(obj);
            nome.setValue(obj.getTurma().toString());
            nomeAvaliacao.setValue(obj.getNome());
            peso.setValue(obj.getPeso());

            aluno = ctrlPessoa.obterAlunosporTurma(obj.getTurma());
            for (int i = 0; i < aluno.size(); i++) {
                Aluno c = aluno.get(i);
                Listitem linha = new Listitem(c.getMatricula() + "", c);
                linha.appendChild(new Listcell(c.getNome()));

                Listcell listcell = new Listcell();
                Doublebox t1 = new Doublebox();
                t1.setWidth("70%");
                for (int j = 0; j < resultados.size(); j++) {
                    if (i < resultados.size() && resultados.get(j).getMatriculaTurma().getAluno().equals(aluno.get(i))) {
                        t1.setValue(resultados.get(j).getPontuacao());
                    }
                }
                t1.setParent(listcell);
                notas.add(t1);

                linha.appendChild(listcell);

                Listcell listcell2 = new Listcell();
                Textbox t = new Textbox();
                t.setWidth("99%");
                for (int j = 0; j < resultados.size(); j++) {
                    if (i < resultados.size() && resultados.get(j).getMatriculaTurma().getAluno().equals(aluno.get(i))) {
                        t.setValue(resultados.get(j).getObservacao());
                    }
                }
                t.setParent(listcell2);
                observacoes.add(t);

                linha.appendChild(listcell2);

                linha.setParent(listbox);
            }
            bloquearTela();
        }
    }

    public void onCreate$winRegistroPontuacao() throws AcademicoException {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winRegistroPontuacao.detach();
        }
    }

    public void onClick$salvar(Event event) throws Exception {
        ArrayList<Object> argsNotas = new ArrayList<Object>();
        ArrayList<Object> argsObservacoes = new ArrayList<Object>();
        for (int i = 0; i < notas.size(); i++) {
            argsNotas.add(notas.get(i).getValue());
            argsObservacoes.add(observacoes.get(i).getValue());
        }
        try {
            ctrl.incluirResultado(obj, argsNotas, argsObservacoes);
        }
        catch (AcademicoException ex) {
            Logger.getLogger(PagRegistroPontuacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        winRegistroPontuacao.onClose();
    }

    public void onClick$voltar(Event event) {
        winRegistroPontuacao.onClose();
    }

    public void bloquearTela() {
        nome.setDisabled(true);
        nomeAvaliacao.setDisabled(true);
        peso.setDisabled(true);
    }

}
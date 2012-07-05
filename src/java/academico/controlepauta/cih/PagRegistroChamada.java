package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.Aula;
import academico.controlepauta.cdp.Frequencia;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagRegistroChamada extends GenericForwardComposer {

    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    private Window winRegistroChamada;
    private Textbox nomeTurma, conteudo;
    private Datebox data;
    private Intbox qtdAulas;
    private Listbox listbox;
    private Button salvar;
    private Button fechar;
    private Aula obj;
    private Turma obj2;
    private List<Intbox> faltas = new ArrayList<Intbox>();
    private List<MatriculaTurma> matriculaturmas;
    private List<Frequencia> frequencias;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj2 = (Turma) arg.get("obj2");
        nomeTurma.setValue(obj2.toString());
        nomeTurma.setDisabled(true);

        List<Aluno> alunos = ctrlPessoa.obterAlunosporTurma(obj2);
        matriculaturmas = ctrlMatricula.obter(obj2);

        List<Aula> aulas = ctrl.obterAulas();
        for (int i = 0; i < aulas.size(); i++) {
            if (aulas.get(i).getTurma() == obj2) {
                obj = aulas.get(i);
                break;
            }
        }

        for (int i = 0; i < alunos.size(); i++) {
            Listitem linha = new Listitem(alunos.get(i).getMatricula() + "", alunos);
            linha.appendChild(new Listcell(alunos.get(i).getNome()));

            frequencias = ctrl.obterFrequencias(alunos.get(i));
            int totalFaltas = 0;

            for (int j = 0; j < frequencias.size(); j++) {
                totalFaltas += frequencias.get(j).getNumFaltasAula();
            }

            linha.appendChild(new Listcell(totalFaltas + ""));

            Listcell listcell = new Listcell();
            Intbox t1 = new Intbox();
            t1.setWidth("40%");
            t1.setParent(listcell);
            faltas.add(t1);
            linha.appendChild(listcell);

            linha.setParent(listbox);
        }
    }

    public void onCreate$winRegistroChamada() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != ctrl.SALVAR) {
            obj = (Aula) arg.get("obj");

            preencherTela();
            if (MODO == ctrl.CONSULTAR) {
                this.salvar.setVisible(false);
                bloquearTela();
            }
        }
    }

    public void onClick$salvar(Event event) {
        try {
            String msg = valido();
            if (msg.trim().equals("")) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                if (MODO == ctrl.SALVAR) {
                    ArrayList<Object> args = new ArrayList<Object>();
                    cal.setTime(data.getValue());
                    args.add(cal);
                    args.add(qtdAulas.getValue());
                    args.add(conteudo.getValue());
                    args.add(obj2);

                    frequencias = new ArrayList<Frequencia>();
                    for (int i = 0; i < faltas.size(); i++) {
                        Frequencia f = new Frequencia();
                        f.setNumFaltasAula(faltas.get(i).getValue());
                        f.setMatriculaTurma(matriculaturmas.get(i));
                        frequencias.add(f);
                    }

                    args.add(frequencias);
                    ctrl.incluirAula(args);
                } else {
                    cal.setTime(data.getValue());
                    obj.setDia(cal);
                    obj.setTurma(obj2);
                    obj.setQuantidade(qtdAulas.getValue());
                    obj.setConteudo(conteudo.getValue());
                    List<Frequencia> frequencia = new ArrayList<Frequencia>();
                    for (int i = 0; i < obj.getFrequencia().size(); i++) {
                        Frequencia t = null;
                        try {
                            t = (Frequencia) ((Frequencia) obj.getFrequencia().get(i)).clone();
                        } catch (CloneNotSupportedException ex) {
                            System.err.println(ex);
                        }
                        frequencia.add(t);


                    }
                    for (int i = 0; i < faltas.size(); i++) {
                        if (obj.getFrequencia().get(i).getNumFaltasAula() == faltas.get(i).getValue()) {
                            continue;
                        } else {
                            obj.getFrequencia().get(i).setNumFaltasAula(faltas.get(i).getValue());
                        }
                    }
                    ctrl.alterarAula(obj, frequencia);
                }
                winRegistroChamada.onClose();
            } else {
                Messagebox.show(msg,
                        "", 0, Messagebox.EXCLAMATION);
            }
        } catch (AcademicoException ex) {
            Logger.getLogger(PagFormularioAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PagFormularioAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$fechar(Event event) {
        winRegistroChamada.onClose();
    }

    public void bloquearTela() {
        nomeTurma.setDisabled(true);
        conteudo.setDisabled(true);
        data.setDisabled(true);
        qtdAulas.setDisabled(true);
        listbox.setDisabled(true);

        for (int i = 0; i < faltas.size(); i++) {
            faltas.get(i).setDisabled(true);
        }
    }

    private void preencherTela() {
        data.setValue(obj.getDia().getTime());
        qtdAulas.setValue(obj.getQuantidade());
        conteudo.setValue(obj.getConteudo());

        for (int i = 0; i < faltas.size(); i++) {
            faltas.get(i).setValue(obj.getFrequencia().get(i).getNumFaltasAula());
        }
    }

    private String valido() {
        String msg = "";

        if (data.getValue() == null) {
            msg += "- Data\n";
        }
        if (qtdAulas.getText().trim().equals("")) {
            msg += "- Quantidade de Aulas\n";
        }
        if (conteudo.getText().trim().equals("")) {
            msg += "- Conteudo\n";
        }

        for (int i = 0; i < faltas.size(); i++) {
            if (faltas.get(i).getValue() == null) {
                msg += "- Todos os campos Faltas\n";
                break;
            }
        }


        if (!msg.trim().equals("")) {
            msg = "Informe:\n" + msg;
        }
        return msg;
    }
}

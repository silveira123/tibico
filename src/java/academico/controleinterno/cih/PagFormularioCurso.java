package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCursoDisplina;
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

public class PagFormularioCurso extends GenericForwardComposer {

    private CtrlCadastroCursoDisplina ctrl = CtrlCadastroCursoDisplina.getInstance();
    private Window winCadastro;
    private Textbox nome, descricao;
    private Intbox duracao;
    private Combobox grauInstrucao, grandeAreaConhecimento, regime;
    private Curso obj;
    private int MODO;

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
        grandeAreaConhecimento.setModel(new ListModelList(vetGrandeAreaConhecimentos, true));

    }

    public void onCreate$winCadastro() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != ctrl.SALVAR) {

            obj = (Curso) arg.get("obj");
            preencherTela();
            if (MODO == ctrl.CONSULTAR) {
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        nome.setText(obj.getNome());
        descricao.setText(obj.getDescricao());
        duracao.setValue(obj.getDuracao());

        List<Comboitem> a = grauInstrucao.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getValue() == obj.getGrauInstrucao()) // verificando qual o grau de instrução ta cadastrado
            {
                grauInstrucao.setSelectedItem(a.get(i));
            }
        }

        a = grandeAreaConhecimento.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getValue() == obj.getGrandeAreaConhecimento()) // verificando qual grande area de conhecimento ta cadastrado
            {
                grandeAreaConhecimento.setSelectedItem(a.get(i));
            }
        }

        a = regime.getItems(); // retornado a lista de instruções
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getValue() == obj.getRegime()) // verificando qual o regimeta cadastrado
            {
                regime.setSelectedItem(a.get(i));
            }
        }

    }

    private void bloquearTela() {
        nome.setReadonly(true);
        descricao.setReadonly(true);
        duracao.setReadonly(true);
        grauInstrucao.setDisabled(true);
        grandeAreaConhecimento.setDisabled(true);
        regime.setDisabled(true);
    }

    public void onClick$Salvar(Event event) {

        Curso c = null;
        try {
            if (MODO == ctrl.EDITAR) {
                obj.setNome(nome.getText());
                obj.setDuracao(duracao.getValue());
                obj.setDescricao(descricao.getText());
                obj.setGrauInstrucao(GrauInstrucao.valueOf(grauInstrucao.getText()));
                obj.setGrandeAreaConhecimento((GrandeAreaConhecimento) grandeAreaConhecimento.getSelectedItem().getValue());
                obj.setRegime(Regime.valueOf(regime.getText()));
                c = ctrl.alterarCurso(obj);
                alert("Cadastro editado!");
            }
            else {
                ArrayList<Object> list = new ArrayList<Object>();
                list.add(nome.getText());
                list.add(duracao.getValue());
                list.add(descricao.getText());
                list.add(GrauInstrucao.valueOf(grauInstrucao.getText()));
                list.add(grandeAreaConhecimento.getSelectedItem().getValue());
                list.add(Regime.valueOf(regime.getText()));

                c = ctrl.incluirCurso(list);
                alert("Cadastro feito!");
                limparCampos();
            }
        }
        catch (Exception e) {
            alert("Falha no cadastro feito!");
        }


    }

    public void onClose$winCadastro(Event event) {
        ctrl.redirectPag("/pageventoscurso.zul");
    }

    public void limparCampos() {
        nome.setText("");
        descricao.setText("");
        duracao.setText("");
        grauInstrucao.setText("");
        grandeAreaConhecimento.setText("");
        regime.setText("");
    }
}

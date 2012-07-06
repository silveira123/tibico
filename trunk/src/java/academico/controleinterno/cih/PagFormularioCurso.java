package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import academico.controlepauta.cih.PagPrincipal;
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

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
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
        nomeCurso.setReadonly(true);
        descricao.setReadonly(true);
        duracao.setReadonly(true);
        grauInstrucao.setDisabled(true);
        grandeArea.setDisabled(true);
        regime.setDisabled(true);
    }

    public void onClick$salvarCurso(Event event) {

        Curso c = null;
        try {
            String msg = valido();
            if(msg.trim().equals("")){
				if (MODO == ctrl.EDITAR) {
					obj.setNome(nomeCurso.getText());
					obj.setDuracao(duracao.getValue());
					obj.setDescricao(descricao.getText());
					obj.setGrauInstrucao(GrauInstrucao.valueOf(grauInstrucao.getText()));
					obj.setGrandeAreaConhecimento((GrandeAreaConhecimento) grandeArea.getSelectedItem().getValue());
					obj.setRegime(Regime.valueOf(regime.getText()));
					obj.setSigla(sigla.getText());
					c = ctrl.alterarCurso(obj);
					Messagebox.show("Cadastro editado!");
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
					Messagebox.show("Cadastro feito!");
					limparCampos();
				}
				winFormularioCurso.onClose();
			}
            else Messagebox.show(msg, "", 0, Messagebox.EXCLAMATION);
        }
        catch (Exception e) {
            Messagebox.show("Falha no cadastro feito!");
            System.err.println(e);
        }
    }

    public void onClick$cancelarCurso(Event event) {
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
        
        if (nomeCurso.getText().trim().equals(""))
            msg += "- Nome\n";
		if (sigla.getText().trim().equals(""))
            msg += "- Sigla\n";	
        if (grandeArea.getSelectedItem() == null)
            msg += "- Grande Área de Conhecimento\n";
        if (grauInstrucao.getSelectedItem() == null)
            msg += "- Grau de Instrução\n";
        if (duracao.getValue() == null)
            msg += "- Duração\n";
        
        if(!msg.trim().equals(""))
            msg = "Informe:\n"+msg;
        return msg;
    }
}

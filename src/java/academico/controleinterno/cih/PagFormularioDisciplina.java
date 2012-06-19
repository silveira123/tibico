package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCursoDisplina;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

public class PagFormularioDisciplina extends GenericForwardComposer {

    private CtrlCadastroCursoDisplina ctrl = CtrlCadastroCursoDisplina.getInstance();
    private Window winCadastro;
    private Textbox nome;
    private Intbox cargaHoraria, numCreditos, periodoCorrespondente;
    private Combobox areaConhecimento;
    private Listbox preRequisitos;
    private Disciplina obj;
    private Curso objCurso;
    private Button salvar;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Disciplina> listaDisciplina = ctrl.obterDisciplinas();
        List<Disciplina> data = new ArrayList<Disciplina>();

        List<AreaConhecimento> vetAreaConhecimentos = ctrl.obterAreaConhecimento();
        if (listaDisciplina != null) {
            for (int i = 0; i < listaDisciplina.size(); i++) {
                data.add(listaDisciplina.get(i));
            }
        }
        areaConhecimento.setModel(new ListModelList(vetAreaConhecimentos, true));
        preRequisitos.setModel(new ListModelList(data, true));
        ((ListModelList)preRequisitos.getModel()).setMultiple(true);
    }

    public void onCreate$winCadastro() {
        MODO = (Integer) arg.get("tipo");
        objCurso = (Curso) arg.get("curso");

        if (MODO != ctrl.SALVAR) {

            obj = (Disciplina) arg.get("obj");
            preencherTela();
            if (MODO == ctrl.CONSULTAR) {
                this.salvar.setVisible(false);
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        nome.setText(obj.getNome());
        cargaHoraria.setValue(obj.getCargaHoraria());
        numCreditos.setValue(obj.getNumCreditos());
        periodoCorrespondente.setValue(obj.getPeriodoCorrespondente());
        
        
        if (obj.getPrerequisito().size() > 0) {
            List<Listitem> listItems = preRequisitos.getItems();
            List selects = obj.getPrerequisito();
            
            for (int i = 0; i < listItems.size(); i++) {
                if(selects.contains(listItems.get(i).getValue()))
                    preRequisitos.addItemToSelection(listItems.get(i));
            }
        }

        // retornado a lista de areadeconhecimento
        List<Comboitem> a = areaConhecimento.getItems();
        for (int i = 0; i < a.size(); i++) {
            // verificando qual a area de conhecimento cadastrado
            if (a.get(i).getValue() == obj.getAreaConhecimento()) {
                areaConhecimento.setSelectedItem(a.get(i));
            }
        }
    }

    private void bloquearTela() {
        nome.setReadonly(true);
        cargaHoraria.setReadonly(true);
        numCreditos.setReadonly(true);
        periodoCorrespondente.setReadonly(true);
        areaConhecimento.setDisabled(true);
        preRequisitos.setDisabled(true);
    }

    public void onClick$salvar(Event event) {

        Disciplina d = null;
        try {
            if (MODO == ctrl.EDITAR) {
                obj.setNome(nome.getText());
                obj.setCargaHoraria(cargaHoraria.getValue());
                obj.setNumCreditos(numCreditos.getValue());
                obj.setAreaConhecimento((AreaConhecimento) areaConhecimento.getSelectedItem().getValue());
                obj.setPeriodoCorrespondente(periodoCorrespondente.getValue());
                obj.setCurso(objCurso);

                Set aux = ((Selectable) preRequisitos.getModel()).getSelection();
                obj.setPrerequisito(new ArrayList<Disciplina>(aux));

                d = ctrl.alterarDisciplina(obj);
                alert("Cadastro editado!");
            }
            else {
                ArrayList<Object> list = new ArrayList<Object>();

                list.add(nome.getText());
                list.add(cargaHoraria.getValue());
                list.add(numCreditos.getValue());
                list.add(periodoCorrespondente.getValue());

                Set aux = ((Selectable) preRequisitos.getModel()).getSelection();
                list.add(new ArrayList<Disciplina>(aux));

                list.add(objCurso);
                list.add(areaConhecimento.getSelectedItem().getValue());

                d = ctrl.incluirDisciplina(list);
                alert("Cadastro feito!");

                limparCampos();
            }
        }
        catch (Exception e) {
            alert("Falha no cadastro feito!");
        }

    }

    public void onClose$winCadastro(Event event) {
        ctrl.abrirEventosDisciplina(objCurso);
    }

    public void limparCampos() {
        nome.setText("");
        cargaHoraria.setText("");
        numCreditos.setText("");
        periodoCorrespondente.setText("");
        // preRequisitos.setSelectedItems(null);
    }
}

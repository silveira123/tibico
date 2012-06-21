package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

public class PagFormularioDisciplina extends GenericForwardComposer {

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
    private Window winFormularioDisciplina;
    private Textbox nomeDisciplina;
    private Intbox cargaHorario, creditos, periodo;
    private Combobox cursoCombo;
    private Listbox listPreRequisitos, listAreaConhecimento;
    private Disciplina obj;
    private Button salvarDisciplina;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        //TODO Fazer a selecao de disciplinas por curso
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);

        List<Disciplina> disciplinas = ctrl.obterDisciplinas();
        if (disciplinas != null) {
            listPreRequisitos.setModel(new ListModelList(disciplinas, true));
        }
        ((ListModelList) listPreRequisitos.getModel()).setMultiple(true);

        List<AreaConhecimento> areaConhecimentos = ctrl.obterAreaConhecimento();
        if (areaConhecimentos != null) {
            listAreaConhecimento.setModel(new ListModelList(areaConhecimentos, true));
        }
        ((ListModelList) listAreaConhecimento.getModel()).setMultiple(true);
    }

    public void onCreate$winFormularioDisciplina() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != CtrlCadastroCurso.SALVAR) {
            obj = (Disciplina) arg.get("obj");
            preencherTela();
            if (MODO == CtrlCadastroCurso.CONSULTAR) {
                this.salvarDisciplina.setVisible(false);
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        
        // retornado a lista de areadeconhecimento
        List<Comboitem> curso = cursoCombo.getItems();
        for (int i = 0; i < curso.size(); i++) {
            // verificando qual a area de conhecimento cadastrado
            if (obj.getCurso().equals(curso.get(i).getValue())) {
                cursoCombo.setSelectedItem(curso.get(i));
            }
        }
        
        nomeDisciplina.setText(obj.getNome());
        cargaHorario.setValue(obj.getCargaHoraria());
        creditos.setValue(obj.getNumCreditos());
        periodo.setValue(obj.getPeriodoCorrespondente());
        
        List<Listitem> listItems = listPreRequisitos.getItems();
        
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getValue().equals(obj)) {
                listItems.remove(i);
            }
        }
        
        //seleciona os que devem ser marcados em prerequisito
        if (obj.getPrerequisito().size() > 0) {
            
            List selects = obj.getPrerequisito();
            
            for (int i = 0; i < listItems.size(); i++) {
                if (selects.contains(listItems.get(i).getValue())) {
                    listPreRequisitos.addItemToSelection(listItems.get(i));
                }
            }
        }
        
        //seleciona os que devem ser marcados em area de conhecimento
        if (obj.getAreaConhecimento().size() > 0) {
            List selects = obj.getAreaConhecimento();

            for (int i = 0; i < listItems.size(); i++) {
                if (selects.contains(listItems.get(i).getValue())) {
                    listAreaConhecimento.addItemToSelection(listItems.get(i));
                }
            }
        }
    }

    private void bloquearTela() {
        nomeDisciplina.setReadonly(true);
        cargaHorario.setReadonly(true);
        creditos.setReadonly(true);
        periodo.setReadonly(true);
        listAreaConhecimento.setDisabled(true);
        listPreRequisitos.setDisabled(true);
    }

    public void onClick$salvarDisciplina(Event event) {

        Disciplina d = null;
        int controle = 0;
        try {
            if (MODO == CtrlCadastroCurso.EDITAR) {

                obj.setNome(nomeDisciplina.getText());
                obj.setCargaHoraria(cargaHorario.getValue());
                obj.setNumCreditos(creditos.getValue());
                obj.setPeriodoCorrespondente(periodo.getValue());
                Curso c = cursoCombo.getSelectedItem().getValue();
                obj.setCurso(c);

                Set auxAC = ((Selectable) listAreaConhecimento.getModel()).getSelection();
                obj.setAreaConhecimento(new ArrayList<AreaConhecimento>(auxAC));

                Set auxP = ((Selectable) listPreRequisitos.getModel()).getSelection();
                obj.setPrerequisito(new ArrayList<Disciplina>(auxP));

                d = ctrl.alterarDisciplina(obj);

                Messagebox.show("Cadastro editado!");

            }
            else {
                ArrayList<Object> list = new ArrayList<Object>();

                list.add(nomeDisciplina.getText());
                list.add(cargaHorario.getValue());
                list.add(creditos.getValue());
                list.add(periodo.getValue());

                Set auxP = ((Selectable) listPreRequisitos.getModel()).getSelection();
                list.add(new ArrayList<Disciplina>(auxP));

                Curso c = cursoCombo.getSelectedItem().getValue();
                list.add(c);

                Set auxAC = ((Selectable) listAreaConhecimento.getModel()).getSelection();
                list.add(new ArrayList<AreaConhecimento>(auxAC));

                d = ctrl.incluirDisciplina(list);

                Messagebox.show("Cadastro feito!");
            }
        }
        catch (Exception e) {
            Messagebox.show("Falha no cadastro feito!");
            System.err.println(e);
        }
        
        limparCampos();
        winFormularioDisciplina.onClose();
        
    }

    public void limparCampos() {
        nomeDisciplina.setText("");
        cargaHorario.setText("");
        creditos.setText("");
        periodo.setText("");
        listPreRequisitos.clearSelection();
        listAreaConhecimento.clearSelection();
    }
}

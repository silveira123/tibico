package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagFormularioDisciplina extends GenericForwardComposer {

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
    private Window winFormularioDisciplina;
    private Textbox nomeDisciplina;
    private Intbox cargaHorario, creditos, periodo;
    private Combobox cursoCombo;
    private Listbox listPreRequisitos, listAreaConhecimento;
    private Disciplina obj;
    private Button salvar;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //Fazer a selecao de disciplinas por curso
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);
        
        List<Disciplina> disciplinas = ctrl.obterDisciplinas();
        if(disciplinas!=null)
        {
            if(obj != null && disciplinas.contains(obj))
            {
                System.out.println("asdasd");
                disciplinas.remove(obj);
            }
                
            listPreRequisitos.setModel(new ListModelList(disciplinas, true));
        }
        ((ListModelList)listPreRequisitos.getModel()).setMultiple(true);
        
        List<AreaConhecimento> areaConhecimentos = ctrl.obterAreaConhecimento();
        if(areaConhecimentos!=null) listAreaConhecimento.setModel(new ListModelList(areaConhecimentos, true));
        ((ListModelList)listAreaConhecimento.getModel()).setMultiple(true);
    }

    public void onCreate$winFormularioDisciplina() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != CtrlCadastroCurso.SALVAR) {
            obj = (Disciplina) arg.get("obj");
            preencherTela();
            if (MODO == CtrlCadastroCurso.CONSULTAR) {
                this.salvar.setVisible(false);
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        nomeDisciplina.setText(obj.getNome());
        cargaHorario.setValue(obj.getCargaHoraria());
        creditos.setValue(obj.getNumCreditos());
        periodo.setValue(obj.getPeriodoCorrespondente());
        
        
        if (obj.getPrerequisito().size() > 0) {
            List<Listitem> listItems = listPreRequisitos.getItems();
            List selects = obj.getPrerequisito();
            
            for (int i = 0; i < listItems.size(); i++) {
                if(selects.contains(listItems.get(i).getValue()))
                    listPreRequisitos.addItemToSelection(listItems.get(i));
            }
        }
        
        if (obj.getAreaConhecimento().size() > 0) {
            List<Listitem> listItems = listAreaConhecimento.getItems();
            List selects = obj.getAreaConhecimento();
            
            for (int i = 0; i < listItems.size(); i++) {
                if(selects.contains(listItems.get(i).getValue()))
                    listAreaConhecimento.addItemToSelection(listItems.get(i));
            }
        }

        // retornado a lista de areadeconhecimento
//        List<Comboitem> a = areaConhecimento.getItems();
//        for (int i = 0; i < a.size(); i++) {
//            // verificando qual a area de conhecimento cadastrado
//            if (a.get(i).getValue() == obj.getAreaConhecimento()) {
//                //listAreaConhecimento.setSelectedItem(a.get(i));
//            }
//        }
    }

    private void bloquearTela() {
        nomeDisciplina.setReadonly(true);
        cargaHorario.setReadonly(true);
        creditos.setReadonly(true);
        periodo.setReadonly(true);
        listAreaConhecimento.setDisabled(true);
        listPreRequisitos.setDisabled(true);
    }
//
//    public void onClick$salvar(Event event) {
//
//        Disciplina d = null;
//        try {
//            if (MODO == ctrl.EDITAR) {
//                obj.setNome(nome.getText());
//                obj.setCargaHoraria(cargaHoraria.getValue());
//                obj.setNumCreditos(numCreditos.getValue());
//                obj.setAreaConhecimento((AreaConhecimento) areaConhecimento.getSelectedItem().getValue());
//                obj.setPeriodoCorrespondente(periodoCorrespondente.getValue());
//                obj.setCurso(objCurso);
//
//                Set aux = ((Selectable) preRequisitos.getModel()).getSelection();
//                obj.setPrerequisito(new ArrayList<Disciplina>(aux));
//
//                d = ctrl.alterarDisciplina(obj);
//                alert("Cadastro editado!");
//            }
//            else {
//                ArrayList<Object> list = new ArrayList<Object>();
//
//                list.add(nome.getText());
//                list.add(cargaHoraria.getValue());
//                list.add(numCreditos.getValue());
//                list.add(periodoCorrespondente.getValue());
//
//                Set aux = ((Selectable) preRequisitos.getModel()).getSelection();
//                list.add(new ArrayList<Disciplina>(aux));
//
//                list.add(objCurso);
//                list.add(areaConhecimento.getSelectedItem().getValue());
//
//                d = ctrl.incluirDisciplina(list);
//                alert("Cadastro feito!");
//
//                limparCampos();
//            }
//        }
//        catch (Exception e) {
//            alert("Falha no cadastro feito!");
//        }
//
//    }
//
    public void limparCampos() {
        nomeDisciplina.setText("");
        cargaHorario.setText("");
        creditos.setText("");
        periodo.setText("");
        listPreRequisitos.clearSelection();
    }
}

package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

public class PagFormularioDisciplina extends GenericForwardComposer {

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
    
    private Window winFormularioDisciplina;
    private Textbox nomeDisciplina;
    private Intbox cargaHoraria, creditos, periodo;
    private Combobox cursoCombo;
    private Listbox listPreRequisitos, listAreaConhecimento;
    private Disciplina obj;
    private Curso obj2;
    private Button salvarDisciplina;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        //TODO Fazer a selecao de disciplinas por curso
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);
        

        cursoCombo.setDisabled(true);

        List<Disciplina> disciplinas = ctrl.obterDisciplinas();
        if (disciplinas != null) {
            listPreRequisitos.setModel(new ListModelList(disciplinas, true));
        }
        ((ListModelList) listPreRequisitos.getModel()).setMultiple(true);

        // Populando o list de AreaConhecimento
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
        else
        {
            obj2 = (Curso) arg.get("obj"); 
            List<Comboitem> curso = cursoCombo.getItems();
            for (int i = 0; i < curso.size(); i++) {
                // verificando qual a area de conhecimento cadastrado
                if (obj2.equals(curso.get(i).getValue())) {
                    cursoCombo.setSelectedItem(curso.get(i));
                }
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
        cargaHoraria.setValue(obj.getCargaHoraria());
        creditos.setValue(obj.getNumCreditos());
        periodo.setValue(obj.getPeriodoCorrespondente());

        List<Listitem> listItemsPreRequisito = listPreRequisitos.getItems();

        //retira o obj da lista das disciplinas prerequisito
        for (int i = 0; i < listItemsPreRequisito.size(); i++) {
            if (listItemsPreRequisito.get(i).getValue().equals(obj)) {
                listItemsPreRequisito.remove(i);
            }
        }

        setSelecionadosList(listPreRequisitos, obj.getPrerequisito());
        setSelecionadosList(listAreaConhecimento, obj.getAreaConhecimento());
    }

    public void setSelecionadosList(Listbox listbox, List selects) {
        ListModel model = listbox.getModel();
        ((Selectable) model).setSelection(selects);
    }

    public ArrayList getSelecionadosList(Listbox listbox) {
        ListModel model = listbox.getModel();
        return new ArrayList(((Selectable) model).getSelection());
    }

    private void bloquearTela() {
        nomeDisciplina.setReadonly(true);
        cargaHoraria.setReadonly(true);
        creditos.setReadonly(true);
        periodo.setReadonly(true);

        List<Listitem> listItemsPreRequisito = listPreRequisitos.getItems();
        listPreRequisitos.setCheckmark(false);
        for (int i = 0; i < listItemsPreRequisito.size(); i++) {
           listItemsPreRequisito.get(i).setDisabled(true);
        }

        List<Listitem> listAreaConhecimentos = listAreaConhecimento.getItems();
        listAreaConhecimento.setCheckmark(false);
        for (int i = 0; i < listAreaConhecimentos.size(); i++) {
           listAreaConhecimentos.get(i).setDisabled(true);
        }
        cursoCombo.setDisabled(true);
    }

    public void onClick$salvarDisciplina(Event event) {

        Disciplina d = null;
        try {
            String msg = valido();
            if(msg.trim().equals("")){
                if (MODO == CtrlCadastroCurso.EDITAR) {

                    obj.setNome(nomeDisciplina.getText());
                    obj.setCargaHoraria(cargaHoraria.getValue());
                    obj.setNumCreditos(creditos.getValue());
                    obj.setPeriodoCorrespondente(periodo.getValue());
                    Curso c = cursoCombo.getSelectedItem().getValue();
                    obj.setCurso(c);
                    ArrayList<Disciplina> auxP = getSelecionadosList(listPreRequisitos);
                    obj.setPrerequisito(auxP);
                    ArrayList<AreaConhecimento> auxAC = getSelecionadosList(listAreaConhecimento);
                    obj.setAreaConhecimento(auxAC);
                    d = ctrl.alterarDisciplina(obj);

                    Messagebox.show("Cadastro editado!");

                }
                else {
                    ArrayList<Object> list = new ArrayList<Object>();

                    list.add(nomeDisciplina.getText());
                    list.add(cargaHoraria.getValue());
                    list.add(creditos.getValue());
                    list.add(periodo.getValue());
                    ArrayList<Disciplina> auxP = getSelecionadosList(listPreRequisitos);
                    list.add(auxP);
                    Curso c = cursoCombo.getSelectedItem().getValue();
                    list.add(c);
                    ArrayList<AreaConhecimento> auxAC = getSelecionadosList(listAreaConhecimento);
                    list.add(auxAC);
                    d = ctrl.incluirDisciplina(list);
                    
                    limparCampos();
                    Messagebox.show("Cadastro feito!");
                }
                winFormularioDisciplina.onClose();
            }
            else Messagebox.show(msg, "", 0, Messagebox.EXCLAMATION);
        }
        catch (Exception e) {
            Messagebox.show("Falha no cadastro feito!");
            System.err.println(e);
        }

        winFormularioDisciplina.onClose();
        limparCampos();

    }

    public void onClick$cancelarDisciplina(Event event) {
        winFormularioDisciplina.onClose();
    }

    public void limparCampos() {
        nomeDisciplina.setText("");
        cargaHoraria.setText("");
        creditos.setText("");
        periodo.setText("");
        listPreRequisitos.clearSelection();
        listAreaConhecimento.clearSelection();
    }
    
    private String valido() {
        String msg = "";
        
        if (nomeDisciplina.getText().trim().equals(""))
            msg += "- Nome\n";
        if (getSelecionadosList(listAreaConhecimento).isEmpty())
            msg += "- Área de Conhecimento\n";
        if (cargaHoraria.getValue() == null)
            msg += "- Carga Horária\n";
        if (creditos.getValue() == null)
            msg += "- Carga Horária\n";
        if (periodo.getValue() == null)
            msg += "- Periodo Correspondente\n";
        
        if(!msg.trim().equals(""))
            msg = "Informe:\n"+msg;
        return msg;
    }
}

package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCursoDisplina;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.academico.cdp.AreaConhecimento;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagFormularioDisciplina extends GenericForwardComposer {

    private CtrlCadastroCursoDisplina ctrl = CtrlCadastroCursoDisplina.getInstance();
    private Window winCadastro;
    private Textbox nome;
    private Intbox cargaHoraria, numCreditos, periodoCorrespondente;
    private Combobox areaConhecimento;
    private Listbox preRequisitos;
    private Disciplina obj;
    private Curso objCurso;
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
    }

    public void onCreate$winCadastro() {
        MODO = (Integer) arg.get("tipo");
        objCurso = (Curso) arg.get("curso");

        if (MODO != ctrl.SALVAR) {

            obj = (Disciplina) arg.get("obj");
            preencherTela();
            if (MODO == ctrl.CONSULTAR) {
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
            preRequisitos.setSelectedItems(new HashSet(obj.getPrerequisito()));
        }

        List<Comboitem> a = areaConhecimento.getItems(); // retornado a lista de areadeconhecimento
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getValue() == obj.getAreaConhecimento()) // verificando qual a area de conhecimento cadastrado
            {
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

    public void onClick$Salvar(Event event) {

        Disciplina d = null;
        try {
            if (MODO == ctrl.EDITAR) {
                obj.setNome(nome.getText());
                obj.setCargaHoraria(cargaHoraria.getValue());
                obj.setNumCreditos(numCreditos.getValue());
                obj.setAreaConhecimento((AreaConhecimento) areaConhecimento.getSelectedItem().getValue());
                obj.setPeriodoCorrespondente(periodoCorrespondente.getValue());
                obj.setCurso(objCurso);
                
                //TODO fazer a conversao de set listitem para list disciplina
//                if (preRequisitos.getSelectedCount() != 0) {
//                    (preRequisitos.getSelectedItems());
//                } else {
//                    list.add(null);
//                }

                d = ctrl.alterarDisciplina(obj);
                alert("Cadastro editado!");
            } else {
                ArrayList<Object> list = new ArrayList<Object>();

                list.add(nome.getText());
                list.add(cargaHoraria.getValue());
                list.add(numCreditos.getValue());
                list.add(periodoCorrespondente.getValue());
                if (preRequisitos.getSelectedCount() != 0) {
                    list.add(preRequisitos.getSelectedItems());
                } else {
                    list.add(null);
                }

                list.add(objCurso);
                list.add(areaConhecimento.getSelectedItem().getValue());

                d = ctrl.incluirDisciplina(list);
                alert("Cadastro feito!");
                
                limparCampos();
            }
        } catch (Exception e) {
            alert("Falha no cadastro feito!");
        }

    }

    public void onClose$winCadastro(Event event) {
        ctrl.redirectPag("/pageventosdisciplina.zul");
    }

    public void limparCampos() {
        nome.setText("");
        cargaHoraria.setText("");
        numCreditos.setText("");
        periodoCorrespondente.setText("");
        // preRequisitos.setSelectedItems(null);
    }
}

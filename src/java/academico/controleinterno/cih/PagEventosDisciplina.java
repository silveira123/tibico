package academico.controleinterno.cih;


import academico.controleinterno.cci.CtrlCadastroCursoDisplina;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;


public class PagEventosDisciplina extends GenericForwardComposer {
    private CtrlCadastroCursoDisplina ctrl = CtrlCadastroCursoDisplina.getInstance();
    
    private Window winDadosDisciplina;
    private Listbox listbox;
    //private Listitem itens;
    private ListModelList list; // the model of the listbox
    private Curso curso;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        curso = (Curso) arg.get("obj");
        
        List<Disciplina> listaDisciplinas = ctrl.obterDisciplinas(curso);
        List<Disciplina> data = new ArrayList<Disciplina>();

        if(listaDisciplinas != null)
        {
            for (int i = 0; i < listaDisciplinas.size(); i++) {
                data.add(listaDisciplinas.get(i));
            }
        }
        listbox.setModel(new ListModelList(data, true));
    }

    public void onClick$Excluir(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                ctrl.apagarDisciplina((Disciplina) listitem.getValue());
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                alert("Não foi possivel excluir a disciplina");
            }
        }
        else{
            alert("Selecione uma disciplina");
        }
        
    }

    public void onClick$Incluir(Event event) {
        ctrl.abrirIncluirDisciplina(curso);
    }

    public void onClick$Editar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirEditarDisciplina((Disciplina) listitem.getValue(), curso);
        }
    }
    
    public void onClick$Consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirConsultarDisciplina((Disciplina) listitem.getValue(), curso);
        }
    }

}
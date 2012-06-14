package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCursoDisplina;
import academico.controleinterno.cdp.Curso;
import academico.util.persistencia.ObjetoPersistente;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

public class PagEventosCurso extends GenericForwardComposer {

    private CtrlCadastroCursoDisplina ctrl = CtrlCadastroCursoDisplina.getInstance();
    private Window winDadosCurso;
    private Listbox listbox;
    private ListModelList list;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Curso> listaCursos = ctrl.obterCursos();
        List<Curso> data = new ArrayList<Curso>();

        if (listaCursos != null) {
            for (int i = 0; i < listaCursos.size(); i++) {
                data.add(listaCursos.get(i));
            }
        }
        listbox.setModel(new ListModelList(data, true));
    }

    public void onClick$Excluir(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                ctrl.apagarCurso((Curso) listitem.getValue());
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                alert("Não foi possivel excluir o curso");
            }
        } else {
            alert("Selecione um curso");
        }

    }

    public void onClick$Incluir(Event event) {
        ctrl.abrirIncluirCurso();
    }

    public void onClick$Editar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirEditarCurso((Curso) listitem.getValue());
        }
    }

    public void onClick$Consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirConsultarCurso((Curso) listitem.getValue());
        }
    }
    
    public void onClick$addDisciplina(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirEventosDisciplina((Curso) listitem.getValue());
        }
    }
    
}
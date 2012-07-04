package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import java.util.List;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

public class PagEventosCurso extends GenericForwardComposer {

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
    private Window winEventosCurso;
    private Listbox listCurso;
   
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        List<Curso> cursos = ctrl.obterCursos();
        if (cursos != null) {
            for (int i = 0; i < cursos.size(); i++) {
                Curso c = cursos.get(i);
                Listitem linha = new Listitem(cursos.get(i).toString(), c);

                linha.appendChild(new Listcell(cursos.get(i).getGrandeAreaConhecimento().toString()));
                linha.appendChild(new Listcell(cursos.get(i).getDuracao() + ""));

                linha.setParent(listCurso);
            }
        }
    }

    public void onClick$excluirCurso(Event event) {
        Listitem listitem = listCurso.getSelectedItem();
        if (listitem != null) {
            try {
                Curso c = listitem.getValue();
                ctrl.apagarCurso(c);
                listCurso.removeItemAt(listCurso.getSelectedIndex());
            }
            catch (Exception e) {
                Messagebox.show("Não foi possivel excluir o curso");
            }
        }
        else {
            Messagebox.show("Selecione um curso");
        }
    }

    public void onClick$incluirCurso(Event event) {
        ctrl.abrirIncluirCurso();
    }

    public void onClick$alterarCurso(Event event) {
        Listitem listitem = listCurso.getSelectedItem();
        if (listitem != null) {
            Curso c = listitem.getValue();
            ctrl.abrirEditarCurso(c);
        }
    }

    public void onClick$consultarCurso(Event event) {
        Listitem listitem = listCurso.getSelectedItem();
        if (listitem != null) {
            Curso c = listitem.getValue();
            ctrl.abrirConsultarCurso(c);
        }
    }
    
    
}
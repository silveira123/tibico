package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagEventosDisciplina extends GenericForwardComposer {

    private CtrlCadastroCurso ctrl = CtrlCadastroCurso.getInstance();
    private Window winEventosDisciplina;
    private Listbox listDisciplina;
    private Curso curso;
    private Combobox cursoCombo;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);

        List<Disciplina> disciplinas = ctrl.obterDisciplinas();
        if (disciplinas != null) {
            for (int i = 0; i < disciplinas.size(); i++) {
                Disciplina c = disciplinas.get(i);
                Listitem linha = new Listitem(disciplinas.get(i).toString(), c);

                linha.appendChild(new Listcell(disciplinas.get(i).getCurso().toString()));
                linha.appendChild(new Listcell(disciplinas.get(i).getCargaHoraria() + ""));

                linha.setParent(listDisciplina);
            }
        }
    }

    public void onClick$excluirDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            try {
                Disciplina d = listitem.getValue();
                ctrl.apagarDisciplina(d);
                listDisciplina.removeItemAt(listDisciplina.getSelectedIndex());
            }
            catch (Exception e) {
                Messagebox.show("Não foi possivel excluir a disciplina");
            }
        }
        else {
            Messagebox.show("Selecione uma disciplina");
        }
    }

    public void onClick$incluirDisciplina(Event event) {
        ctrl.abrirIncluirDisciplina();      
    }

    public void onClick$alterarDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            Disciplina d = listitem.getValue();
            ctrl.abrirEditarDisciplina(d); 
        }
    }
    
    public void onClick$consultarDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            Disciplina d = listitem.getValue();
            ctrl.abrirConsultarDisciplina(d);  
        }
    }
}
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.util.Exceptions.AcademicoException;
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
        ctrl.setPagEventosDisciplina(this);
        List<Curso> vetCurso = ctrl.obterCursos();
        cursoCombo.setModel(new ListModelList(vetCurso, true));
        cursoCombo.setReadonly(true);
    }

    public void onSelect$cursoCombo(Event event) throws AcademicoException
    {
        Curso select = (Curso) cursoCombo.getSelectedItem().getValue();
        
        //limpando o listbox antes de add as novas linhas
        for (int i = 0; i < listDisciplina.getItemCount(); i++) {
            listDisciplina.removeItemAt(0);
        }
        
        List<Disciplina> disciplinas = ctrl.obterDisciplinas(select);
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
    
    public void addDisciplina(Disciplina c)
    {
        Listitem linha = new Listitem(c.toString(), c);
        linha.appendChild(new Listcell(c.getCurso().toString()));
        linha.appendChild(new Listcell(c.getCargaHoraria() + ""));
        linha.setParent(listDisciplina);
    }
    
    public void refreshDisciplina(Disciplina c)
    {
        for (int i = 0; i < listDisciplina.getItemCount(); i++) {
            if(listDisciplina.getItemAtIndex(i).getValue() == c)
            {
                listDisciplina.getItemAtIndex(i).getChildren().clear();      
                listDisciplina.getItemAtIndex(i).appendChild(new Listcell(c.toString()));
                listDisciplina.getItemAtIndex(i).appendChild(new Listcell(c.getCurso().toString()));
                listDisciplina.getItemAtIndex(i).appendChild(new Listcell(c.getCargaHoraria() + ""));
                break;
            }
        }
    }
    public void onClick$excluirDisciplina(Event event) {
        Listitem listitem = listDisciplina.getSelectedItem();
        if (listitem != null) {
            try {
                Disciplina d = listitem.getValue();
                if(ctrl.apagarDisciplina(d))
                    listDisciplina.removeItemAt(listDisciplina.getSelectedIndex());
                else
                   Messagebox.show("Não foi possivel excluir a disciplina. A disciplina possui pré-requisito ou Está alocado em uma Turma."); 
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
        Comboitem comboitem = cursoCombo.getSelectedItem();
        if (comboitem != null) {
            Curso c = comboitem.getValue();
            ctrl.abrirIncluirDisciplina(c);    
        }
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
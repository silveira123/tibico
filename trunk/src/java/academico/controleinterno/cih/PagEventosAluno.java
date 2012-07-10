package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * <<descrição da Classe>> 
 * 
 * @author Gabriel Quézid 
 * @author Rodrigo Maia
 * @version 0.1
 * @see
 */
public class PagEventosAluno extends GenericForwardComposer {
    
    private CtrlPessoa ctrl = CtrlPessoa.getInstance();  
    private CtrlCadastroCurso ctrlCurso = CtrlCadastroCurso.getInstance();
    private Window winDadosAluno;
    private Listbox listAluno;
    private ListModelList list; // the model of the listProfessor
    private Combobox curso;
    private Aluno a;
    private Curso select;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosAluno(this);
        
        a = (Aluno) arg.get("obj");
        
        List<Curso> cursos = ctrlCurso.obterCursos();
        curso.setModel(new ListModelList(cursos, true));
        
    }

    public void onSelect$curso(Event event) throws AcademicoException
    {
        select = (Curso) curso.getSelectedItem().getValue();
        
        List<Aluno> listaAlunos = ctrl.obterAlunos();

        if (listaAlunos != null) {
            for (int i = 0; i < listaAlunos.size(); i++) {
                Aluno a = listaAlunos.get(i);
                if(a.getCurso() == select)
                {
                    Listitem linha = new Listitem(listaAlunos.get(i).toString(), a);

                    linha.appendChild(new Listcell(listaAlunos.get(i).getCurso().toString()));

                    linha.setParent(listAluno);
                }
            }
        }
    }
    
    public void addAluno(Aluno a)
    {
        Listitem linha = new Listitem(a.toString(), a);
        linha.appendChild(new Listcell(a.getCurso().toString()));
        linha.setParent(listAluno);
    }
    
    public void refreshAluno(Aluno a)
    {
        for (int i = 0; i < listAluno.getItemCount(); i++) {
            if(listAluno.getItemAtIndex(i).getValue() == a)
            {
                listAluno.getItemAtIndex(i).getChildren().clear();      
                listAluno.getItemAtIndex(i).appendChild(new Listcell(a.toString()));
                listAluno.getItemAtIndex(i).appendChild(new Listcell(a.getCurso().toString()));
                break;
            }
        }
    }
    
    public void onClick$excluirAluno(Event event) {
        Listitem listitem = listAluno.getSelectedItem();
        if (listitem != null) {
            try {
                ctrl.apagarAluno((Aluno) listitem.getValue());
                listAluno.removeItemAt(listAluno.getSelectedIndex());
            } catch (Exception e) {
                Messagebox.show("Não foi possivel excluir o aluno");
            }
        }
        else{
            Messagebox.show("Selecione um aluno");
        }
        
    }

    public void onClick$incluirAluno(Event event) {
        ctrl.abrirIncluirAluno(a,select);      
    }

    public void onClick$alterarAluno(Event event) {
        Listitem listitem = listAluno.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirEditarAluno((Aluno) listitem.getValue()); 
        }
    }
    
    public void onClick$consultarAluno(Event event) {
        Listitem listitem = listAluno.getSelectedItem();
        if (listitem != null) {
            ctrl.abrirConsultarAluno((Aluno) listitem.getValue());  
        }
    }
}

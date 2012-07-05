package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
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
    private Window winDadosAluno;
    private Listbox listAluno;
    private ListModelList list; // the model of the listProfessor
    private Aluno a;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        a = (Aluno) arg.get("obj");
        
        List<Aluno> listaAlunos = ctrl.obterAlunos();
        List<Aluno> data = new ArrayList<Aluno>();

        if (listaAlunos != null) {
            for (int i = 0; i < listaAlunos.size(); i++) {
                Aluno a = listaAlunos.get(i);
                Listitem linha = new Listitem(listaAlunos.get(i).toString(), a);

                linha.appendChild(new Listcell(listaAlunos.get(i).getCurso().toString()));

                linha.setParent(listAluno);
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
        ctrl.abrirIncluirAluno(a);      
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

package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagFormularioMatricula extends GenericForwardComposer {

    private CtrlLetivo ctrlLetivo = CtrlLetivo.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private CtrlMatricula ctrlMatricula = CtrlMatricula.getInstance();
    
    private Window winFormularioMatricula;
    private Listbox left;
    private Listbox right;
    private Textbox nomeAluno;
    private Aluno obj;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        nomeAluno.setReadonly(true);
    }

    public void onCreate$winFormularioMatricula() {
        obj = (Aluno) arg.get("aluno");
        if (obj != null) {
            nomeAluno.setValue(obj.toString());
            List<Turma> turma = ctrlMatricula.obterTurmasPossiveis(obj);
            left.setModel(new ListModelList(turma, true));
            ((ListModelList) left.getModel()).setMultiple(true);
        }
    }

    public void onClick$cancelarMatricula(Event event) {
        winFormularioMatricula.onClose();
    }

    public void onClick$salvarMatricula(Event event) {
        List<Listitem> listItems = right.getItems();
        
        for (int i = 0; i < listItems.size(); i++) {
            ArrayList<Object> listMT = new ArrayList<Object>();
            listMT.add(obj);
            Turma t = (Turma) listItems.get(i).getValue();
            listMT.add(t);
            try {
                ctrlMatricula.efetuarMatricula(listMT);
            }
            catch (AcademicoException ex) {
                Messagebox.show("Erro ao matricular! " + obj);
            }
        }
        winFormularioMatricula.onClose();
    }
}
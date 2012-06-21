package academico.controleinterno.cih;


import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Turma;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;


public class PagEventosTurma extends GenericForwardComposer {
    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winFormularioTurma;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listbox;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Turma> listaTurma = ctrl.obterTurma(); 
        for (int i = 0; i < listaTurma.size(); i++) {
            Turma t = listaTurma.get(i);
            Listitem linha = new Listitem(t.getDisciplina().toString(), t);
                
            linha.appendChild(new Listcell(t.getCalendario().toString()));
           // linha.appendChild(new Listcell(t.));
            //TODO fazer essa ultima linha para trazer o infeliz do professor
            linha.setParent(listbox);
        }
    }

    public void onClick$excluir(Event event) { 
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Turma t = listitem.getValue();
                ctrl.apagarTurma(t);
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                alert("Não foi possivel excluir a turma");
            }
        } else {
            alert("Selecione uma turma");
        }
    }

    public void onClick$incluir(Event event) {
        ctrl.abrirIncluirTurma();
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Turma t = listitem.getValue();
            ctrl.abrirEditarTurma(t);
        }
    }
    
    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Turma t = listitem.getValue();
            ctrl.abrirConsultarTurma(t);
        }
    }
}
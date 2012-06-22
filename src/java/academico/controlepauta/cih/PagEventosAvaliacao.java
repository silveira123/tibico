package academico.controlepauta.cih;


import academico.controleinterno.cih.*;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cdp.Avaliacao;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;


public class PagEventosAvaliacao extends GenericForwardComposer {
    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlLetivo ctrlTurma = CtrlLetivo.getInstance();
    private Window winEventosAvaliacao;
    private Combobox nome;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listbox;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Turma> listaTurma = ctrlTurma.obterTurma();
        nome.setModel(new ListModelList(listaTurma, true));
        /*List<Avaliacao> listaAvaliacao = ctrl.obterAvaliacoes(); 
        for (int i = 0; i < listaAvaliacao.size(); i++) {
            Avaliacao a = listaAvaliacao.get(i);
            Listitem linha = new Listitem(a.toString(), a);
                
            linha.appendChild(new Listcell(a + ""));
            linha.appendChild(new Listcell(a.getPeso() + ""));
            
            linha.setParent(listbox);
        }*/
    }

    public void onClick$excluir(Event event) { 
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Avaliacao c = listitem.getValue();
                ctrl.apagarAvaliacao(c);
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                alert("Não foi possivel excluir a avaliação");
            }
        } else {
            alert("Selecione uma avaliação");
        }
    }

    public void onClick$incluir(Event event) {
        Comboitem comboitem = nome.getSelectedItem();
        if(comboitem != null)
        {
            Turma t = comboitem.getValue();
            ctrl.abrirIncluirAvaliacao(t);
        }
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirEditarAvaliacao(c);
        }
    }
    
    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirConsultarAvaliacao(c);
        }
    }
}
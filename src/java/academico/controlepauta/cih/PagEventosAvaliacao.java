package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cdp.Turma;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cdp.Avaliacao;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Menuitem inserirPontuacao;
    private Listbox listbox;
    private Professor obj;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosAvaliacao(this);
		
        List<Turma> listaTurma = new ArrayList<Turma>();
        obj = (Professor) arg.get("professor");
        if (obj != null) {
            listaTurma = ctrlTurma.obterTurma(obj);
        } else {
            listaTurma = ctrlTurma.obterTurma();
        }
        nome.setModel(new ListModelList(listaTurma, true));
        nome.setReadonly(true);
    }

    public void onSelect$nome(Event event) {
        try {
            Turma t = nome.getSelectedItem().getValue();
            List<Avaliacao> listaAvaliacao = ctrl.obterAvaliacoes();

            while (listbox.getItemCount() > 0) {
                listbox.removeItemAt(0);
            }

            for (int i = 0; i < listaAvaliacao.size(); i++) {
                Avaliacao a = listaAvaliacao.get(i);
                if (a.getTurma() == t) {
                    Listitem linha = new Listitem(a.toString(), a);
                    linha.appendChild(new Listcell(a.getPeso() + ""));

                    linha.setParent(listbox);
                }
            }
        } catch (AcademicoException ex) {
            Logger.getLogger(PagEventosAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void addAvaliacao(Avaliacao a)
    {
        Listitem linha = new Listitem(a.toString(), a);
        linha.appendChild(new Listcell(a.getPeso() + ""));
        linha.setParent(listbox);
    }
    
    public void refreshAvaliacao(Avaliacao a)
    {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if(listbox.getItemAtIndex(i).getValue() == a)
            {
                listbox.getItemAtIndex(i).getChildren().clear();      
                listbox.getItemAtIndex(i).appendChild(new Listcell(a.toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(a.getPeso() + ""));
                break;
            }
        }
    }
    
   
    public void onClick$excluir(Event event) {

        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Avaliacao c = listitem.getValue();
                ctrl.apagarAvaliacao(c);
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                Messagebox.show("Não foi possivel excluir a avaliação");
            }
        } else {
            Messagebox.show("Selecione uma avaliação");
        }
    }

    public void onClick$incluir(Event event) {
        Comboitem comboitem = nome.getSelectedItem();
        if (comboitem != null) {
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

    public void onClick$inserirPontuacao(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Avaliacao c = listitem.getValue();
            ctrl.abrirRegistroPontuacao(c);
        }
    }
}
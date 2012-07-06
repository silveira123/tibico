package academico.controleinterno.cih;


import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;


public class PagEventosCalendario extends GenericForwardComposer {
    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winEventosCalendario;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listbox;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosCalendario(this);
        List<Calendario> listaCalendario = ctrl.obterCalendario(); 
        for (int i = 0; i < listaCalendario.size(); i++) {
            Calendario c = listaCalendario.get(i);
            Listitem linha = new Listitem(c.toString(), c);
                
            linha.appendChild(new Listcell(c.getDataInicioCA().getTime().getDate() + "/" +
                    (c.getDataInicioCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));
            linha.appendChild(new Listcell(c.getDataFimCA().getTime().getDate() + "/" +
                    (c.getDataFimCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));

            linha.setParent(listbox);
        }
    }

    public void addCalendario(Calendario c)
    {
        Listitem linha = new Listitem(c.toString(), c);
        linha.appendChild(new Listcell(c.getDataInicioCA().getTime().getDate() + "/" +
                    (c.getDataInicioCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));
        linha.appendChild(new Listcell(c.getDataFimCA().getTime().getDate() + "/" +
                    (c.getDataFimCA().getTime().getMonth() + 1) + "/" + 
                    (c.getDataFimCA().getTime().getYear() + 1900)));
        linha.setParent(listbox);
    }
    
    public void refreshCalendario(Calendario c)
    {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            if(listbox.getItemAtIndex(i).getValue() == c)
            {
                listbox.getItemAtIndex(i).getChildren().clear();      
                listbox.getItemAtIndex(i).appendChild(new Listcell(c.toString()));
                listbox.getItemAtIndex(i).appendChild(new Listcell(c.getDataInicioCA().getTime().getDate() + "/" +
                            (c.getDataInicioCA().getTime().getMonth() + 1) + "/" + 
                            (c.getDataFimCA().getTime().getYear() + 1900)));
                listbox.getItemAtIndex(i).appendChild(new Listcell(c.getDataFimCA().getTime().getDate() + "/" +
                            (c.getDataFimCA().getTime().getMonth() + 1) + "/" + 
                            (c.getDataFimCA().getTime().getYear() + 1900)));
                break;
            }
        }
    }
    public void onClick$excluir(Event event) { 
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            try {
                Calendario c = listitem.getValue();
                ctrl.apagarCalendario(c);
                listbox.removeItemAt(listbox.getSelectedIndex());
            } catch (Exception e) {
                Messagebox.show("Não foi possivel excluir o calendario");
            }
        } else {
            Messagebox.show("Selecione um calendario");
        }
    }

    public void onClick$incluir(Event event) {
        ctrl.abrirIncluirCalendario();
    }

    public void onClick$alterar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Calendario c = listitem.getValue();
            ctrl.abrirEditarCalendario(c);
        }
    }
    
    public void onClick$consultar(Event event) {
        Listitem listitem = listbox.getSelectedItem();
        if (listitem != null) {
            Calendario c = listitem.getValue();
            ctrl.abrirConsultarCalendario(c);
        }
    }
}
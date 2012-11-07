/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Sala;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 *
 * @author gmiranda
 */
public class PagEventosSala extends GenericForwardComposer {

    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winEventosSala;
    private Menuitem incluir;
    private Menuitem excluir;
    private Menuitem consultar;
    private Menuitem alterar;
    private Listbox listSalas;
    private Div boxInformacao;
    private Label msg;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ctrl.setPagEventosSala(this);
        while (listSalas.getItemCount() > 0) {
            listSalas.removeItemAt(0);
        }

        List<Sala> salas = ctrl.obterSala();
        if (salas != null) {

            for (int i = 0; i < salas.size(); i++) {
                Sala s = salas.get(i);
                Listitem linha = new Listitem(salas.get(i).toString(), s);

                linha.appendChild(new Listcell(salas.get(i).getNumBloco().toString()));
                linha.appendChild(new Listcell(salas.get(i).getNumSala().toString()));


                String equip = "";
                for (int j = 0; j < salas.get(i).getEquipamentoSala().size() - 1; j++) {
                    equip += salas.get(i).getEquipamentoSala().get(j).toString() + ", ";
                }
                equip += salas.get(i).getEquipamentoSala().get(salas.get(i).getEquipamentoSala().size() - 1).toString() + ".";
                linha.appendChild(new Listcell(equip));
                linha.setParent(listSalas);
            }
        }
    }

    public void onCreate$winEventosSala(Event event) {
        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winEventosSala.detach();
        }
    }

    public void addSala(Sala s) {
        Listitem linha = new Listitem(s.toString(),s);

        linha.appendChild(new Listcell(s.getNumBloco().toString()));
        linha.appendChild(new Listcell(s.getNumSala().toString()));


        String equip = "";
        for (int j = 0; j < s.getEquipamentoSala().size() - 1; j++) {
            equip += s.getEquipamentoSala().get(j).toString() + ", ";
        }
        equip += s.getEquipamentoSala().get(s.getEquipamentoSala().size() - 1).toString() + ".";
        linha.appendChild(new Listcell(equip));
        linha.setParent(listSalas);
    }

    public void refreshSala(Sala s) {
        for (int i = 0; i < listSalas.getItemCount(); i++) {
            if (listSalas.getItemAtIndex(i).getValue() == s) {
                listSalas.getItemAtIndex(i).getChildren().clear();
                listSalas.getItemAtIndex(i).appendChild(new Listcell(s.toString()));
                listSalas.getItemAtIndex(i).appendChild(new Listcell(s.getNumBloco().toString()));
                listSalas.getItemAtIndex(i).appendChild(new Listcell(s.getNumSala().toString()));
                String equip = "";
                for (int j = 0; j < s.getEquipamentoSala().size() - 1; j++) {
                    equip += s.getEquipamentoSala().get(j).toString() + ", ";
                }
                equip += s.getEquipamentoSala().get(s.getEquipamentoSala().size() - 1).toString() + ".";
                listSalas.getItemAtIndex(i).appendChild(new Listcell(equip));
                break;
            }
        }
    }
    
    public void onClick$excluir(Event event) {
        Listitem listitem = listSalas.getSelectedItem();
        if (listitem != null) {
            try {
                Sala s = listitem.getValue();
                if (ctrl.apagarSala(s)) {
                    listSalas.removeItemAt(listSalas.getSelectedIndex());
                    setMensagemAviso("success", "Sala excluida com sucesso");
                }
            }
            catch (Exception e) {
                setMensagemAviso("error", "Não foi possivel excluir a sala");
            }
        }
        else {
            setMensagemAviso("info", "Selecione uma sala");
        }
    }
    
    public void onClick$incluir(Event event) {
            ctrl.abrirIncluirSala();  
    }
    
    public void onClick$alterar(Event event) {
        Listitem listitem = listSalas.getSelectedItem();
        if (listitem != null) {
            Sala s = listitem.getValue();
            ctrl.abrirEditarSala(s);
            refreshSala(s);
        }
    }
    
    public void onClick$consultar(Event event) {
        Listitem listitem = listSalas.getSelectedItem();
        if (listitem != null) {
            Sala s = listitem.getValue();
            ctrl.abrirConsultarSala(s);
        }
    }
    
    public void setMensagemAviso(String tipo, String mensagem) {
        boxInformacao.setClass(tipo);
        boxInformacao.setVisible(true);
        msg.setValue(mensagem);
    }
    
    public void onClick$boxInformacao(Event event) {
        boxInformacao.setVisible(false);
    }
}

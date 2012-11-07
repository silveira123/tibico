/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Equipamento;
import academico.controleinterno.cdp.Sala;
import academico.util.Exceptions.AcademicoException;
import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

/**
 *
 * @author gmiranda
 */
public class PagFormularioSala extends GenericForwardComposer {

    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winFormularioSala;
    private Intbox numBloco;
    private Intbox numSala;
    private Listbox listEquipamentos;
    private Sala obj;
    private Button salvar;
    private Button voltar;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        List<Equipamento> listaEquipamento = ctrl.obterEquipamentos();
        if (listaEquipamento != null) {
            listEquipamentos.setModel(new ListModelList(listaEquipamento, true));
        }
        ((ListModelList) listEquipamentos.getModel()).setMultiple(true);
    }

    public void onCreate$winFormularioSala() throws AcademicoException {

        //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
        if (Executions.getCurrent().getSession().getAttribute("usuario") == null) {
            Executions.sendRedirect("/");
            winFormularioSala.detach();
        } else {
            MODO = (Integer) arg.get("tipo");

            if (MODO != CtrlLetivo.SALVAR) {
                obj = (Sala) arg.get("obj");
                preencherTela();
                if (MODO == CtrlLetivo.CONSULTAR) {
                    this.salvar.setVisible(false);
                    bloquearTela();
                }
            } else {
                numSala.setDisabled(false);
                numBloco.setDisabled(false);
                numSala.setVisible(true);
                numBloco.setVisible(true);
                
                List<Equipamento> listaEquipamento = ctrl.obterEquipamentos();
                if (listaEquipamento != null) {
                    listEquipamentos.setModel(new ListModelList(listaEquipamento, true));
                }
                ((ListModelList) listEquipamentos.getModel()).setMultiple(true);
            }
        }
    }

    private void preencherTela() throws AcademicoException {

        numBloco.setValue(obj.getNumBloco());
        numSala.setValue(obj.getNumSala());

        List<Equipamento> equipamentos = ctrl.obterEquipamentos();
        if (equipamentos != null) {
            listEquipamentos.setModel(new ListModelList(equipamentos, true));
        }
        ((ListModelList) listEquipamentos.getModel()).setMultiple(true);


        if (listEquipamentos.getModel() != null) {
            setSelecionadosList(listEquipamentos, obj.getEquipamentoSala());
        }
    }

    public void setSelecionadosList(Listbox listbox, List selects) {
        ListModel model = listbox.getModel();
        ((Selectable) model).setSelection(selects);
    }

    public ArrayList getSelecionadosList(Listbox listbox) {
        ListModel model = listbox.getModel();
        return new ArrayList(((Selectable) model).getSelection());
    }

    private void bloquearTela() {
        numBloco.setDisabled(true);
        numSala.setDisabled(true);


        List<Listitem> equipamentos = listEquipamentos.getItems();
        listEquipamentos.setCheckmark(false);
        for (int i = 0; i < equipamentos.size(); i++) {
            equipamentos.get(i).setDisabled(true);
        }


    }
    public void onClick$salvar(Event event) throws AcademicoException {

        Sala s = null;
        String msg = valido();
        if (msg.trim().equals("")) {
            if (MODO == CtrlLetivo.EDITAR) {

                obj.setNumBloco(numBloco.getValue());
                obj.setNumSala(numSala.getValue());
                List<Equipamento> auxE = getSelecionadosList(listEquipamentos);
                obj.setEquipamentoSala(auxE);
                s = ctrl.alterarSala(obj);

            }
            else {
                ArrayList<Object> list = new ArrayList<Object>();

                list.add(numBloco.getValue());
                list.add(numSala.getValue());
                ArrayList<Equipamento> auxE = getSelecionadosList(listEquipamentos);
                list.add(auxE);
                s = ctrl.incluirSala(list);

                limparCampos();
            }
            winFormularioSala.onClose();
        }
        else {
            Messagebox.show(msg, "Informe", 0, Messagebox.EXCLAMATION);
        }
    }
    private String valido() {
        String msg = "";

        if (numSala.getValue()==null||numSala.getValue()<0||numSala.getValue()>100) {
            msg += "- Número Sala\n";
        }
        if (numBloco.getValue()==null||numBloco.getValue()<0||numBloco.getValue()>100) {
            msg += "- Número Bloco\n";
        }
        if (getSelecionadosList(listEquipamentos).isEmpty()) {
            msg += "- Equipamentos\n";
        }
        

        return msg;
    }
    public void limparCampos() {
        numBloco.setText("");
        numSala.setText("");
        listEquipamentos.clearSelection();
    }
    public void onClick$voltar(Event event) {
        winFormularioSala.onClose();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Sala;
import academico.controleinterno.cdp.Turma;
import academico.util.horario.cdp.Horario;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 *
 * @author gmiranda
 */
public class PagAlocarHorarioSala extends GenericForwardComposer{
    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private Window winAlocarHorarioSala;
    private Listbox listBox;
   private Turma obj;
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Turma) arg.get("turma");
        List<Sala> listSalas = ctrl.obterSala();
        List<Horario> listHorario = obj.getHorario();
        for (Horario horario : listHorario) {
            Listitem listItem = new Listitem();
            Listcell listCell = new Listcell();
            Cell cell = new Cell();
            cell.appendChild(new Label(horario.toString()));
            Combobox c = new Combobox();
            c.setModel(new ListModelList(listSalas, true));
            cell.appendChild(c);
            listCell.appendChild(cell);
            listItem.appendChild(listCell);
        }
        

    }
}

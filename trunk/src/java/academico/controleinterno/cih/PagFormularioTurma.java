package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagFormularioTurma extends GenericForwardComposer {

    private CtrlLetivo ctrl = CtrlLetivo.getInstance();
    private CtrlCadastroCurso ctrlCurso = CtrlCadastroCurso.getInstance();
    private Window winFormularioCalendario;
    private Textbox identificador, duracao;
    private Datebox dataInicioCA, dataFimCA, dataInicioPL, dataFimPL, dataInicioPM, dataFimPM;
    private Combobox curso;
    private Calendario obj;
    private Button salvar, cancelar;
    private int MODO;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Curso> listaCurso = ctrlCurso.obterCursos();
        curso.setModel(new ListModelList(listaCurso, true));
        
        
    }

    public void onCreate$winFormularioCalendario() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != ctrl.SALVAR) {
            obj = (Calendario) arg.get("obj");
            preencherTela();
            if (MODO == ctrl.CONSULTAR) {
                this.salvar.setVisible(false);
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        List<Comboitem> a = curso.getItems();
        for (int i = 0; i < a.size(); i++) {
            // verificando qual a area de conhecimento cadastrado
            if (a.get(i).getValue() == obj.getCurso()) {
                curso.setSelectedItem(a.get(i));
            }
        }
        
        identificador.setValue(obj.getIdentificador());
        duracao.setValue(obj.getDuracao());
        dataInicioCA.setValue(obj.getDataInicioCA().getTime());
        dataFimCA.setValue(obj.getDataFimCA().getTime());
        dataInicioPL.setValue(obj.getDataInicioPL().getTime());
        dataFimPL.setValue(obj.getDataFimPL().getTime());
        dataInicioPM.setValue(obj.getDataInicioPM().getTime());
        dataFimPM.setValue(obj.getDataFimPM().getTime());
    }

    private void bloquearTela() {
        curso.setDisabled(true);
        identificador.setDisabled(true);
        duracao.setDisabled(true);
        dataInicioCA.setDisabled(true);
        dataFimCA.setDisabled(true); 
        dataInicioPL.setDisabled(true);
        dataFimPL.setDisabled(true); 
        dataInicioPM.setDisabled(true);
        dataFimPM.setDisabled(true);
    }

    public void onClick$salvar(Event event) {
        try {
            Calendar cal = Calendar.getInstance();
            if(MODO == ctrl.SALVAR)
            {
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(curso.getSelectedItem().getValue());
                args.add(identificador.getValue());
                args.add(duracao.getValue());
                cal.setTime(dataInicioCA.getValue());
                args.add(cal);
                cal.setTime(dataFimCA.getValue());
                args.add(cal);
                cal.setTime(dataInicioPL.getValue());
                args.add(cal);
                cal.setTime(dataFimPL.getValue());
                args.add(cal);
                cal.setTime(dataInicioPM.getValue());
                args.add(cal);
                cal.setTime(dataFimPM.getValue());
                args.add(cal);
                
               
                ctrl.incluirCalendario(args);
                limparCampos();
                ctrl.redirectPag("/PagEventosCalendario.zul");
            }
            else
            {
               obj.setCurso((Curso) curso.getSelectedItem().getValue());
               obj.setIdentificador(identificador.getValue());
               obj.setDuracao(duracao.getValue());
               
               cal.setTime(dataInicioCA.getValue());
               obj.setDataInicioCA(cal);
               cal.setTime(dataFimCA.getValue());
               obj.setDataFimCA(cal);
               
               cal.setTime(dataInicioPL.getValue());
               obj.setDataInicioPL(cal);
               cal.setTime(dataFimPL.getValue());
               obj.setDataFimPL(cal);
               
               cal.setTime(dataInicioPM.getValue());
               obj.setDataInicioPM(cal);
               cal.setTime(dataFimPM.getValue());
               obj.setDataFimPM(cal);
               
               ctrl.alterarCalendario(obj);
               ctrl.redirectPag("/PagEventosCalendario.zul");
            }
        } 
        catch (AcademicoException ex) {
            Logger.getLogger(PagFormularioCalendario.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            Logger.getLogger(PagFormularioCalendario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$cancelar(Event event) {
        ctrl.redirectPag("/PagEventosCalendario.zul");
    }
    
    public void onClose$winCadastro(Event event) {
        ctrl.redirectPag("/PagEventosCalendario.zul");
    }

    public void limparCampos() {
        curso.setSelectedItem(null);
        identificador.setValue("");
        duracao.setValue("");
        dataInicioCA.setValue(null);
        dataFimCA.setValue(null); 
        dataInicioPL.setValue(null);
        dataFimPL.setValue(null); 
        dataInicioPM.setValue(null); 
        dataFimPM.setValue(null);
    }
}

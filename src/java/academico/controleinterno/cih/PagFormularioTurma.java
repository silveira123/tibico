package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
import academico.util.Exceptions.AcademicoException;
import academico.util.horario.cdp.Horario;
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
    private CtrlCadastroCurso ctrlDisciplina = CtrlCadastroCurso.getInstance();
    private Window winFormularioTurma;
    private Combobox disciplina;
    private Combobox calendario;
    private Intbox numVagas;
    private Listbox listHorario;
    private Combobox professor;
    private Button salvar;
    private Button cancelar;
    private int MODO;
    private Turma obj;
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        List<Disciplina> listaDisciplina = ctrlDisciplina.obterDisciplinas();
        disciplina.setModel(new ListModelList(listaDisciplina, true));
        
        List<Calendario> listaCalendario = ctrl.obterCalendario();
        calendario.setModel(new ListModelList(listaCalendario, true));
        
        List<Horario> listaHorario = ctrl.obterHorario();
        listHorario.setModel(new ListModelList(listaHorario, true));
        
        
        //TODO tem que ver como vai fazer para botar os horários na tela
        //TODO fazer toda parte do professor
    }
    

    public void onCreate$winFormularioTurma() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != ctrl.SALVAR) {
            obj = (Turma) arg.get("obj");
            preencherTela();
            if (MODO == ctrl.CONSULTAR) {
                this.salvar.setVisible(false);
                bloquearTela();
            }
        }
    }

    private void preencherTela() {
        List<Comboitem> a = disciplina.getItems();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getValue() == obj.getDisciplina()) {
                disciplina.setSelectedItem(a.get(i));
            }
        }
        a = calendario.getItems();
        for (int i = 0; i < a.size(); i++) {
           if (a.get(i).getValue() == obj.getCalendario()) {
                calendario.setSelectedItem(a.get(i));
            }
        }
        numVagas.setValue(obj.getNumVagas());
        
        List<Listitem> itens = listHorario.getItems();
        for (int i = 0; i < itens.size(); i++) {
            for (int j = 0; j < obj.getHorario().size(); j++) {
                if (itens.get(i).getValue() == obj.getHorario().get(j)) {
                    listHorario.setSelectedItem(itens.get(i));
                }
            }
        }
        
        //TODO fazer professor, pois ainda falta a apl
    }

    private void bloquearTela() {
        disciplina.setDisabled(true);
        calendario.setDisabled(true);
        numVagas.setDisabled(true);
        listHorario.setDisabled(true);
        professor.setDisabled(true); 
        
    }

    public void onClick$salvar(Event event) {
        try {
            if(MODO == ctrl.SALVAR)
            {
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(disciplina.getSelectedItem().getValue());
                args.add(calendario.getSelectedItem().getValue());
                args.add(numVagas.getValue());
                Object[] o = listHorario.getSelectedItems().toArray();
                ArrayList<Horario> selecionados = new ArrayList<Horario>();
                for (int i = 0; i < o.length; i++) {
                    selecionados.add((Horario)o[i]);
                }
                obj.setHorario(selecionados);
                args.add(selecionados);
                
                //TODO fazer para o professor
                ctrl.incluirTurma(args);
                limparCampos();
                ctrl.redirectPag("/PagEventosTurma.zul");
            }
            else
            {
               obj.setDisciplina((Disciplina) disciplina.getSelectedItem().getValue());
               obj.setCalendario((Calendario) calendario.getSelectedItem().getValue());
               obj.setNumVagas(numVagas.getValue());
               Object[] o = listHorario.getSelectedItems().toArray();
               ArrayList<Horario> selecionados = new ArrayList<Horario>();
               for (int i = 0; i < o.length; i++) {
                    selecionados.add((Horario)o[i]);
               }
               obj.setHorario(selecionados);
               //TODO fazer para o professor
              
               
               ctrl.alterarTurma(obj);
               ctrl.redirectPag("/PagEventosTurma.zul");
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
        ctrl.redirectPag("/PagEventosTurma.zul");
    }
    
    public void onClose$winCadastro(Event event) {
        ctrl.redirectPag("/PagEventosTurma.zul");
    }

    public void limparCampos() {
        disciplina.setSelectedItem(null);
        calendario.setSelectedItem(null);
        numVagas.setValue(null);
        listHorario.setSelectedItems(null);
        professor.setSelectedItem(null);
       
    }
}

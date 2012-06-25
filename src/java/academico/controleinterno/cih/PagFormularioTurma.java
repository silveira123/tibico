package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
import academico.util.Exceptions.AcademicoException;
import academico.util.horario.cdp.DiaSemana;
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
    private Listhead listhead;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        List<Disciplina> listaDisciplina = ctrlDisciplina.obterDisciplinas();
        disciplina.setModel(new ListModelList(listaDisciplina, true));

        List<Calendario> listaCalendario = ctrl.obterCalendario();
        calendario.setModel(new ListModelList(listaCalendario, true));

        List<Horario> listaHorario = ctrl.obterHorario();
        DiaSemana dia = null;
        if (listaHorario.size() > 0) {
            dia = listaHorario.get(0).getDia();
        }
        for (int i = 0; i < listaHorario.size(); i++) {
            Listheader lh = new Listheader();
            lh.setAlign("Center");
            if (i == 0) {
                lh.setLabel("Dia");
                listhead.appendChild(lh);
            }
            else if (listaHorario.get(i - 1).getDia().equals(dia)) {
                lh.setLabel(listaHorario.get(i - 1).toString());
                listhead.appendChild(lh);

            }
            else {
                break;
            }
        }

        for (int i = 0; i < listaHorario.size();) {

            int j;
            Listitem linha = new Listitem();
            for (j = i; j < listhead.getChildren().size() - 1 + i; j++) {
                Listcell cell = new Listcell();
                Checkbox c = new Checkbox();
                if (j == i) {
                    cell.setLabel(listaHorario.get(j).getDia().toString());
                    linha.appendChild(cell);
                    cell = new Listcell();
                    cell.setValue(listaHorario.get(j));
                    cell.appendChild(c);
                    linha.appendChild(cell);
                }
                else {
                    cell.setValue(listaHorario.get(j));
                    cell.appendChild(c);
                    linha.appendChild(cell);
                }

            }
            listHorario.appendChild(linha);
            i = j;


        }



        // listHorario.setModel(new ListModelList(listaHorario, true));
        
        
        //TODO tem que ver como vai fazer para botar os horários na tela
        //TODO fazer toda parte do professor
    }

    public void onCreate$winFormularioTurma() {
        MODO = (Integer) arg.get("tipo");

        if (MODO != CtrlLetivo.SALVAR) {
            obj = (Turma) arg.get("obj");
            preencherTela();
            if (MODO == CtrlLetivo.CONSULTAR) {
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
            if (MODO == ctrl.SALVAR) {
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(disciplina.getSelectedItem().getValue());
                args.add(calendario.getSelectedItem().getValue());
                args.add(numVagas.getValue());
                ArrayList<Horario> selecionados = null;//getHorariosSelecionados();
                args.add(selecionados);

                //TODO fazer para o professor
                ctrl.incluirTurma(args);
                limparCampos();
                ctrl.redirectPag("/PagEventosTurma.zul");
            }
            else {
                obj.setDisciplina((Disciplina) disciplina.getSelectedItem().getValue());
                obj.setCalendario((Calendario) calendario.getSelectedItem().getValue());
                obj.setNumVagas(numVagas.getValue());
                ArrayList<Horario> selecionados = null;//getHorariosSelecionados();
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
        listHorario.clearSelection();
        professor.setSelectedItem(null);

    }

//    public ArrayList<Horario> getHorariosSelecionados() throws Exception {
//        List<Horario> listaHorario = ctrl.obterHorario();
//        ArrayList<Horario> horariosSelecionados = new ArrayList<Horario>();
//
//        for (int i = 0; i < listHorario.getChildren().size(); i++) {
//            if (listHorario.getChildren().get(i) instanceof Listitem) {
//                Listitem listitem = (Listitem) listHorario.getChildren().get(i);
//                for (int j = 0; j < listitem.getChildren().size(); j++) {
//                    if (listitem.getChildren().get(i) instanceof Listcell) {
//                        Listcell listcell = (Listcell) listitem.getChildren().get(i);
//                        if (listcell.getChildren().size() == 1) {
//                            Checkbox check = (Checkbox) listcell.getChildren().get(0);
//                            if (check.isChecked()) {
//                                Horario h = (Horario) listcell.getValue();
//                                horariosSelecionados.add(h);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println(horariosSelecionados.size());
//        return horariosSelecionados;
//    }
}

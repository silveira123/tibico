package academico.controleinterno.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.*;
import academico.util.Exceptions.AcademicoException;
import academico.util.horario.cdp.DiaSemana;
import academico.util.horario.cdp.Horario;
import java.util.ArrayList;
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
    private Window winFormularioTurma;
    private Combobox curso;
    private Combobox disciplina;
    private Combobox calendario;
    private Intbox numVagas;
    private Listbox listHorario;
    private Combobox professor;
    private Button salvar;
    private Button voltar;
    private int MODO;
    private Turma obj;
    private Listhead listhead;
    private ArrayList<Checkbox> horariosCheckbox = new ArrayList<Checkbox>();
    private int tipo;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        tipo = (Integer) arg.get("p");
        List<Curso> listaCurso = ctrlCurso.obterCursos();
        curso.setModel(new ListModelList(listaCurso, true));

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
            } else if (listaHorario.get(i - 1).getDia().equals(dia)) {
                lh.setLabel(listaHorario.get(i - 1).toString());
                listhead.appendChild(lh);

            } else {
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
                } else {
                    cell.setValue(listaHorario.get(j));
                    cell.appendChild(c);
                    linha.appendChild(cell);
                }
                horariosCheckbox.add(c);
            }
            listHorario.appendChild(linha);
            i = j;
        }

        curso.setReadonly(true);
        disciplina.setReadonly(true);
        professor.setReadonly(true);
        // listHorario.setModel(new ListModelList(listaHorario, true));

    }

    public void onCreate$winFormularioTurma() throws AcademicoException {
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

    private void preencherTela() throws AcademicoException {

        if (obj.getDisciplina().getCurso() != null) {
            ((ListModelList) curso.getModel()).addToSelection(obj.getDisciplina().getCurso());
            onSelect$curso(null);
        }

        if (obj.getDisciplina() != null) {
            ((ListModelList) disciplina.getModel()).addToSelection(obj.getDisciplina());
            onSelect$disciplina(null);
        }
        if (obj.getCalendario() != null) {
            ((ListModelList) calendario.getModel()).addToSelection(obj.getCalendario());
        }

        if (obj.getProfessor() != null) {
            ((ListModelList) professor.getModel()).addToSelection(obj.getProfessor());
        }

        numVagas.setValue(obj.getNumVagas());

        List<Horario> listaHorario = ctrl.obterHorario();

        for (int i = 0; i < listaHorario.size(); i++) {
            if (obj.getHorario().contains(listaHorario.get(i))) {
                horariosCheckbox.get(i).setChecked(true);
            }
            if(tipo != 1){
                horariosCheckbox.get(i).setDisabled(true);
            }
        }
        if (tipo != 1) {
            curso.setDisabled(true);
            disciplina.setDisabled(true);
            calendario.setDisabled(true);
            numVagas.setDisabled(true);
        }
    }

    private void bloquearTela() {
        curso.setDisabled(true);
        disciplina.setDisabled(true);
        calendario.setDisabled(true);
        numVagas.setDisabled(true);
        listHorario.setDisabled(true);
        professor.setDisabled(true);
        for (int i = 0; i < horariosCheckbox.size(); i++) {
            horariosCheckbox.get(i).setDisabled(true);
        }

    }

    public void onClick$salvar(Event event) {
        try {
            String msg = valido();
            if (msg.trim().equals("")) {
                if (MODO == ctrl.SALVAR) {
                    ArrayList<Object> args = new ArrayList<Object>();
                    args.add(disciplina.getSelectedItem().getValue());
                    args.add(calendario.getSelectedItem().getValue());
                    args.add(numVagas.getValue());
                    ArrayList<Horario> selecionados = getHorariosSelecionados();
                    args.add(selecionados);
                    if (professor.getSelectedItem() != null) {
                        args.add(professor.getSelectedItem().getValue());
                    } else {
                        args.add(null);
                    }
                    limparCampos();

                    ctrl.incluirTurma(args);
                    Messagebox.show("Cadastro feito!");
                } else {
                    if (tipo == 1) {
                        obj.setDisciplina((Disciplina) disciplina.getSelectedItem().getValue());
                        obj.setCalendario((Calendario) calendario.getSelectedItem().getValue());
                        obj.setNumVagas(numVagas.getValue());
                        ArrayList<Horario> selecionados = getHorariosSelecionados();
                        obj.setHorario(selecionados);

                        if (professor.getSelectedItem() != null) {
                            Professor p = professor.getSelectedItem().getValue();
                            obj.setProfessor(p);
                        }



                        ctrl.alterarTurma(obj);
                        Messagebox.show("Cadastro editado!");
                    }
                    else{
                        if (professor.getSelectedItem() != null) {
                            Professor p = professor.getSelectedItem().getValue();
                            obj.setProfessor(p);
                        }
                        ctrl.alterarTurma(obj);
                        Messagebox.show("Professor Alocado!");
                        
                    }
                }
                winFormularioTurma.onClose();
            } else {
                Messagebox.show(msg, "Informe:", 0, Messagebox.EXCLAMATION);
            }
        } catch (AcademicoException ex) {
            Logger.getLogger(PagFormularioCalendario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PagFormularioCalendario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$voltar(Event event) {
        winFormularioTurma.onClose();
    }

    public void limparCampos() {
        curso.setSelectedItem(null);
        disciplina.setSelectedItem(null);
        calendario.setSelectedItem(null);
        numVagas.setValue(null);
        professor.setSelectedItem(null);

        for (int i = 0; i < horariosCheckbox.size(); i++) {
            horariosCheckbox.get(i).setChecked(false);
        }
    }

    public ArrayList<Horario> getHorariosSelecionados() throws Exception {
        List<Horario> listaHorario = ctrl.obterHorario();
        ArrayList<Horario> horariosMarcados = new ArrayList<Horario>();

        for (int i = 0; i < listaHorario.size(); i++) {
            if (horariosCheckbox.get(i).isChecked()) {
                horariosMarcados.add(listaHorario.get(i));
            }
        }
        return horariosMarcados;
    }

    public void onSelect$curso(Event event) {
        disciplina.setSelectedItem(null);
        calendario.setSelectedItem(null);

        if (curso.getSelectedItem() != null) {
            List<Disciplina> listDisciplinas = ctrl.obterDisciplinas((Curso) curso.getSelectedItem().getValue());
            disciplina.setModel(new ListModelList(listDisciplinas, true));

            List<Calendario> listCalendarios = ctrl.obterCalendarios((Curso) curso.getSelectedItem().getValue());
            calendario.setModel(new ListModelList(listCalendarios, true));
        }
    }

    public void onSelect$disciplina(Event event) {
        professor.setSelectedItem(null);

        //pega a o array de selecionados, foi feito dessa forma prq o getSelectedItems estava voltando null
        Object[] array = ((ListModelList) disciplina.getModel()).getSelection().toArray();
        Disciplina d;
        if (array.length > 0) {
            d = (Disciplina) array[0];
            List<Professor> listProfessor = ctrl.obterProfessores(d);
            professor.setModel(new ListModelList(listProfessor, true));
        }
    }

    private String valido() {
        String msg = "";

        if (curso.getSelectedItem() == null) {
            msg += "- Curso\n";
        }
        if (disciplina.getSelectedItem() == null) {
            msg += "- Disciplina\n";
        }
        if (calendario.getSelectedItem() == null) {
            msg += "- Calendário\n";
        }

        return msg;
    }
}

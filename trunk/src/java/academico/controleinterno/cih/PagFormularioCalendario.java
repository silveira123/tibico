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

public class PagFormularioCalendario extends GenericForwardComposer {

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
            Calendar inicioCA = Calendar.getInstance();
            Calendar fimCA = Calendar.getInstance();
            Calendar inicioPL = Calendar.getInstance();
            Calendar fimPL = Calendar.getInstance();
            Calendar inicioPM = Calendar.getInstance();
            Calendar fimPM = Calendar.getInstance();

            if (MODO == ctrl.SALVAR) {
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(curso.getSelectedItem().getValue());
                args.add(identificador.getValue());
                args.add(duracao.getValue());
                inicioCA.setTime(dataInicioCA.getValue());
                fimCA.setTime(dataFimCA.getValue());
                inicioPL.setTime(dataInicioPL.getValue());
                fimPL.setTime(dataFimPL.getValue());
                inicioPM.setTime(dataInicioPM.getValue());
                fimPM.setTime(dataFimPM.getValue());

                int result = validar(inicioCA, fimCA, inicioPL, fimPL, inicioPM, fimPM);
                if (result < 8) {
                    imprimeValidacao(result);
                } else {
                    args.add(inicioCA);
                    args.add(fimCA);
                    args.add(inicioPL);
                    args.add(fimPL);
                    args.add(inicioPM);
                    args.add(fimPM);

                    ctrl.incluirCalendario(args);
                    limparCampos();
                    ctrl.redirectPag("/PagEventosCalendario.zul");
                }
            } else {
                obj.setCurso((Curso) curso.getSelectedItem().getValue());
                obj.setIdentificador(identificador.getValue());
                obj.setDuracao(duracao.getValue());

                inicioCA.setTime(dataInicioCA.getValue());
                fimCA.setTime(dataFimCA.getValue());

                inicioPL.setTime(dataInicioPL.getValue());
                fimPL.setTime(dataFimPL.getValue());

                inicioPM.setTime(dataInicioPM.getValue());
                fimPM.setTime(dataFimPM.getValue());

                int result = validar(inicioCA, fimCA, inicioPL, fimPL, inicioPM, fimPM);
                if (result < 8) {
                    imprimeValidacao(result);
                } else {
                    obj.setDataInicioCA(inicioCA);
                    obj.setDataFimCA(fimCA);

                    obj.setDataInicioPL(inicioPL);
                    obj.setDataFimPL(fimPL);

                    obj.setDataInicioPM(inicioPM);
                    obj.setDataFimPM(fimPM);

                    ctrl.alterarCalendario(obj);
                    ctrl.redirectPag("/PagEventosCalendario.zul");
                }
            }
        } catch (AcademicoException ex) {
            Logger.getLogger(PagFormularioCalendario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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

    public int validar(Calendar inicioCA, Calendar fimCA, Calendar inicioPL, Calendar fimPL, Calendar inicioPM, Calendar fimPM) {
        if (inicioCA.after(fimCA)) {
            return 1; //Caso o InicioCA seja depois do fim CA ele retorna
        } else if (inicioPL.after(fimPL)) {
            return 2;//Caso o inicioPL seja depois do fimPL ele retorna
        } else if (inicioPL.before(inicioCA)) {
            return 3;//Caso o inicioPL seja antes do inicioCA ele retorna
        } else if (fimPL.after(fimCA)) {
            return 4;//Caso o fimPL seja depois do fimCA ele retorna
        } else if (inicioPM.after(fimPM)) {
            return 5;//Caso o inicioPM seja depois do fimPM ele retorna
        } else if (inicioPM.before(inicioCA)) {
            return 6;//Caso o inicioPM seja antes do inicioCA ele retorna
        } else if (fimPM.after(fimCA)) {
            return 7;//Caso o fimPM seja depois do fimCA ele retorna
        } else {
            return 8;
        }
    }
    
    public void imprimeValidacao(int result)
    {
        if(result==1){
            Messagebox.show("Inicio do Calendário Acadêmico não pode ser depois do FIM");
        }
        else if(result==2){
            Messagebox.show("Inicio do Período Letivo não pode ser depois do FIM");
        }
        else if(result==3){
            Messagebox.show("Inicio do Período Letivo não pode ser antes do Inicio do Calendário Acadêmico");
        }
        else if(result==4){
             Messagebox.show("Fim do Período Letivo não pode ser depois do Fim do Calendário Acadêmico");
        }
        else if(result==5){
            Messagebox.show("Inicio do Período Matrícula não pode ser depois do FIM");
        }
        else if(result==6){
            Messagebox.show("Inicio do Período Matrícula não pode ser antes do Inicio do Calendário Acadêmico");
        }
        else if(result==7){
            Messagebox.show("Fim do Período Matrícula não pode ser antes do Fim do Calendário Acadêmico");
        }
        
    
    }
}

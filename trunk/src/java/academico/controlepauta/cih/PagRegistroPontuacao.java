package academico.controlepauta.cih;


import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cdp.Avaliacao;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.controlepauta.cdp.Resultado;
import academico.util.Exceptions.AcademicoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;


public class PagRegistroPontuacao extends GenericForwardComposer {
    private CtrlAula ctrl = CtrlAula.getInstance();
    private CtrlPessoa ctrlPessoa = CtrlPessoa.getInstance();
    private Window winRegistroPontuacao;
    private Textbox nome;
    private Textbox nomeAvaliacao;
    private Intbox peso;
    private Listbox listbox;
    private Button salvar;
    private Button fechar;
    private Avaliacao obj;
    private List<Doublebox> notas = new ArrayList<Doublebox>();
    private List<Textbox> observacoes = new ArrayList<Textbox>();;
    private List<Aluno> aluno;
    private List<Resultado> resultados;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obj = (Avaliacao) arg.get("obj");
        resultados = ctrl.obterResultados(obj);
        nome.setValue(obj.getTurma().toString());
        nomeAvaliacao.setValue(obj.getNome());
        peso.setValue(obj.getPeso());
        
        aluno = ctrlPessoa.obterAlunosporTurma(obj.getTurma());
         for (int i = 0; i < aluno.size(); i++) {
            Aluno c = aluno.get(i);
            Listitem linha = new Listitem(c.getMatricula() + "", c);
            linha.appendChild(new Listcell(c.getNome()));
           
            Listcell listcell = new Listcell();
            Doublebox t1 = new Doublebox();
            t1.setWidth("70%");
            t1.setValue(resultados.get(i).getPontuacao());
            t1.setParent(listcell);
            notas.add(t1);
            
            linha.appendChild(listcell);
            
            Listcell listcell2 = new Listcell();
            Textbox t = new Textbox();
            t.setWidth("99%");
            t.setValue(resultados.get(i).getObservacao());
            t.setParent(listcell2);
            observacoes.add(t);
            
            linha.appendChild(listcell2);
            
            linha.setParent(listbox);
        }
        bloquearTela();
    }
        
    
    public void onClick$salvar(Event event) {
        ArrayList<Object> argsNotas = new ArrayList<Object>();
        ArrayList<Object> argsObservacoes = new ArrayList<Object>();
        for (int i = 0; i < notas.size(); i++) {
            argsNotas.add(notas.get(i).getValue());
            argsObservacoes.add(observacoes.get(i).getValue());
        }
        try {
            //TODO inserir a matriculaTurma no  resultado
            ctrl.incluirResultado(obj, argsNotas, argsObservacoes);
        } catch (AcademicoException ex) {
            Logger.getLogger(PagRegistroPontuacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Messagebox.show("Resultado salvos com sucesso!");
    }

    public void onClick$fechar(Event event) {
       winRegistroPontuacao.onClose();
    }
    
    public void bloquearTela()
    {
        nome.setDisabled(true);
        nomeAvaliacao.setDisabled(true);
        peso.setDisabled(true);
    }
}
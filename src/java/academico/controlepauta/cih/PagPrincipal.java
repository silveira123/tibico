package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Professor;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.Usuario;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class PagPrincipal extends GenericForwardComposer {

    private Toolbarbutton turma;
    private Toolbarbutton matricularAluno;
    private Toolbarbutton alocarProfessor;
    private Toolbarbutton cadastrarAluno;
    private Toolbarbutton professor;
    private Toolbarbutton disciplina;
    private Toolbarbutton calendario;
    private Toolbarbutton curso;
    private Toolbarbutton chamada;
    private Toolbarbutton avaliacao;
    private Toolbarbutton resultado;
    private Toolbarbutton boletim;
    private Toolbarbutton historico;
    private Borderlayout border;
    private Panel controlarTurma;
    private Panel cadastroPessoa;
    private Panel cadastroAcademico;
    private Panel pauta;
    private Panel relatorios;
    private Div div;
    private CtrlAula ctrlAula = CtrlAula.getInstance();
    private Aluno aluno;
    private Usuario user;
    private Professor prof;
    
    public void onCreate$div(Event event) {
        user = (Usuario) arg.get("usuario");

        if (user.getPrivilegio() == 3) {
            controlarTurma.setVisible(false);
            cadastroPessoa.setVisible(false);
            cadastroAcademico.setVisible(false);
            prof = ctrlAula.getProfessor(user.getNome());
        } else if (user.getPrivilegio() == 4) {
            pauta.setVisible(false);
            cadastroPessoa.setVisible(false);
            cadastroAcademico.setVisible(false);
            turma.setVisible(false);
            alocarProfessor.setVisible(false);
            resultado.setVisible(false);
            aluno = ctrlAula.getAluno(user.getNome());
        }
    }

    public void onClick$curso(Event event) {
        border.getCenter().getChildren().clear();
        Window winCurso = (Window) CtrlCadastroCurso.getInstance().abrirEventosCurso();
        winCurso.setWidth("100%");
        winCurso.setHeight("100%");
        winCurso.setParent(border.getCenter());
    }

    public void onClick$disciplina(Event event) {
        border.getCenter().getChildren().clear();
        Window winDisciplina = (Window) CtrlCadastroCurso.getInstance().abrirEventosDisciplina();
        winDisciplina.setWidth("100%");
        winDisciplina.setHeight("100%");
        winDisciplina.setParent(border.getCenter());
    }

    public void onClick$turma(Event event) {
        border.getCenter().getChildren().clear();
        Window winTurma = (Window) CtrlLetivo.getInstance().abrirEventosTurma();
        winTurma.setWidth("100%");
        winTurma.setHeight("100%");
        winTurma.setParent(border.getCenter());
    }

    public void onClick$calendario(Event event) {
        border.getCenter().getChildren().clear();
        Window winCalendario = (Window) CtrlLetivo.getInstance().abrirEventosCalendario();
        winCalendario.setWidth("100%");
        winCalendario.setHeight("100%");
        winCalendario.setParent(border.getCenter());
    }

    public void onClick$matricularAluno(Event event) {
        Window winMatricularAluno;
        border.getCenter().getChildren().clear();
        if (user.getPrivilegio() == 4) {
            winMatricularAluno = (Window) CtrlMatricula.getInstance().abrirEventosMatricula(aluno);
        } else {
            winMatricularAluno = (Window) CtrlMatricula.getInstance().abrirEventosMatricula();
        }

        winMatricularAluno.setWidth("100%");
        winMatricularAluno.setHeight("100%");
        winMatricularAluno.setParent(border.getCenter());
    }

    public void onClick$cadastrarAluno(Event event) {
        border.getCenter().getChildren().clear();
        Window winCadastrarAluno = (Window) CtrlPessoa.getInstance().abrirEventosAluno();
        winCadastrarAluno.setWidth("100%");
        winCadastrarAluno.setHeight("100%");
        winCadastrarAluno.setParent(border.getCenter());
    }

    public void onClick$professor(Event event) {
        border.getCenter().getChildren().clear();
        Window winProfessor = (Window) CtrlPessoa.getInstance().abrirEventosProfessor();
        winProfessor.setWidth("100%");
        winProfessor.setHeight("100%");
        winProfessor.setParent(border.getCenter());
    }

    public void onClick$alocarProfessor(Event event) {
        border.getCenter().getChildren().clear();
        Window winAlocarProfessor = (Window) CtrlLetivo.getInstance().abrirEventosTurma();
        winAlocarProfessor.setWidth("100%");
        winAlocarProfessor.setHeight("100%");
        winAlocarProfessor.setParent(border.getCenter());
    }

    public void onClick$historico(Event event) {
        border.getCenter().getChildren().clear();
        Window winHistorico;
        if (user.getPrivilegio() == 4) {
            winHistorico = (Window) CtrlMatricula.getInstance().abrirRelatorioHistorico(aluno);
        } else {
            winHistorico = (Window) CtrlMatricula.getInstance().abrirRelatorioHistorico();
        }
        winHistorico.setWidth("100%");
        winHistorico.setHeight("100%");
        winHistorico.setParent(border.getCenter());
    }

    public void onClick$boletim(Event event) {
        border.getCenter().getChildren().clear();
        Window winBoletim;
        if (user.getPrivilegio() == 4) {
            winBoletim = (Window) CtrlMatricula.getInstance().abrirRelatorioBoletim(aluno);
        } else {
            winBoletim = (Window) CtrlMatricula.getInstance().abrirRelatorioBoletim();
        }
        winBoletim.setWidth("100%");
        winBoletim.setHeight("100%");
        winBoletim.setParent(border.getCenter());
    }

    public void onClick$resultado(Event event) {
        border.getCenter().getChildren().clear();
        Window winResultado = (Window) CtrlMatricula.getInstance().abrirRelatorioResultados();
        winResultado.setWidth("100%");
        winResultado.setHeight("100%");
        winResultado.setParent(border.getCenter());
    }

    public void onClick$chamada(Event event) {
        border.getCenter().getChildren().clear();
        Window winChamada ;
        if (user.getPrivilegio() == 3) {
            winChamada = (Window) CtrlAula.getInstance().abrirEventosChamada(prof);
        }
        else{
             winChamada = (Window) CtrlAula.getInstance().abrirEventosChamada();
        }
        
        winChamada.setWidth("100%");
        winChamada.setHeight("100%");
        winChamada.setParent(border.getCenter());
    }

    public void onClick$avaliacao(Event event) {
        border.getCenter().getChildren().clear();
        Window winAvaliacao;
        if (user.getPrivilegio() == 3) {
            winAvaliacao = (Window) CtrlAula.getInstance().abrirEventosAvaliacao(prof);
        }
        else{
            winAvaliacao = (Window) CtrlAula.getInstance().abrirEventosAvaliacao();
        }
         
        winAvaliacao.setWidth("100%");
        winAvaliacao.setHeight("100%");
        winAvaliacao.setParent(border.getCenter());
    }
}
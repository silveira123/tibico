/*
 * PagPrincipal.java 
 * Versão: 0.1 
 * Data de Criação : 03/07/2012, 13:40:23
 * Copyright (c) 2012 Fabrica de Software IFES.
 * Incubadora de Empresas IFES, sala 11
 * Rodovia ES-010 - Km 6,5 - Manguinhos, Serra, ES, 29164-321, Brasil.
 * All rights reserved.
 *
 * This software is the confidential and proprietary 
 * information of Fabrica de Software IFES. ("Confidential Information"). You 
 * shall not disclose such Confidential Information and 
 * shall use it only in accordance with the terms of the 
 * license agreement you entered into with Fabrica de Software IFES.
 */
package academico.controlepauta.cih;

import academico.controleinterno.cci.CtrlCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cci.CtrlPessoa;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cdp.Professor;
import academico.controlepauta.cci.CtrlAula;
import academico.controlepauta.cci.CtrlMatricula;
import academico.controlepauta.cdp.Usuario;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Esta classe, através de alguns importes utiliza atributos do zkoss para leitura e interpretação de dados; A classe contém os eventos do Ptojeto Tibico; Os eventos são apresentados de acordo com o
 * privilégio de acesso.
 * <p/>
 * @author Pietro Crhist
 * @author Geann Valfré
 */
public class PagPrincipal extends GenericForwardComposer {

    private Toolbarbutton turma;
    private Toolbarbutton visualizarTurmas;
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
    private Toolbarbutton about;
    private Toolbarbutton sala;
    private Vlayout vlayout;
    private North north;
    private West west;
    private South south;
    private Borderlayout border, telaprincipal;
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
    private Label nomeUsuario;

    public void onCreate$div(Event event) {
        user = (Usuario) execution.getSession().getAttribute("usuario");

        if (user != null) {         
            if (user.getPessoa() != null) {
                nomeUsuario.setValue("SEJA BEM VINDO(A) " + user.getPessoa().getNome().toUpperCase());
            }
            else {
                nomeUsuario.setValue("SEJA BEM VINDO(A) ADMINISTRADOR");
            }

            if (user.getPrivilegio() == 3) {
                turma.setVisible(false);
                matricularAluno.setVisible(false);
                alocarProfessor.setVisible(false);

                cadastroPessoa.setVisible(true);
                cadastrarAluno.setVisible(false);

                cadastroAcademico.setVisible(false);
                prof = (Professor) user.getPessoa();
            }
            else if (user.getPrivilegio() == 4) {
                pauta.setVisible(false);
                cadastroAcademico.setVisible(false);
                turma.setVisible(false);
                alocarProfessor.setVisible(false);

                cadastroPessoa.setVisible(true);
                professor.setVisible(false);


                resultado.setVisible(false);
                visualizarTurmas.setVisible(false);
                aluno = (Aluno) user.getPessoa();
            }
        }
        else {
            //if feito para verificar se existe algum usuario logado, se nao existir eh redirecionado para o login
            Executions.sendRedirect("/");
            div.detach();
        }

    }

    public void onClientInfo$div(ClientInfoEvent event)
    {
        telaprincipal.setHeight(event.getDesktopHeight() + "px");
        telaprincipal.setWidth((event.getDesktopWidth() - 10) + "px");
        north.setSize((event.getDesktopHeight() * 0.037) + "px");
       // vlayout.setHeight((event.getDesktopHeight() * 0.246) + "px");
        west.setSize((event.getDesktopHeight() * 0.246) + "px");
        south.setSize((event.getDesktopHeight() * 0.049) + "px");;
    }
    
    public void setFundo() {
        if (border.getCenter().getChildren() == null) {
            Image img = new Image();
            img.setSrc("images/tibico6.png");
            img.setWidth("50%");
            img.setHeight("30%");
            img.setParent(border.getCenter());
        }
    }

    public void onClick$curso(Event event) {
        border.getCenter().getChildren().clear();
        Window winCurso = (Window) CtrlCurso.getInstance().abrirEventosCurso();
        winCurso.setWidth("100%");
        winCurso.setHeight("100%");
        winCurso.setParent(border.getCenter());
    }

    public void onClick$logout(Event event) {
        Executions.getCurrent().getSession().removeAttribute("usuario");
        Executions.sendRedirect("/");
    }

    public void onClick$disciplina(Event event) {
        border.getCenter().getChildren().clear();
        Window winDisciplina = (Window) CtrlCurso.getInstance().abrirEventosDisciplina();
        winDisciplina.setWidth("100%");
        winDisciplina.setHeight("100%");
        winDisciplina.setParent(border.getCenter());
    }
    
    public void onClick$sala(Event event) {
        border.getCenter().getChildren().clear();
        Window winDisciplina = (Window) CtrlLetivo.getInstance().abrirEventosSala();
        winDisciplina.setWidth("100%");
        winDisciplina.setHeight("100%");
        winDisciplina.setParent(border.getCenter());
    }

    public void onClick$turma(Event event) {
        border.getCenter().getChildren().clear();
        Window winTurma = (Window) CtrlLetivo.getInstance().abrirEventosTurma(1);
        winTurma.setWidth("100%");
        winTurma.setHeight("100%");
        winTurma.setParent(border.getCenter());
    }

    public void onClick$visualizarTurmas(Event event) {
        border.getCenter().getChildren().clear();
        Window winVisualizarTurmas;
        if (user.getPrivilegio() == 3) {
            winVisualizarTurmas = (Window) CtrlLetivo.getInstance().abrirVisualizarTurmas(prof);
        }
        else {
            winVisualizarTurmas = (Window) CtrlLetivo.getInstance().abrirVisualizarTurmas();
        }

        winVisualizarTurmas.setWidth("100%");
        winVisualizarTurmas.setHeight("100%");
        winVisualizarTurmas.setParent(border.getCenter());
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
        }
        else {
            winMatricularAluno = (Window) CtrlMatricula.getInstance().abrirEventosMatricula();
        }

        winMatricularAluno.setWidth("100%");
        winMatricularAluno.setHeight("100%");
        winMatricularAluno.setParent(border.getCenter());
    }

    public void onClick$cadastrarAluno(Event event) {
        Window winCadastrarAluno;
        if (user.getPrivilegio() == 4) {
            Aluno a = (Aluno) user.getPessoa();
            CtrlPessoa.getInstance().abrirEditarAluno(a, a.getCurso());
        }
        else {
            border.getCenter().getChildren().clear();
            winCadastrarAluno = (Window) CtrlPessoa.getInstance().abrirEventosAluno();
            winCadastrarAluno.setWidth("100%");
            winCadastrarAluno.setHeight("100%");
            winCadastrarAluno.setParent(border.getCenter());
        }
    }

    public void onClick$professor(Event event) {
        Window winProfessor;
        if (user.getPrivilegio() == 3) {
            Professor p = (Professor) user.getPessoa();
            CtrlPessoa.getInstance().abrirEditarProfessor(p);
        }
        else {
            border.getCenter().getChildren().clear();
            winProfessor = (Window) CtrlPessoa.getInstance().abrirEventosProfessor();
            winProfessor.setWidth("100%");
            winProfessor.setHeight("100%");
            winProfessor.setParent(border.getCenter());
        }

    }

    public void onClick$alocarProfessor(Event event) {
        border.getCenter().getChildren().clear();
        Window winAlocarProfessor = (Window) CtrlLetivo.getInstance().abrirEventosAlocarProfessor(2);
        winAlocarProfessor.setWidth("100%");
        winAlocarProfessor.setHeight("100%");
        winAlocarProfessor.setParent(border.getCenter());
    }

    public void onClick$historico(Event event) {
        border.getCenter().getChildren().clear();
        Window winHistorico;
        if (user.getPrivilegio() == 4) {
            winHistorico = (Window) CtrlMatricula.getInstance().abrirRelatorioHistorico(aluno);
        }
        else {
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
        }
        else {
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
        Window winChamada;
        if (user.getPrivilegio() == 3) {
            winChamada = (Window) CtrlAula.getInstance().abrirEventosChamada(prof);
        }
        else {
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
        else {
            winAvaliacao = (Window) CtrlAula.getInstance().abrirEventosAvaliacao();
        }

        winAvaliacao.setWidth("100%");
        winAvaliacao.setHeight("100%");
        winAvaliacao.setParent(border.getCenter());
    }

    public void onClick$about(Event event) {
        CtrlAula.getInstance().abrirAbout();
    }
}
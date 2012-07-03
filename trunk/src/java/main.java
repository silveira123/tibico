import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Aluno;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgd.*;
import academico.controleinterno.cgt.AplCadastroCurso;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.util.horario.cdp.Horario;
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cgt.AplCadastrarPessoa;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import academico.util.Exceptions.AcademicoException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//       ArrayList<Horario> horario = new ArrayList<Horario>(CtrlLetivo.getInstance().obterHorario());
//       Calendario c = CtrlLetivo.getInstance().obterCalendario().get(0);
//       Disciplina d = CtrlCadastroCurso.getInstance().obterDisciplinas().get(0);
//       
//       Turma t = new Turma();
//       t.setCalendario(c);
//       t.setHorario(horario);
//       t.setDisciplina(d);
//       t.setNumVagas(8);
       
//       Aluno a = new Aluno();
//       a.setCoeficiente(7.6);
//       a.setNome("n");
//       a.setNomeMae("m");
//       a.setNomePai("l");
//       a.setCurso(AplCadastroCurso.getInstance().obterCursos().get(0));
//       a.setCpf(Long.valueOf("32132132132"));
//       a.setMatricula(Long.valueOf("32132132132"));
       AlunoDAO tdao = new AlunoDAOJPA();
//       tdao.salvar(a);
        
        TurmaDAO d = new TurmaDAOJPA();
       
       System.out.println(d.obterTurmasAtuais(tdao.obter(Aluno.class).get(0)).get(0));
    }
}

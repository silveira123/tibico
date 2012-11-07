import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgd.AlunoDAO;
import academico.controleinterno.cgd.AlunoDAOJPA;
import academico.util.horario.cdp.DiaSemana;
import academico.util.horario.cdp.Horario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import academico.util.email.Mail;
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
       Turma t = new Turma();
       Horario h =new Horario();
       h.setDia(DiaSemana.TERÇA);
       h.setHorarioFim(Calendar.getInstance());
       h.setHorarioInicio(Calendar.getInstance());
       List<Horario> l = new ArrayList<Horario>();
       l.add(h);
       t.setHorario(l);
       CtrlLetivo.getInstance().abrirAlocarHorarioSala(t);
       
//       tdao.salvar(a);
        
        //TurmaDAO d = new TurmaDAOJPA();
       
      // System.out.println(d.obterTurmasAtuais(tdao.obter(Aluno.class).get(0)).get(0));
        
        
        
    }
}

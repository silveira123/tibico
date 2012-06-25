
import academico.controleinterno.cci.CtrlCadastroCurso;
import academico.controleinterno.cci.CtrlLetivo;
import academico.controleinterno.cdp.Calendario;
import academico.controleinterno.cdp.Disciplina;
import academico.controleinterno.cdp.Turma;
import academico.controleinterno.cgd.TurmaDAO;
import academico.controleinterno.cgd.TurmaDAOJPA;
import academico.controleinterno.cgt.AplControlarTurma;
import academico.util.horario.cdp.Horario;
import java.util.ArrayList;



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
       
       
       TurmaDAO tdao = new TurmaDAOJPA();
//       tdao.salvar(t);
       
       System.out.println(AplControlarTurma.getInstance().obterHorarios().size());
    }
}

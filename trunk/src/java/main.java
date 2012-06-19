
import academico.controleinterno.cdp.Professor;
import academico.controleinterno.cgd.ProfessorDAOJPA;
import academico.util.academico.cdp.AreaConhecimento;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.pessoa.cdp.Sexo;
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
       Professor c = new Professor();
       GrandeAreaConhecimento g = new GrandeAreaConhecimento();
       AreaConhecimento a = new AreaConhecimento();
       AreaConhecimento b = new AreaConhecimento();
       ArrayList<AreaConhecimento> array = new ArrayList<AreaConhecimento>();
       
       g.setNome("garea1");
       
       a.setgAreaConhecimento(g);
       b.setgAreaConhecimento(g);
       a.setNome("area1");
       b.setNome("area2");
       
       array.add(a);
       array.add(b);
       
       c.setNome("Teste");
       c.setCpf(1234);
       c.setIdentidade("123423");
       
       c.setAreaConhecimento(array);

       
       ProfessorDAOJPA cjpa = new ProfessorDAOJPA();
       cjpa.salvar(c);
    }
}

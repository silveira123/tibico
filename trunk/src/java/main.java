
import academico.controlepauta.cdp.Aula;
import academico.controlepauta.cdp.Frequencia;
import academico.controlepauta.cgd.AulaDAOJPA;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.academico.cgd.GrandeAreaConhecimentoDAOJPA;
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
       GrandeAreaConhecimento a = new GrandeAreaConhecimento();
       a.setNome("asd");
             
       GrandeAreaConhecimentoDAOJPA cjpa = new GrandeAreaConhecimentoDAOJPA();
       cjpa.salvar(a);
    }
}

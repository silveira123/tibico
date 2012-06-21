
import academico.controlepauta.cdp.Aula;
import academico.controlepauta.cdp.Frequencia;
import academico.controlepauta.cgd.AulaDAOJPA;
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
       Aula a = new Aula();
       a.setConteudo("asd");
             
       AulaDAOJPA cjpa = new AulaDAOJPA();
       cjpa.salvar(a);
    }
}

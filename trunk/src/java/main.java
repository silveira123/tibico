
import academico.controleinterno.cdp.Curso;
import academico.controleinterno.cgd.CursoDAOJPA;



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
       Curso c = new Curso();
       c.setNome("Teste");
       
       CursoDAOJPA cjpa = new CursoDAOJPA();
       cjpa.salvar(c);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controleinterno.cgt;

import academico.controleinterno.cdp.Curso;
import academico.util.academico.cdp.GrandeAreaConhecimento;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.List;


public class AplPrincipal {
    // TODO: O método main não existe, é só teste!
    public static void main(String[] args) {
//        Curso c1 = new Curso();
//        c1.setNome("BSI");
//        Curso c2 = new Curso();
//        c2.setNome("ECA");
//        System.out.println("Objetos: " + c1 + ";" + c2);
//        DAO daoCurso = DAOFactory.obterDAO("JPA", Curso.class);
//        try {
//            for (int i = 0; i < 200; i++) {
//                daoCurso.salvar(c1);
//            }
//            daoCurso.salvar(c2);
//            System.out.println("Objetos Salvos: " + c1 + ";" + c2);
//        } catch (Exception ex) {
//            System.out.println("Erro ao salvar");
//        }
//        List lista = daoCurso.obter(Curso.class);
//        System.out.println("Objetos Recuperados: " + lista);
//        System.out.println("Objetos Salvos: " + c1 + ";" + c2);
        DAO daoGA = DAOFactory.obterDAO("JPA", GrandeAreaConhecimento.class);
        GrandeAreaConhecimento g = new GrandeAreaConhecimento();
        g.setNome("Exatas");
        daoGA.salvar(g);
        
    }

    public void conectarBD() {
    }

    public void desconectarBD() {
    }

    public void login() {
    }

    public void logout() {
    }
}

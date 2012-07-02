
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
    public static void main(String[] args){
        try {
            List<Professor> p = AplCadastrarPessoa.getInstance().obterProfessor();
            AplCadastrarPessoa.getInstance().apagarProfessor(p.get(1));
        }
        catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

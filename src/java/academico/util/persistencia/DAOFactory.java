package academico.util.persistencia;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAOFactory {

    public static String TIPO_JDBC = "JDBC";
    public static String TIPO_JPA = "JPA";
    
    //TODO resolver passagem do tipo e Exceptions
    
    public static DAO obterDAO(String tipo, Class classe) {
        String nome = classe.getName();
        System.out.println(nome);
        nome = nome.replace("cdp", "cgd");
        nome = nome + "DAO" + tipo;
        
        try {
            return (DAO) Class.forName(nome).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.controlepauta.cgt;

import academico.controlepauta.cdp.Avaliacao;
import academico.util.Exceptions.AcademicoException;
import academico.util.persistencia.DAO;
import academico.util.persistencia.DAOFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Turma
 * @author pietrochrist
 */
public class AplControlarAula {
    private DAO apDaoAvaliacao = DAOFactory.obterDAO("JPA", Avaliacao.class);
    
    private AplControlarAula() {
    }
    
    private static AplControlarAula instance = null;

    public static AplControlarAula getInstance() {
        if (instance == null) {
            instance = new AplControlarAula();
        }
        return instance;
    }

    public Avaliacao incluirAvaliacao(ArrayList<Object> args) throws AcademicoException {
        
        return (Avaliacao) apDaoAvaliacao.salvar(null);
    }

    public Avaliacao alterarAvaliacao(Avaliacao avaliacao) throws Exception {
        return (Avaliacao) apDaoAvaliacao.salvar(avaliacao);
    }

    public void apagarAvaliacao(Avaliacao avaliacao) throws Exception {
        apDaoAvaliacao.excluir(avaliacao);
    }

    public List<Avaliacao> obterAvaliacoes() throws AcademicoException {
        return (List<Avaliacao>) apDaoAvaliacao.obter(Avaliacao.class);
    }
}

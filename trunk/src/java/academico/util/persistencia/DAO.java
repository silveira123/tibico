/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.persistencia;

import academico.controleinterno.cdp.Aluno;
import academico.controlepauta.cdp.MatriculaTurma;
import academico.util.Exceptions.AcademicoException;
import java.util.List;

/**
 *
 * @author Administrador
 */

public interface DAO<T> {
    public T salvar(T obj) throws AcademicoException;
    public void excluir(T obj) throws AcademicoException;
    public List<T> obter(Class<T> classe) throws AcademicoException;
    //public T obterId(long id);
    //public void addCriterio(String); ???
}

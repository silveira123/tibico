/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.persistencia;

import java.util.List;

/**
 *
 * @author Administrador
 */

public interface DAO<T> {
    public T salvar(T obj);
    public void excluir(T obj);
    public List<T> obter(Class<T> classe);
    //public T obterId(long id);
    //public void addCriterio(String); ???
}

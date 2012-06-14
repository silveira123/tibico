/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package academico.util.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public abstract class DAOJPA<T extends ObjetoPersistente> implements DAO<T> {

    //Obtém o factory a partir da unidade de persistência.
    protected static EntityManager entityManager =
            Persistence.createEntityManagerFactory("JPA").
            createEntityManager();

    public T salvar(T obj) {
        // Inicia uma transação com o banco de dados.
        entityManager.getTransaction().begin();
        // Verifica se o curso ainda não está salvo no banco de dados.
        if (obj.getId() == null) {
            //Salva os dados do curso.
            entityManager.persist(obj);
        } else {
            //Atualiza os dados do curso.
            obj = entityManager.merge(obj);
        }
        // Finaliza a transação.
        entityManager.getTransaction().commit();
        return obj;
    }

    public void excluir(T obj) {
        try {
            // Inicia uma transação com o banco de dados.
            entityManager.getTransaction().begin();
            // Remove o curso da base de dados.
            entityManager.remove(obj);
            // Finaliza a transação.
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro no excluir curso " + e);
        }
    }

    //TODO tentar resolver a passagem do .class
    public List<T> obter(Class<T> classe) {
        Query query = entityManager.createQuery("SELECT t FROM " + classe.getSimpleName() + " t");
        List<T> lista = query.getResultList();
        return lista;
    }
}

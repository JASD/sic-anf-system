/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecon.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Antonio
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private int cont;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        cont = q.getResultList().size();
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /*
     * NamedQuery con Parametros
     */
    public List<T> findRange(int[] range, String namedQuery, Object[] parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        cont = q.getResultList().size();
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /*
     * NamedQuery sin Parametros
     */
    public List<T> findRange(int[] range, String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        cont = q.getResultList().size();
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    /**
     * Todos los elementos sin paginacion de un namedQuery
     * @param namedQuery
     * @return 
     */
    
    public List<T> find(String namedQuery, Object[] parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        cont = q.getResultList().size();
        return q.getResultList();
    }
    
    /*
     * Encuentra Todo de un NamedQuery Sin Parametros
     */
    public List<T> findNoParameters(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        cont = q.getResultList().size();
        return q.getResultList();
    }
    /*public int count() {
    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
    cq.select(getEntityManager().getCriteriaBuilder().count(rt));
    javax.persistence.Query q = getEntityManager().createQuery(cq);
    return ((Long) q.getSingleResult()).intValue();
    }*/
    public int count() {

        return cont;

    }

    /*
     * NamedQuery de un solo resultado con parametros
     */
    public T getSingleResult(String namedQuery, Object[] parameters) throws NoResultException {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        return (T) q.getSingleResult();
    }
    
     public T getSingleResult(String namedQuery) throws NoResultException {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        return (T) q.getSingleResult();
    }

    /*
     *Named Query de varios resutlados con parametros 
     */
    public List<T> getResultList(String namedQuery, Object[] parameters) throws NoResultException {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        return q.getResultList();
    }
}

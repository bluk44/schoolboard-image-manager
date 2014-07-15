package dataaccess;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	@Override
	public void save(T entity) {
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.persist(entity);		
	}
	
	@Override
	public void update(T entity) {
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.refresh(entity);	
	}
	
	@Override
	public void merge(T entity) {
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findMany(Query query) {
        return (List<T>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOne(Query query) {
		return (T)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class clazz) {
		EntityManager em = EclipseLinkUtil.getEntityManager();
		Query q = em.createQuery("SELECT c from "+clazz.getName()+" c");
		return (List<T>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findByID(Class clazz, ID id) {
		EntityManager em = EclipseLinkUtil.getEntityManager();			
		return (T)em.find(clazz, id);
	}

}

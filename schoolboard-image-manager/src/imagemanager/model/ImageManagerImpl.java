package imagemanager.model;

import imagemanager.exception.DBException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import dataaccess.JPAUtil;

public class ImageManagerImpl implements ImageManager {

	// TODO obsluga wyjatkow
	@Override
	public void createLabel(Label newLabel) throws DBException{
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(newLabel);
			em.getTransaction().commit();
			em.close();
		} catch (RollbackException ex) {
			throw new DBException("Label already exists");
		} finally {}
	}

	@Override
	public List<Label> getAllLabels() {
		List<Label> results;
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select l from Label l");
		results = q.getResultList();
		em.getTransaction().commit();
		em.close();
		return results;
	}

	@Override
	public void rename(String title, Label label) {

	}

	@Override
	public void deleteLabel(Label... labels) {
		// TODO Auto-generated method stub

	}

}

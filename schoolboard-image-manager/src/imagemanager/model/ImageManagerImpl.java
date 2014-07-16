package imagemanager.model;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dataaccess.JPAUtil;


public class ImageManagerImpl implements ImageManager{
		
	// TODO obsluga wyjatkow
	@Override
	public Label createLabel(Label newLabel){
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(newLabel);
		em.getTransaction().commit();
		em.close();
		return newLabel;
	}

	@Override
	public List<Label> getAllLabels() {
		List<Label> results;
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select l from Label l");
		results = q.getResultList();
		System.out.println("results "+results);
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

package imagemanager.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dataaccess.JPAUtil;


public class ImageManagerImpl implements ImageManager{
	
	private Set<Label> labels = new HashSet<Label>();
	
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
	public void readAllLabels() {
		List<Label> results;
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select l from Label l");
		results = q.getResultList();
		em.getTransaction().commit();
		em.close();
		labels = new HashSet<Label>(results);
	}
	
	@Override
	public Set<Label> getLabels() {
		return labels;
	}

	@Override
	public void rename(String title, Label label) {
		
	}

	@Override
	public void deleteLabel(Label... labels) {
		// TODO Auto-generated method stub
		
	}

}	

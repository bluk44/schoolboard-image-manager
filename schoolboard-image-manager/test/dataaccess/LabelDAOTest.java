package dataaccess;

import imagemanager.model.Label;

import java.util.List;

import javax.persistence.EntityManager;

public class LabelDAOTest {

	public static void main(String[] args) {
		//saveTest();
		//findByIdTest();
		 //deleteTest();
		findAllTest();
	}

	public static void saveTest() {
		Label lab = new Label("test label");
		LabelDAO dao = new LabelDAOImpl();
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.getTransaction().begin();
		dao.save(lab);
		em.getTransaction().commit();
		em.close();
	}

	public static void findByIdTest() {
		LabelDAO dao = new LabelDAOImpl();
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.getTransaction().begin();
		Label lab = dao.findByID(Label.class, 101l);
		em.getTransaction().commit();
		em.close();
		System.out.println(lab);
	}
	
	public static void findAllTest(){
		LabelDAO dao = new LabelDAOImpl();
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.getTransaction().begin();
		List<Label> labs = dao.findAll(Label.class);
		em.getTransaction().commit();
		em.close();
		for (Label lab : labs) {
			System.out.println(lab);

		}
	}
	
	public static void deleteTest() {
		LabelDAO dao = new LabelDAOImpl();
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.getTransaction().begin();
		Label lab = dao.findByID(Label.class, 101l);
		dao.delete(lab);
		em.getTransaction().commit();

	}
}

package dataaccess;

import javax.persistence.EntityManager;

import imagemanager.model.Label;

public class LabelDAOTest {

	public static void main(String[] args) {
		saveTest();

	}
	
	public static void saveTest(){
		Label lab = new Label("test label");
		LabelDAO dao = new LabelDAOImpl();
		EntityManager em = EclipseLinkUtil.getEntityManager();
		em.getTransaction().begin();
		dao.save(lab);
		em.getTransaction().commit();
		em.close();
	}
}

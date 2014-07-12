package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EclipseLinkUtil {
	private static final String PERSISTENCE_UNIT_NAME = "imgmanager";
	private static EntityManager manager;

	
	public static EntityManager getEntityManager(){
		if(manager == null){
			manager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
		}
		return manager;
	}
}

package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EclipseLinkUtil {
	private static final String PERSISTENCE_UNIT_NAME = "tree";
	private static EntityManagerFactory factory;
	static{
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	public static EntityManagerFactory getEntityManagerFactory(){
		return factory;
	}
}

package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ApplicationConfig {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("<nome da sua persistency unit>");
	final public static EntityManager entityManager = entityManagerFactory.createEntityManager();
}

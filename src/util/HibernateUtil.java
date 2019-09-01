package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {
		try {
			System.out.println("Hibernate Configuration Initialazing");
			Configuration configuration = new Configuration();
			System.out.println("Hibernate Configuring");
			configuration.configure();
			configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
			configuration.setProperty("hibernate.connection.username", System.getenv("JDBC_DATABASE_USERNAME"));
			configuration.setProperty("hibernate.connection.password", System.getenv("JDBC_DATABASE_PASSWORD"));

			System.out.println("Hibernate Configuration loaded");
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			System.out.println("Hibernate serviceRegistry created");

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			return sessionFactory;
		}
		catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null){
			sessionFactory = buildSessionFactory();
		} 
		return sessionFactory;
	}
}


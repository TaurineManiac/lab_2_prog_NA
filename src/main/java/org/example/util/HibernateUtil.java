package org.example.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.model.Developer;
import org.example.model.Employee;
import org.example.model.Manager;
import org.example.model.Tester;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Log4j2
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            Dotenv dotenv = Dotenv.load();
            String password = dotenv.get("DB_PASSWORD");

            if (password != null && !password.isEmpty()) {
                configuration.setProperty("hibernate.connection.password", password);
                log.info("Database password successfully loaded from .env file.");
            } else {
                log.error("CRITICAL: DB_PASSWORD not found in .env file!");
            }

            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Tester.class);
            configuration.addAnnotatedClass(Developer.class);
            configuration.addAnnotatedClass(Manager.class);

            return configuration.buildSessionFactory();
        }catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}

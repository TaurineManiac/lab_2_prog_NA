package org.example.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.model.Developer;
import org.example.model.Employee;
import org.example.model.Manager;
import org.example.model.Tester;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Log4j2
@Getter
public class HubernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.xml");

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

    
}

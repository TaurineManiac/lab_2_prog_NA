package org.example.repository.impl;

import lombok.extern.log4j.Log4j2;
import org.example.model.Employee;
import org.example.repository.inter.EmployeeRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Log4j2
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public void save(List<Employee> employees) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (Employee employee : employees) {
                session.merge(employee);
            }

            transaction.commit();
        }
        catch(Exception ex) {
            if(transaction!=null){
                transaction.rollback();
            }
            log.error(ex);
        }
    }

    @Override
    public List<Employee> getAll() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employee").list();
        }
    }

    @Override
    public Employee getById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        }
    }

    @Override
    public void deleteById(int id) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if(employee!=null){
                session.remove(employee);

            }
            transaction.commit();
        }
        catch(Exception ex) {
            if(transaction!=null){
                transaction.rollback();
            }
            log.error(ex);

        }
    }
}

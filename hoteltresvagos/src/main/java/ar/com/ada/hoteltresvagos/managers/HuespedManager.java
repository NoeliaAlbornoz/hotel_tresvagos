package ar.com.ada.hoteltresvagos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.hoteltresvagos.entities.*;

public class HuespedManager {

    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Huesped huesped) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(huesped);

        session.getTransaction().commit();
        session.close();
    }

    public Huesped read(int huespedId) {
        Session session = sessionFactory.openSession();

        Huesped huesped = session.get(Huesped.class, huespedId);

        session.close();

        return huesped;
    }

    public Huesped readByDNI(int dni) {
        Session session = sessionFactory.openSession();

        Huesped huesped = session.byNaturalId(Huesped.class).using("dni", dni).load();

        session.close();

        return huesped;
    }

    public void update(Huesped huesped) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(huesped);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Huesped huesped) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(huesped);

        session.getTransaction().commit();
        session.close();
    }

    public List<Huesped> buscarTodos() {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery("SELECT * FROM huesped", Huesped.class);
        
        List<Huesped> todos = query.getResultList();

        return todos;

    }

    public List<Huesped> buscarPor(String nombre) {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery("SELECT * FROM huesped where nombre = ?", Huesped.class);

        query.setParameter(1, nombre);

        List<Huesped> huespedes = query.getResultList();

        return huespedes;

    }

    public List<Huesped> buscarPor(int dni) {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery("SELECT * FROM huesped where dni = ?", Huesped.class);

        query.setParameter(1, dni);

        List<Huesped> huespedes = query.getResultList();

        return huespedes;

    }

}
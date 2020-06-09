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
import ar.com.ada.hoteltresvagos.entities.reportes.Reporte;
import ar.com.ada.hoteltresvagos.entities.reportes.ReporteImportesEstado;
import ar.com.ada.hoteltresvagos.entities.reportes.ReporteImportesHuesped;

public class ReporteManager {

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

    public void create(Reporte reporte) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(reporte);

        session.getTransaction().commit();
        session.close();
    }

    public Reporte read(int reporteId) {
        Session session = sessionFactory.openSession();

        Reporte reporte = session.get(Reporte.class, reporteId);

        session.close();

        return reporte;
    }

    public void update(Reporte reporte) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(reporte);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Reporte reporte) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(reporte);

        session.getTransaction().commit();
        session.close();
    }

    public List<ReporteImportesHuesped> generarPorHuespedId(int huesped_id) {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery
        ("SELECT h.huesped_id, h.nombre,
        count(r.reserva_id) cantidad_reservas, 
        sum(r.importe_reserva) Total_importe_reserva, 
        sum(r.importe_pagado) Total_importe_pagado,
        sum(r.importe_total) Total_importe
        FROM huesped h INNER JOIN reserva r on h.huesped_id = r.huesped_id 
        WHERE h.huesped_id = ?
        GROUP BY h.huesped_id, h.nombre", Reporte.class);

        query.setParameter(1, huesped_id);

        List<ReporteImportesHuesped> reportes = query.getResultList();

        return reportes;

    }

    public List<ReporteImportesHuesped> generarPorHuespedes() {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery
        ("SELECT h.huesped_id, h.nombre, 
        count(*) cantidad_reservas, sum(r.importe_reserva) Total_importe_reserva, 
        sum(r.importe_pagado) Total_importe_pagado,
        sum(r.importe_total) Total_importe
        FROM huesped h INNER JOIN reserva r on h.huesped_id = r.huesped_id
        GROUP BY h.huesped_id, h.nombre", Reporte.class);

        query.setParameter(1, huesped_id);

        List<ReporteImportesHuesped> reportes = query.getResultList();

        return reportes;

    }

    public List<ReporteImportesEstado> generarPorEstadoId(int estado_id) {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery
        ("select r.estado_id,
        count(r.reserva_id) cantidad_reservas,
        sum(r.importe_reserva) Total_importe_reserva, 
        sum(r.importe_pagado) Total_importe_pagado,
        sum(r.importe_total) Total_importe
        FROM huesped h INNER JOIN reserva r on h.huesped_id = r.huesped_id
        where estado_id = ?
        group by e.estado_pago_id", Reporte.class);

        query.setParameter(1, huesped_id);

        List<ReporteImportesEstado> reportes = query.getResultList();

        return reportes;

    }

    public List<ReporteImportesEstado> generarPorEstados() {

        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery
        ("select r.estado_id, 
        count(r.reserva_id) cantidad_reservas,
        sum(r.importe_reserva) Total_importe_reserva, 
        sum(r.importe_pagado) Total_importe_pagado,
        sum(r.importe_total) Total_importe
        FROM huesped h INNER JOIN reserva r on h.huesped_id = r.huesped_id
        group by e.estado_pago_id", Reporte.class);

        query.setParameter(1, huesped_id);

        List<ReporteImportesEstado> reportes = query.getResultList();

        return reportes;

    }

}
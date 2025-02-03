package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class prestamoDAO implements Iprestamo{
    /**
     * @return todos los prestamos
     */
    @Override
    public List<prestamo> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<prestamo> prestamo = session.createQuery("from prestamo", prestamo.class).list();

        session.close();

        return prestamo;
    }

    /**
     * @param id
     * @return prestamos segun id
     */
    @Override
    public prestamo findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        prestamo prestamo2 = session.find(prestamo.class, id);

        session.close();

        return prestamo2;
    }
    /**
     * @param socio
     * @return prestamos segun socio
     */
    @Override
    public List<prestamo> findBySocio(socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<prestamo> prestamo3 = session.createQuery("from prestamo where socio = :socio", prestamo.class)
                .setParameter("socio", socio)
                .list();

        session.close();

        return prestamo3;
    }
}

package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class socioDAO implements Isocio{
    /**
     * @return todos los socios
     */
    @Override
    public List<socio> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<socio> socio = session.createQuery("from socio", socio.class).list();

        session.close();

        return socio;
    }

    /**
     * @param id
     * @return socios segun id
     */
    @Override
    public socio findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio2 = session.find(socio.class, id);

        session.close();

        return socio2;
    }
    /**
     * @param nombre
     * @return socios segun nombre
     */
    @Override
    public socio findByNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio3 = session.find(socio.class, nombre);

        session.close();

        return socio3;
    }
    /**
     * @param nTel
     * @return socios segun numero telefono
     */
    @Override
    public socio findByTel(Integer nTel) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio4 = session.find(socio.class, nTel);

        session.close();

        return socio4;
    }
}

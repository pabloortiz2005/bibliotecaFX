package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class socioDAO implements Isocio{
    /**
     * @return todos los libros
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
     * @return libros segun id
     */
    @Override
    public socio findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio2 = session.find(socio.class, id);

        session.close();

        return socio2;
    }
}

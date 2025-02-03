package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class libroDAO implements Ilibro {
    /**
     * @return todos los libros
     */
    @Override
    public List<libro> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<libro> libro = session.createQuery("from libro", libro.class).list();

        session.close();

        return libro;
    }

    /**
     * @param id
     * @return libros segun id
     */
    @Override
    public libro findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        libro libro2 = session.find(libro.class, id);

        session.close();

        return libro2;
    }
}

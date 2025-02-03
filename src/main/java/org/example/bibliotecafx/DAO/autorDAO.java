package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class autorDAO implements Iautor{
    /**
     * @return todos los autores
     */
    @Override
    public List<autor> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<autor> autor = session.createQuery("from autor", autor.class).list();

        session.close();

        return autor;
    }

    /**
     * @param id
     * @return autores segun id
     */
    @Override
    public autor findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        autor autor2 = session.find(autor.class, id);

        session.close();

        return autor2;
    }
}

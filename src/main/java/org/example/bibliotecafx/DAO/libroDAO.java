package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.autor;
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
    /**
     * @param ISBN
     * @return libros segun ISBN
     */
    @Override
    public libro findByISBN(String ISBN) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        libro libro3 = session.find(libro.class, ISBN);

        session.close();

        return libro3;
    }
    /**
     * @param autor
     * @return libros segun autor
     */
    @Override
    public  List<libro> findByAutor(autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<libro> libro4 = session.createQuery("from libro where autor = :autor", libro.class)
                .setParameter("autor", autor)
                .list();

        session.close();

        return libro4;
    }
    /**
     * @param titulo
     * @return libros segun titulo
     */
    @Override
    public libro findByTitulo(String titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        libro libro5 = session.find(libro.class, titulo);

        session.close();

        return libro5;
    }

}

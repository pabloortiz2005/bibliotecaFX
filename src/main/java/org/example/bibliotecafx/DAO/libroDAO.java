package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    /**
     * @param id
     * @return borra un libro segun id
     */
    @Override
    public void deleteById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Iniciar una transacción
        Transaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();

            // Buscar el libro por su ID
            libro libro = session.get(libro.class, id);

            // Verificar si el libro fue encontrado
            if (libro != null) {
                // Eliminar el libro
                session.delete(libro);
                System.out.println("El libro con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontró un libro con el ID proporcionado.");
            }

            // Hacer commit de la transacción
            transaction.commit();
        } catch (Exception e) {
            // Si hay algún error, hacer rollback
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar la sesión
            session.close();
        }
    }

}

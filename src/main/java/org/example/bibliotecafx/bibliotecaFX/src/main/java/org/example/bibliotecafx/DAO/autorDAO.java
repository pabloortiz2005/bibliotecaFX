package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

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
    /**
     * @param nombre
     * @return autores segun nombre
     */
    @Override
    public autor findByNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        autor autor3 = session.find(autor.class, nombre);

        session.close();

        return autor3;
    }
    /**
     * @param id
     * @return borra un autor segun id
     */
    @Override
    public void deleteById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Iniciar una transacción
        Transaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();

            // Buscar el autor por su ID
            autor autor = session.get(autor.class, id);

            // Verificar si el autor fue encontrado
            if (autor != null) {
                // Eliminar el autor
                session.delete(autor);
                System.out.println("El autor con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontró un autor con el ID proporcionado.");
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
    /**
     * @param autor
     * @return Cambiar autor
     */
    @Override
    public autor ChangeAutor(autor autor, String nombre, String nacionalidad) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            autor autorExistente = session.find(autor.class, autor.getIdA());

            // Actualizar los datos del autor existente
            autorExistente.setNombre(nombre);
            autorExistente.setNacionalidad(nacionalidad);


            // Guardar los cambios realizados en el autor
            session.update(autorExistente);
            transaction.commit();
            System.out.println("El autor con ID " + autorExistente.getIdA() + " ha sido actualizado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return autor;
    }

/**
 * @param autor
 * @return Crear autor
 */

@Override
public autor create(autor autor) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        session.save(autor);
        transaction.commit();
        System.out.println("El autor se ha creado correctamente.");
    } catch (Exception e) {
        if (transaction != null) transaction.rollback();
        e.printStackTrace();
    } finally {
        session.close();
    }

    return autor;
}
}


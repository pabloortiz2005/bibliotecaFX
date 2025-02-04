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
     * @param id
     * @return Cambiar autor
     */
    @Override
    public autor ChangeAutor(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        autor autor4 = session.find(autor.class, id);

        if (autor4 != null) {

            System.out.println("Inserte los datos del autor que quiere modificar");
            Scanner scanner = new Scanner(System.in);
            Scanner scanner2 = new Scanner(System.in);

            System.out.print("Nombre: ");
            autor4.setNombre(scanner.nextLine());

            System.out.print("Nacionalidad: ");
            autor4.setNacionalidad(scanner.nextLine());

            // Iniciar la transacción y actualizar el Autor
            session.beginTransaction();
            session.update(autor4);
            session.getTransaction().commit();

        } else {
            System.out.println("No se encontró un autor con el ID especificado.");
        }

        session.close();
        return autor4;
    }
    /**
     * @param autor
     * @return Crear autor
     */

    @Override
    public autor create(autor autor) {
        Scanner scanner = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();

        autor autor5 = new autor();

        System.out.println("Nombre");
        autor5.setNombre(scanner.nextLine());

        System.out.println("Nacionalidad: ");
        autor5.setNacionalidad(scanner.nextLine());

        return autor5;
    }

}

package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class libroDAO implements Ilibro {
    /**
     * @return todos los libros disponibles
     */
    @Override
    public List<libro> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta que obtiene los libros que no están en préstamo
        List<libro> librosDisponibles = session.createQuery(
                        "SELECT l FROM libro l WHERE NOT EXISTS (" +
                                "SELECT p FROM prestamo p WHERE p.libroP = l AND p.finalizado = false" +
                                ")", libro.class)
                .list();

        session.close();

        return librosDisponibles;
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
    /**
     * @param id
     * @return Cambiar libro
     */
    @Override
    public libro ChangeLibro(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        libro libro6 = session.find(libro.class, id);

        if (libro6 != null) {

            autorDAO autorDAO = new autorDAO();

            System.out.println("Inserte los datos del libro que quiere modificar");
            Scanner scanner = new Scanner(System.in);
            Scanner scanner2 = new Scanner(System.in);

            System.out.print("Titulo: ");
            libro6.setTitulo(scanner.nextLine());

            System.out.print("ISBN: ");
            libro6.setISBN(scanner.nextLine());


            System.out.print("Nombre del autor: ");
            String autorNombre = scanner.nextLine();
            autor autor = autorDAO.findByNombre(autorNombre);

            // Verificar si el autor fue encontrado antes de asignarlo al libro
            if (autor != null) {
                libro6.setAutor(autor);
            } else {
                System.out.println("No se encontró un autor con ese nombre.");
            }

            System.out.print("Editorial: ");
            libro6.setEditorial(scanner.nextLine());

            System.out.print("Año de publicación: ");
            libro6.setAnyoPub(scanner2.nextInt());

            // Iniciar la transacción y actualizar el libro
            session.beginTransaction();
            session.update(libro6);
            session.getTransaction().commit();

        } else {
            System.out.println("No se encontró un libro con el ID especificado.");
        }

        session.close();
        return libro6;
    }
    /**
     * @param libro
     * @return Crear prestamo
     */
    @Override
    public libro create(libro libro) {

        autorDAO autorDAO = new autorDAO();

        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();

        libro libro7 = new libro();

        System.out.print("Nombre del autor: ");
        String autorNombre = scanner.nextLine();
        autor autor = autorDAO.findByNombre(autorNombre);

        // Verificar si el autor fue encontrado antes de asignarlo al libro
        if (autor != null) {
            libro7.setAutor(autor);
        } else {
            System.out.println("No se encontró un autor con ese nombre.");
        }


        System.out.print("ISBN: ");
        libro7.setISBN(scanner.nextLine());
        System.out.print("Titulo: ");
        libro7.setTitulo(scanner.nextLine());
        System.out.print("Editorial: ");
        libro7.setEditorial(scanner.nextLine());
        System.out.print("Año de publicación: ");
        libro7.setAnyoPub(scanner2.nextInt());

        return libro7;
    }


}

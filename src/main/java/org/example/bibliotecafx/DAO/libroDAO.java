package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.bibliotecafx.DAO.autorDao;

import java.util.List;

public class libroDAO implements Ilibro {

    /**
     * Encuentra todos los libros disponibles (que no están en préstamo).
     *
     * @return Lista de libros disponibles.
     */
    @Override
    public List<libro> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<libro> librosDisponibles = session.createQuery(
                        "SELECT l FROM libro l WHERE NOT EXISTS (" +
                                "SELECT p FROM prestamo p WHERE p.libroP = l AND p.finalizado = false" +
                                ")", libro.class)
                .list();
        session.close();
        return librosDisponibles;
    }

    /**
     * Busca un libro por su ID.
     *
     * @param id ID del libro a buscar.
     * @return El libro con el ID especificado.
     */
    @Override
    public libro findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        libro libroEncontrado = session.find(libro.class, id);
        session.close();
        return libroEncontrado;
    }

    /**
     * Busca un libro por su ISBN.
     *
     * @param ISBN El ISBN del libro a buscar.
     * @return El libro con el ISBN especificado.
     */
    @Override
    public libro findByISBN(String ISBN) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        libro libroPorISBN = session.createQuery("FROM libro WHERE ISBN = :ISBN", libro.class)
                .setParameter("ISBN", ISBN)
                .uniqueResult();
        session.close();
        return libroPorISBN;
    }

    /**
     * Busca libros por un autor.
     *
     * @param autor Objeto autor que se desea buscar.
     * @return Lista de libros del autor especificado.
     */
    @Override
    public List<libro> findByAutor(autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<libro> librosPorAutor = session.createQuery("FROM libro WHERE autor = :autor", libro.class)
                .setParameter("autor", autor)
                .list();
        session.close();
        return librosPorAutor;
    }

    /**
     * Busca un libro por su título.
     *
     * @param titulo El título del libro a buscar.
     * @return El libro con el título especificado.
     */
    @Override
    public libro findByTitulo(String titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        libro libroPorTitulo = session.createQuery("FROM libro WHERE titulo = :titulo", libro.class)
                .setParameter("titulo", titulo)
                .uniqueResult();
        session.close();
        return libroPorTitulo;
    }

    @Override
    public List<libro> buscarLibro(String titulo, String autor, String isbn) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        String query = "FROM libro L WHERE (:titulo IS NULL OR L.titulo LIKE :titulo) "
                + "AND (:autor IS NULL OR L.autor.nombre LIKE :autor) "
                + "AND (:isbn IS NULL OR L.ISBN LIKE :isbn)";

        List<libro> libros = session.createQuery(query, libro.class)
                .setParameter("titulo", titulo.isEmpty() ? null : "%" + titulo + "%")
                .setParameter("autor", autor.isEmpty() ? null : "%" + autor + "%")
                .setParameter("isbn", isbn.isEmpty() ? null : "%" + isbn + "%")
                .list();

        session.close();
        return libros;
    }
    /**
     * Elimina un libro por su ID.
     *
     * @param id El ID del libro a eliminar.
     */
    @Override
    public void deleteById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            libro libro = session.find(libro.class, id);
            if (libro != null) {
                session.delete(libro);
                System.out.println("El libro con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontró un libro con el ID proporcionado.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Modifica un libro existente.
     *
     * @param libro Objeto libro con los datos actualizados.
     * @return El libro modificado.
     */
    @Override
    public libro ChangeLibro(libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si el libro ya existe por su ID
            libro libroExistente = session.find(libro.class, libro.getIdL());
            if (libroExistente == null) {
                System.out.println("No se encontró un libro con el ID proporcionado para actualizar.");
                return null;
            }

            // Actualizar los datos del libro existente
            libroExistente.setTitulo(libro.getTitulo());
            libroExistente.setISBN(libro.getISBN());
            libroExistente.setEditorial(libro.getEditorial());
            libroExistente.setAnyoPub(libro.getAnyoPub());

            // Manejo del autor si cambia
            autor autor = libro.getAutor();
            if (autor != null) {
                autorDAO autorDAO = new autorDAO();
                autor autorExistente = autorDAO.findByNombre(autor.getNombre());
                if (autorExistente == null) {
                    // Crear el autor si no existe y asignarlo
                    autorDAO.create(autor);
                    libroExistente.setAutor(autor);
                } else {
                    // Asociar el autor existente
                    libroExistente.setAutor(autorExistente);
                }
            }

            // Guardar los cambios realizados en el libro
            session.update(libroExistente);
            transaction.commit();
            System.out.println("El libro con ID " + libroExistente.getIdL() + " ha sido actualizado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return libro;
    }

    /**
     * Crea un nuevo libro en la base de datos.
     *
     * @param libro Objeto libro con los datos a insertar.
     * @return El libro creado.
     */
    @Override
    public libro create(libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            autor autor = libro.getAutor();

            if (autor != null) {
                autorDAO autorDAO = new autorDAO();
                autor autorExistente = autorDAO.findByNombre(autor.getNombre());
                if (autorExistente == null) {
                    autorDAO.create(autor); // Creamos el autor si no existe.
                } else {
                    libro.setAutor(autorExistente); // Asociamos el autor existente con el libro.
                }
            }

            session.save(libro);
            transaction.commit();
            System.out.println("El libro se ha creado correctamente.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return libro;
    }
}
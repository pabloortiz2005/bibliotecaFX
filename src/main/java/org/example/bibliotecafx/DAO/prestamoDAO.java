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

public class prestamoDAO implements Iprestamo{
    /**
     * @return todos los prestamos
     */
    @Override
    public List<prestamo> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<prestamo> prestamo = session.createQuery("from prestamo", prestamo.class).list();

        session.close();

        return prestamo;
    }
    /**
     * @return todos los prestamos activos
     */
    @Override
    public List<prestamo> findAllPrestados() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<prestamo> prestamo = session.createQuery("from prestamo where finalizado = false", prestamo.class).list();

        session.close();

        return prestamo;
    }

    /**
     * @param id
     * @return prestamos segun id
     */
    @Override
    public prestamo findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        prestamo prestamo2 = session.find(prestamo.class, id);

        session.close();

        return prestamo2;
    }
    /**
     * @param socio
     * @return prestamos segun socio
     */
    @Override
    public List<prestamo> findBySocio(socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<socio> prestamosSocio = session.createQuery("FROM prestamo WHERE socio = :socio", prestamo.class)
                .setParameter("socio", socio)
                .list();
        session.close();
        return prestamosSocio;
    }
    @Override
    /**
     * @param id
     * @return Cambiar estado del prestamo
     */
    public prestamo devolverPrestamo(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();


        prestamo prestamo4 = session.find(prestamo.class, id);


        if (prestamo4 != null) {
           prestamo4.setFinalizado(true);
            session.beginTransaction();
            session.update(prestamo4);
            session.getTransaction().commit();

        } else {
            System.out.println("No se encontró un prestamo con el id especificado.");
        }

        session.close();
        return prestamo4;
    }
    /**
     * @param prestamo
     * @return Crear prestamo
     */
    @Override
    public prestamo create(prestamo prestamo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Instancias de DAO para verificar la existencia de libro y socio
            libroDAO libroDAO = new libroDAO();
            socioDAO socioDAO = new socioDAO();

            // Verificar si el libro existe
            libro libro = prestamo.getLibroP();
            if (libro != null) {
                libro libroExistente = libroDAO.findByTitulo(libro.getTitulo());
                if (libroExistente == null) {
                    System.out.println("No se encontró un libro con ese título.");
                    return null;
                } else {
                    prestamo.setLibroP(libroExistente);
                }
            }

            // Verificar si el socio existe
            socio socio = prestamo.getSocioP();
            if (socio != null) {
                socio socioExistente = socioDAO.findByNombre(socio.getNombre());
                if (socioExistente == null) {
                    System.out.println("No se encontró un socio con ese nombre.");
                    return null;
                } else {
                    prestamo.setSocioP(socioExistente);
                }
            }
            session.save(prestamo);
            transaction.commit();
            System.out.println("El préstamo se ha creado correctamente.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return prestamo;
    }


}

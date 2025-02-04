package org.example.bibliotecafx.DAO;


import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;

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

        List<prestamo> prestamo3 = session.createQuery("from prestamo where socio = :socio", prestamo.class)
                .setParameter("socio", socio)
                .list();

        session.close();

        return prestamo3;
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

        libroDAO libroDAO = new libroDAO();
        socioDAO socioDAO = new socioDAO();

        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();

        prestamo prestamo5 = new prestamo();

        System.out.print("Titulo del libro: ");
        String titulo = scanner.nextLine();
        libro libro = libroDAO.findByISBN(titulo);

        if (libro != null) {
            prestamo5.setLibroP(libro);
        } else {
            System.out.println("No se encontró un libro con ese titulo.");
        }

        System.out.print("Nombre del socio: ");
        String nombre = scanner.nextLine();
        socio socio = socioDAO.findByNombre(nombre);

        if (socio != null) {
            prestamo5.setSocioP(socio);
        } else {
            System.out.println("No se encontró un socio con ese nombre.");
        }

        System.out.print("Fecha de inicio: ");
        prestamo5.setfP(scanner.nextLine());
        System.out.print("Fecha de finalizacion");
        prestamo5.setfD(scanner.nextLine());

        return prestamo5;
    }


}

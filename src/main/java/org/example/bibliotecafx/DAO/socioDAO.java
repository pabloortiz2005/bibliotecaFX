package org.example.bibliotecafx.DAO;



import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.socio;
import org.example.bibliotecafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class socioDAO implements Isocio{
    /**
     * @return todos los socios
     */
    @Override
    public List<socio> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<socio> socio = session.createQuery("from socio", socio.class).list();

        session.close();

        return socio;
    }

    /**
     * @param id
     * @return socios segun id
     */
    @Override
    public socio findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio2 = session.find(socio.class, id);

        session.close();

        return socio2;
    }
    /**
     * @param nombre
     * @return socios segun nombre
     */
    @Override
    public socio findByNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio3 = session.find(socio.class, nombre);

        session.close();

        return socio3;
    }
    /**
     * @param nTel
     * @return socios segun numero telefono
     */
    @Override
    public socio findByTel(Integer nTel) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio4 = session.find(socio.class, nTel);

        session.close();

        return socio4;
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

            // Buscar el socio por su ID
            socio socio = session.get(socio.class, id);

            // Verificar si el socio fue encontrado
            if (socio != null) {
                // Eliminar el socio
                session.delete(socio);
                System.out.println("El socio con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontró un socio con el ID proporcionado.");
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
    public socio ChangeSocio(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        socio socio5 = session.find(socio.class, id);

        if (socio5 != null) {


            System.out.println("Inserte los datos del socio5 que quiere modificar");
            Scanner scanner = new Scanner(System.in);
            Scanner scanner2 = new Scanner(System.in);

            System.out.print("Nombre: ");
            socio5.setNombre(scanner.nextLine());

            System.out.print("Dirección: ");
            socio5.setDireccion(scanner.nextLine());

            System.out.print("Numero de telefono: ");
            socio5.setnTel(scanner2.nextInt());

            // Iniciar la transacción y actualizar el Autor
            session.beginTransaction();
            session.update(socio5);
            session.getTransaction().commit();

        } else {
            System.out.println("No se encontró un socio con el ID especificado.");
        }

        session.close();
        return socio5;
    }
    /**
     * @param socio
     * @return Crear socio
     */

    @Override
    public socio create(socio socio) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();

        socio socio6 = new socio();

        System.out.println("Nombre");
        socio6.setNombre(scanner.nextLine());

        System.out.println("Direccion: ");
        socio6.setDireccion(scanner.nextLine());

        System.out.println("Numero de telefono: ");
        socio6.setnTel(scanner2.nextInt());

        return socio6;
    }
}

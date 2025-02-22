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
    public List<socio> findByNombre(String nombre) {
        List<socio> sociosEncontrados;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            sociosEncontrados = session.createQuery("FROM socio WHERE nombre LIKE :nombre", socio.class)
                    .setParameter("nombre", "%" + nombre + "%") // Permite buscar coincidencias parciales
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sociosEncontrados;
    }
    /**
     * @param nTel
     * @return socios segun numero telefono
     */
    @Override
    public List<socio> findByTel(Integer nTel) {
        List<socio> sociosEncontrados;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            sociosEncontrados = session.createQuery("FROM socio WHERE nTel = :nTel", socio.class)
                    .setParameter("nTel", nTel)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sociosEncontrados;
    }
    /**
     * @param id
     * @return borra un socio segun id
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
     * @param socio
     * @return Cambiar socio
     */
    @Override
    public socio ChangeSocio(socio socio, String nombre,String direccion, Integer NTel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            socio socioExistente = session.find(socio.class, socio.getIdS());

            // Actualizar los datos del socio existente
            socioExistente.setNombre(nombre);
            socioExistente.setDireccion(direccion);
            socioExistente.setnTel(NTel);


            // Guardar los cambios realizados en el socio
            session.update(socioExistente);
            transaction.commit();
            System.out.println("El autor con ID " + socioExistente.getIdS() + " ha sido actualizado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return socio;
    }
    /**
     * @param socio
     * @return Crear socio
     */

    @Override
    public socio create(socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(socio);
            transaction.commit();
            System.out.println("El autor se ha creado correctamente.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return socio;
    }
}

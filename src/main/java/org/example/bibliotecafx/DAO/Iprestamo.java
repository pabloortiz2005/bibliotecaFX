package org.example.bibliotecafx.DAO;



import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;

import java.util.List;

public interface Iprestamo {
    public List<prestamo> findAll();
    public List<prestamo> findAllPrestados();
    public prestamo findById(Integer id);
    public List<prestamo> findBySocio(socio socio);
    public prestamo devolverPrestamo(Integer id);
    public prestamo create(prestamo prestamo);
}

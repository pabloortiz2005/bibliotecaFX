package org.example.bibliotecafx.DAO;



import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;

import java.util.List;

public interface Iprestamo {
    public List<prestamo> findAll();
    public prestamo findById(Integer id);
    public List<prestamo> findBySocio(socio socio);
}

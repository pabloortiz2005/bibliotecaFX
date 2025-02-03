package org.example.bibliotecafx.DAO;



import org.example.bibliotecafx.entities.socio;

import java.util.List;

public interface Isocio {
    public List<socio> findAll();
    public socio findById(Integer id);
}

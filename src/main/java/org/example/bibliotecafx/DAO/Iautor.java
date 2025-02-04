package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.autor;

import java.util.List;

public interface Iautor {
    public List<autor> findAll();
    public autor findById(Integer id);
    public autor findByNombre(String nombre);
    public void deleteById(Integer id);
}

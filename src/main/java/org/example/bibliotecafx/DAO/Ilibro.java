package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;

import java.util.List;

public interface Ilibro {
    public List<libro> findAll();
    public libro findById(Integer id);
    public libro findByISBN(String ISBN);
    public List<libro> findByAutor(autor autor);
    public libro findByTitulo(String titulo);
}

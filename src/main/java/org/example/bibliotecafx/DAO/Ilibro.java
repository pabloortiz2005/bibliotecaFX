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
    public void deleteById(Integer id);
    public libro ChangeLibro(Integer id);
    public libro create(libro libro);
    List<libro> buscarLibro(String titulo, String autor, String isbn);
}

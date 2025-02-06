package org.example.bibliotecafx.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class libro implements Serializable {
    // título, ISBN, autor, editorial y año de publicación
    String titulo;
    String ISBN;
    autor autor;
    String editorial;
    int anyoPub;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idL;

    public libro(String titulo, String ISBN, org.example.bibliotecafx.entities.autor autor, String editorial, int anyoPub) {
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.autor = autor;
        this.editorial = editorial;
        this.anyoPub = anyoPub;
    }

    public libro() {
    }

    public int getAnyoPub() {
        return anyoPub;
    }

    public void setAnyoPub(int anyoPub) {
        this.anyoPub = anyoPub;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public org.example.bibliotecafx.entities.autor getAutor() {
        return autor;
    }

    public void setAutor(org.example.bibliotecafx.entities.autor autor) {
        this.autor = autor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdL() {
        return idL;
    }

    public void setIdL(Integer idL) {
        this.idL = idL;
    }

    @Override
    public String toString() {
        return "libro{" +
                "titulo='" + titulo + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", autor=" + autor +
                ", editorial='" + editorial + '\'' +
                ", anyoPub=" + anyoPub +
                ", idL=" + idL +
                '}';
    }
}


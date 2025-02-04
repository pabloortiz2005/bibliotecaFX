package org.example.bibliotecafx.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class autor {
// nombre , nacionalidad e id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idA;

    String nombre;
    String nacionalidad;


    public autor(String nombre, String nacionalidad, Integer idA) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.idA = idA;
    }

    public autor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(Integer idA) {
        this.idA = idA;
    }

    @Override
    public String toString() {
        return "autor{" +
                "idA=" + idA +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}

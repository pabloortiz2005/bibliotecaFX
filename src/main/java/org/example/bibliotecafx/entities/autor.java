package org.example.bibliotecafx.entities;

public class autor {
// nombre , nacionalidad e id
    int idA;
    String nombre;
    String nacionalidad;


    public autor(String nombre, String nacionalidad, int idA) {
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

    public void setIdA(int idA) {
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

package org.example.bibliotecafx.entities;

public class autor {
// nombre y nacionalidad
    String nombre;
    String Nacionalidad;


    public autor(String nombre, String nacionalidad) {
        this.nombre = nombre;
        Nacionalidad = nacionalidad;
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
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "autor{" +
                "nombre='" + nombre + '\'' +
                ", Nacionalidad='" + Nacionalidad + '\'' +
                '}';
    }
}

package org.example.bibliotecafx.entities;

public class socio {
    // nombre, dirección y número de teléfono

    String nombre;
    String direccion;
    int nTel;

    public socio(String nombre, String direccion, int nTel) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.nTel = nTel;
    }

    public socio() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getnTel() {
        return nTel;
    }

    public void setnTel(int nTel) {
        this.nTel = nTel;
    }

    @Override
    public String toString() {
        return "socio{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", nTel=" + nTel +
                '}';
    }
}

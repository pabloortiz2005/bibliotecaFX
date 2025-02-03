package org.example.bibliotecafx.entities;

public class prestamo {
    // préstamos de libros a socios, especificando la fecha de préstamo y la fecha de devolución

    libro libroP;
    socio socioP;
    String fP;
    String fD;
    int idP;

    public prestamo(String fD, String fP, socio socioP, libro libroP, int idP) {
        this.fD = fD;
        this.fP = fP;
        this.socioP = socioP;
        this.libroP = libroP;
        this.idP = idP;
    }

    public prestamo() {
    }

    public libro getLibroP() {
        return libroP;
    }

    public void setLibroP(libro libroP) {
        this.libroP = libroP;
    }

    public socio getSocioP() {
        return socioP;
    }

    public void setSocioP(socio socioP) {
        this.socioP = socioP;
    }

    public String getfP() {
        return fP;
    }

    public void setfP(String fP) {
        this.fP = fP;
    }

    public String getfD() {
        return fD;
    }

    public void setfD(String fD) {
        this.fD = fD;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    @Override
    public String toString() {
        return "prestamo{" +
                "libroP=" + libroP +
                ", socioP=" + socioP +
                ", fP='" + fP + '\'' +
                ", fD='" + fD + '\'' +
                ", idP=" + idP +
                '}';
    }
}

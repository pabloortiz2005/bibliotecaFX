package org.example.bibliotecafx.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class prestamo implements Serializable {
    // préstamos de libros a socios, especificando la fecha de préstamo y la fecha de devolución


    //para la relacion
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private libro libroP;

    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false)
    private socio socioP;

    LocalDate fP;
    LocalDate fD;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idP;

    boolean finalizado=false;

    public prestamo(LocalDate fD, LocalDate fP, socio socioP, libro libroP, boolean finalizado) {
        this.fD = fD;
        this.fP = fP;
        this.socioP = socioP;
        this.libroP = libroP;
        this.finalizado = finalizado;
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

    public LocalDate getfP() {
        return fP;
    }

    public void setfP(LocalDate fP) {
        this.fP = fP;
    }

    public LocalDate getfD() {
        return fD;
    }

    public void setfD(LocalDate fD) {
        this.fD = fD;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(Integer idP) {
        this.idP = idP;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    @Override
    public String toString() {
        return "prestamo{" +
                "libroP=" + libroP +
                ", socioP=" + socioP +
                ", fP='" + fP + '\'' +
                ", fD='" + fD + '\'' +
                ", idP=" + idP +
                ", finalizado=" + finalizado +
                '}';
    }
}

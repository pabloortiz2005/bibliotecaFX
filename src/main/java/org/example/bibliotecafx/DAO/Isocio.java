package org.example.bibliotecafx.DAO;



import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.socio;

import java.util.List;

public interface Isocio {
    public List<socio> findAll();
    public socio findById(Integer id);
    public socio findByNombre(String nombre);
    public socio findByTel(Integer nTel);
    public void deleteById(Integer id);
    public socio ChangeSocio(socio socio,  String nombre, String direccion, Integer NTel);
    public socio create(socio socio);
}

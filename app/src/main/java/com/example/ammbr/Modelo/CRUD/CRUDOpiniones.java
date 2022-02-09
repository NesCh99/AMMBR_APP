/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.CRUD;

import com.example.ammbr.Modelo.Dao.OpinionesDao;
import com.example.ammbr.Modelo.Dao.OpinionesDaoImpl;
import com.example.ammbr.Modelo.Opiniones;

import java.util.List;

/**
 *
 * @author Nes Ch
 */
public class CRUDOpiniones {
    
    OpinionesDao OpinionesDao = new OpinionesDaoImpl();
    Opiniones Opiniones = new Opiniones();
    
    public boolean nuevaOpinion(int estrellas, String comentario, String idhospedaje, String emailhuesped){
        Opiniones.setEstrellas(estrellas);
        Opiniones.setComentario(comentario.toUpperCase());        
        Opiniones.setIdHospedaje(idhospedaje.toUpperCase());
        Opiniones.setEmailHuesped(emailhuesped);
        return OpinionesDao.save(Opiniones);
    }
    
    public void editarOpinion(int estrellas, String comentario, int idopinion, String emailhuesped){
        Opiniones.setIdOpinion(idopinion);        
        Opiniones.setEstrellas(estrellas);
        Opiniones.setComentario(comentario.toUpperCase());
        Opiniones.setEmailHuesped(emailhuesped.toUpperCase());
        OpinionesDao.editar(Opiniones);
    }
    
    public void eliminarOpinion(int idOpinion, String emailhuesped){
        OpinionesDao.delete(idOpinion, emailhuesped);
    }

    public List<Opiniones> listarOpiniones(String idhospedaje){
        List<Opiniones> list = OpinionesDao.listarxHospedaje(idhospedaje);
        return list;
    }

    public double promCalif(String idHospedaje){
        double prom=0;
        try{
            prom = OpinionesDao.promedio(idHospedaje);
        }catch (Exception e){
            System.out.println("HUBO UN ERROR CONTANDO");
        }

        return prom;
    }
    
}

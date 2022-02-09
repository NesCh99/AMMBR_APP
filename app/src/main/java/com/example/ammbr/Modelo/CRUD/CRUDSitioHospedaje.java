/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.CRUD;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.ammbr.Modelo.Dao.SitioHospedajeDaoImpl;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDao;
import com.example.ammbr.Modelo.Negocio.Seguridad.ControlIngresoDatos;
import com.example.ammbr.Modelo.SitioHospedaje;
import java.util.List;

/**
 *
 * @author Fabian
 */
public class CRUDSitioHospedaje {

    SitioHospedajeDao SitioHospedajeDao = new SitioHospedajeDaoImpl();
    SitioHospedaje sitioHospedaje = new SitioHospedaje();
    ControlIngresoDatos control = new ControlIngresoDatos();

    public void guardarSitioHospedaje(String idHospedaje,
            String emailAdministrador,
            String portada,
            String nombre,
            String latitud,
            String longitud,
            String descripcion,
            String telefono,
            String sitioWebURL,
            String fanPageURL,
            String instagramURL,
            int pagoEfectivo,
            int pagoElectronico,
            int pagoTarjeta,
            int categoria) {
            sitioHospedaje.setEmailAdministrador(emailAdministrador);
            sitioHospedaje.setPortada(portada);
            sitioHospedaje.setIdHospedaje(idHospedaje.toUpperCase());
            sitioHospedaje.setNombre(nombre.toUpperCase());
            sitioHospedaje.setLatitud(latitud);
            sitioHospedaje.setLongitud(longitud);
            sitioHospedaje.setDescripcion(descripcion.toUpperCase());
            sitioHospedaje.setTelefono(telefono);
            sitioHospedaje.setSitioWebURL(sitioWebURL.toLowerCase());
            sitioHospedaje.setFanPageURL(fanPageURL.toLowerCase());
            sitioHospedaje.setInstagramURL(instagramURL.toLowerCase());
            sitioHospedaje.setPagoEfectivo(pagoEfectivo);
            sitioHospedaje.setPagoElectronico(pagoElectronico);
            sitioHospedaje.setPagoTarjeta(pagoTarjeta);
            sitioHospedaje.setCategoria(categoria);
            SitioHospedajeDao.save(sitioHospedaje);
    }

    public void editarSitioHospedaje(String idHospedaje,
                                     String emailAdministrador,
                                     String portada,
                                     String nombre,
                                     String latitud,
                                     String longitud,
                                     String descripcion,
                                     String telefono,
                                     String sitioWebURL,
                                     String fanPageURL,
                                     String instagramURL,
                                     int pagoEfectivo,
                                     int pagoElectronico,
                                     int pagoTarjeta,
                                     int categoria) {
        sitioHospedaje.setEmailAdministrador(emailAdministrador);
        sitioHospedaje.setPortada(portada);
        sitioHospedaje.setIdHospedaje(idHospedaje.toUpperCase());
        sitioHospedaje.setNombre(nombre.toUpperCase());
        sitioHospedaje.setLatitud(latitud);
        sitioHospedaje.setLongitud(longitud);
        sitioHospedaje.setDescripcion(descripcion.toUpperCase());
        sitioHospedaje.setTelefono(telefono);
        sitioHospedaje.setSitioWebURL(sitioWebURL.toLowerCase());
        sitioHospedaje.setFanPageURL(fanPageURL.toLowerCase());
        sitioHospedaje.setInstagramURL(instagramURL.toLowerCase());
        sitioHospedaje.setPagoEfectivo(pagoEfectivo);
        sitioHospedaje.setPagoElectronico(pagoElectronico);
        sitioHospedaje.setPagoTarjeta(pagoTarjeta);
        sitioHospedaje.setCategoria(categoria);
        SitioHospedajeDao.save(sitioHospedaje);

    }

    public void eliminarSitioHospedaje(String idHospedaje) {
        SitioHospedajeDao.delete(idHospedaje);

    }

    public List<SitioHospedaje> listarSitiosHospedaje() {
        List<SitioHospedaje> list = SitioHospedajeDao.list();
        return list;
    }

    public SitioHospedaje listarSitioHospedaje(String idSitioHospedaje){
        SitioHospedaje sitio;
        sitio = SitioHospedajeDao.edit(idSitioHospedaje);
        return sitio;
    }

    public SitioHospedaje listarSitioxAdmin(String emailadministrador){
        SitioHospedaje sitio;
        sitio = SitioHospedajeDao.SitioxAdmin(emailadministrador);
        return sitio;
    }

    public List<SitioHospedaje> listarNombre(String nombre) {
         List<SitioHospedaje> list = SitioHospedajeDao.searchName(nombre);
        return list;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.CRUD;

import com.example.ammbr.Modelo.Dao.HabitacionDao;
import com.example.ammbr.Modelo.Dao.HabitacionDaoImpl;
import com.example.ammbr.Modelo.Habitacion;
import com.example.ammbr.Modelo.Negocio.Seguridad.ControlIngresoDatos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nes Ch
 */
public class CRUDHabitacion {

    HabitacionDao HabitacionDao = new HabitacionDaoImpl();
    Habitacion habitacion = new Habitacion();
    ControlIngresoDatos control = new ControlIngresoDatos();

    public void nuevaHabitacion(String categoriahabitacion, String idhospedaje, int numerocamas, String descripcion, double precio) {
            habitacion.setCategoriaHabitacion(categoriahabitacion.toUpperCase());
            habitacion.setIdHospedaje(idhospedaje.toUpperCase());
            habitacion.setDescripcion(descripcion.toUpperCase());
            habitacion.setNumeroCamas(numerocamas);
            habitacion.setPrecio(precio);
            HabitacionDao.save(habitacion);

    }

    public void modificarHabitacion(String idhabitacion, String categoriahabitacion, String descripcion, int numerocamas, double precio) {
        habitacion.setCategoriaHabitacion(categoriahabitacion.toUpperCase());
        habitacion.setDescripcion(descripcion.toUpperCase());
        habitacion.setNumeroCamas(numerocamas);
        habitacion.setPrecio(precio);
        HabitacionDao.save(habitacion);
    }
    
    public void eliminarHabitacion(String idhabitacion){
        HabitacionDao.delete(idhabitacion.toUpperCase());
    }

    public List<Habitacion> listarHabitaciones(String idHospedaje){
        List<Habitacion> list = HabitacionDao.listarSitioHospedaje(idHospedaje);
        return list;
    }

    public boolean existeHabitacion(String idHospedaje){
        boolean band = false;
        if(HabitacionDao.ExisteHabitacion(idHospedaje)==true){
            band=true;
        }
        return band;
    }
}

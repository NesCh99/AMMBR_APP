/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.CRUD;

import com.example.ammbr.Modelo.Dao.ServiciosExtraDao;
import com.example.ammbr.Modelo.Dao.ServiciosExtraDaoImpl;
import com.example.ammbr.Modelo.ServiciosExtra;
import com.example.ammbr.Modelo.Usuarios;

import java.util.List;

/**
 *
 * @author Nes Ch
 */
public class CRUDServiciosExtra {
    ServiciosExtraDao ServiciosExtraDao = new ServiciosExtraDaoImpl();
    ServiciosExtra servicios = new ServiciosExtra();
    
    public void nuevoServicioExtra(String idhospedaje, String descripcion){
        servicios.setIdHospedaje(idhospedaje.toUpperCase());
        servicios.setDescripcion(descripcion.toUpperCase());
        ServiciosExtraDao.save(servicios);
    }
    
    public void eliminarServicioExtra(int idservicioextra){
        ServiciosExtraDao.delete(idservicioextra);
    }
    
    public void modificarServicioExtra(int idservicioextra, String descripcion){
        servicios.setIdServiciosExtra(idservicioextra);
        servicios.setDescripcion(descripcion.toUpperCase());
        ServiciosExtraDao.save(servicios);
    }

    public boolean existenServicios(String idHospedaje){
        boolean band=false;
        if(ServiciosExtraDao.ServiciosSitio(idHospedaje)==1){
            band=true;
        }

        return band;
    }

    public List<ServiciosExtra> listaServicios(String idHospedaje){
        List<ServiciosExtra> list = ServiciosExtraDao.listCaracteristicas(idHospedaje);
        return list;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.CRUD;

import com.example.ammbr.Modelo.Dao.UsuariosDao;
import com.example.ammbr.Modelo.Dao.UsuariosDaoImpl;
import com.example.ammbr.Modelo.Negocio.Seguridad.ControlIngresoDatos;
import com.example.ammbr.Modelo.Negocio.Seguridad.Encriptacion;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.Modelo.Usuarios;

import java.sql.Blob;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Nes Ch
 */
public class CRUDUsuarios {

    UsuariosDao UsuariosDao = new UsuariosDaoImpl();
    Encriptacion encriptacion = new Encriptacion();
    Usuarios Usuarios = new Usuarios();

    public void nuevoUsuario(String nombre, String apellido, String email, String foto, String contraseña, int tipo, String fecha_nac) {
                    Usuarios.setNombre(nombre.toUpperCase());
                    Usuarios.setApellido(apellido.toUpperCase());
                    Usuarios.setEmail(email.toLowerCase());
                    Usuarios.setContrasena(encriptacion.encode(email.toLowerCase(), contraseña));
                    Usuarios.setFoto(foto);
                    Usuarios.setTipoUsuario(tipo);
                    Usuarios.setFecha_nac(fecha_nac);
                    UsuariosDao.save(Usuarios);
    }

    public void editarUsuario(String email,String nombre, String apellido, String foto, String fecha_nac) {
                    Usuarios.setEmail(email);
                    Usuarios.setNombre(nombre.toUpperCase());
                    Usuarios.setApellido(apellido.toUpperCase());
                    Usuarios.setFoto(foto);
                    Usuarios.setFecha_nac(fecha_nac);
                    UsuariosDao.save(Usuarios);
    }
    public void cambiarTipo(String email, int tipo) {
        Usuarios.setEmail(email.toLowerCase());
        UsuariosDao.cambiarAdmin(Usuarios, tipo);
    }
    public Usuarios usuario(String email){
        Usuarios usuarios;
        usuarios = UsuariosDao.edit(email);
        return usuarios;
    }

    public void eliminarUsuario(String email) {
        Usuarios Usuarios = new Usuarios();
        Usuarios.setEmail(email.toLowerCase());
        UsuariosDao.delete(email);
    }

    public boolean Sesion(String email, String contraseña){
        Encriptacion enc = new Encriptacion();
        return UsuariosDao.sesion(email.toLowerCase(), enc.encode(email.toLowerCase(),contraseña));
    }

    public List<Usuarios> listarEmail(int tipo) {
        List<Usuarios> list = UsuariosDao.listEmail(tipo);
        return list;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.Dao;

import com.example.ammbr.Modelo.Usuarios;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface UsuariosDao {
    public List<Usuarios> list();    //prototipo metodo listar
    public List<Usuarios> listEmail(int tipo);    //prototipo metodo listar
    public Usuarios edit(String email);    //prototipo metodo editar
    public boolean save (Usuarios Usuarios);   //prototipo metodo guardar
    public boolean cambiarAdmin (Usuarios Usuarios, int tipo);   //prototipo metodo guardar
    public boolean search (String email);   //prototipo metodo buscar por email
    public boolean delete(String email);
    public boolean sesion(String email, String contraseña);

}

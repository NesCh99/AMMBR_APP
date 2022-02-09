/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.Negocio.Seguridad;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.ammbr.Modelo.Dao.HabitacionDao;
import com.example.ammbr.Modelo.Dao.HabitacionDaoImpl;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDao;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDaoImpl;
import com.example.ammbr.Modelo.Dao.UsuarioHuespedDao;
import com.example.ammbr.Modelo.Dao.UsuarioHuespedDaoImpl;
import com.example.ammbr.Modelo.Dao.UsuariosDao;
import com.example.ammbr.Modelo.Dao.UsuariosDaoImpl;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.Modelo.UsuarioHuesped;
import com.example.ammbr.Modelo.Usuarios;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nes Ch
 */
public class ControlIngresoDatos {

    UsuariosDao UsuariosDao = new UsuariosDaoImpl();
    Usuarios Usuarios = new Usuarios();
    SitioHospedajeDao SitioHospedajeDao = new SitioHospedajeDaoImpl();
    HabitacionDao HabitacionDao = new HabitacionDaoImpl();

    public boolean existeUsuario(String email) {
        boolean band = false;
        band = UsuariosDao.search(email);
        return band;
    }

    public boolean existeSitioHospedaje(String idHospedaje) {
        boolean band = false;
        band = SitioHospedajeDao.search(idHospedaje);
        return band;
    }

    public boolean existeHabitacion(String idHabitacion) {
        boolean band = false;
        band = HabitacionDao.search(idHabitacion);
        return band;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean MayorEdad(String fechanac) {
        boolean band = false;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fechanac, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);

        if(periodo.getYears()>=18){
            band=true;
        }
        return band;
    }

    public boolean numCaracteres(String idusuario) {
        boolean band = false;
        if (idusuario.length() <= 10) {
            band = true;
        }
        return band;
    }

    public boolean contraseña(String contraseña) {
        boolean band = false;
        if (contraseña.length() >= 8) {
            band = true;
        }

        // Enumeración de caracteres especiales
        /*String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(contraseña);
        boolean specialStrFlag = matcher.find();
        // Debe contener una combinación de letras y números en mayúsculas y minúsculas, se pueden usar caracteres especiales y la longitud es de 8 a 10
        boolean numStrFlag = contraseña.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$");
        if (specialStrFlag && numStrFlag) {
            band = true;
        } else {
            band = false;
        }*/
        return band;
    }

    public boolean email(String email) {
        boolean band = false;
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            band = true;
        } else {
            band = false;
        }
        return band;
    }

    public boolean camposVaciosUsuarios(String idusuario, String nombre, String apellido, String celular) {
        boolean band = false;
        if (idusuario.isEmpty() == false || nombre.isEmpty() == false || apellido.isEmpty() == false || celular.isEmpty() == false) {
            band = true;
        }
        return band;
    }

}

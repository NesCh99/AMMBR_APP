/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.Factory;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

/**
 *
 * @author Nes Ch
 */
public class ConexionMySQL extends ConexionDB{

    public ConexionMySQL(String[] params) {  //recibve un array de string parametros
        this.params = params;    //el atributo params de superclase recibe el parametro del constructor
        this.open();        //llama al metodo que se implementa abajo
    }
    private static Connection conn;
    private static final String driver="com.mysql.jdbc.Driver";
    @Override
    public Connection open() {   //implementacion del metodo abstracto que abre bd


        try{
            Class.forName(driver);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            this.connection = DriverManager.getConnection("jdbc:mysql://dbammbr.mysql.database.azure.com/ammbr?serverTimezone=UTC&useSSL=true", "superusuario@dbammbr", "Ammbr_1234");  //subindice 0: nombre bd. paremtro 1: usr; parametro 2: clave.. llega en constructor
            System.out.println(connection);
            if(connection!=null){
                Log.i("Conexion","Conexion Establecida");
                System.out.println("Conexión establecida");
            }
        } catch (ClassNotFoundException | SQLException e){
            Log.e("Conexion","Conexion no Establecida");
            System.out.println("Error de conexion " + e);
        }
        return this.connection;     //devuelve la conexion q es atributo declarada en superclase
    }


}

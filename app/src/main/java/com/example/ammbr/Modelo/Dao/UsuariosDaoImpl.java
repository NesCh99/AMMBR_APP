
package com.example.ammbr.Modelo.Dao;

import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.Modelo.Factory.ConexionDB;
import com.example.ammbr.Modelo.Factory.FactoryConexionDB;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDaoImpl implements UsuariosDao {
    ConexionDB conn;
    
    public UsuariosDaoImpl(){}

    @Override
    public List<Usuarios> list() {
        this.conn = FactoryConexionDB.open();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM usuarios ");  //construye la cadena de consulta

        List<Usuarios> list = new ArrayList<>(); 
        
        try{
            ResultSet rs = this.conn.query(sql.toString());  //ejecuta la consulta
            while (rs.next()){  //mientras haya registros en la tabla
                Usuarios Usuarios = new Usuarios();
                Usuarios.setEmail(rs.getString("EMAIL"));
                Usuarios.setNombre(rs.getString("NOMBRE"));
                Usuarios.setApellido(rs.getString("APELLIDO"));
                Usuarios.setContrasena(rs.getString("CONTRASENA"));
                Usuarios.setFoto(rs.getString("FOTO"));
                Usuarios.setTipoUsuario(rs.getInt("TIPOUSUARIO"));
                Usuarios.setFecha_nac(rs.getString("FECHA_NAC"));
                list.add(Usuarios);  //añade el objeto temporal en la lista
            }
        } catch (Exception e) {
            
        } finally {
           this.conn.close();      //cierra la conexion
        }
        return list;    //devuelve la lista generada
    }

    @Override
    public List<Usuarios> listEmail(int tipo) {
        this.conn = FactoryConexionDB.open();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM usuarios WHERE TIPOUSUARIO = ").append(tipo);  //construye la cadena de consulta

        List<Usuarios> list = new ArrayList<>();

        try{
            ResultSet rs = this.conn.query(sql.toString());  //ejecuta la consulta
            while (rs.next()){  //mientras haya registros en la tabla
                Usuarios Usuarios = new Usuarios();
                Usuarios.setEmail(rs.getString("EMAIL"));
                Usuarios.setNombre(rs.getString("NOMBRE"));
                Usuarios.setApellido(rs.getString("APELLIDO"));
                list.add(Usuarios);  //añade el objeto temporal en la lista
            }
        } catch (Exception e) {

        } finally {
            this.conn.close();      //cierra la conexion
        }
        return list;    //devuelve la lista generada
    }

    @Override
    public Usuarios edit(String email) {
        this.conn = FactoryConexionDB.open();
        Usuarios Usuarios = new Usuarios();
        
        StringBuilder sql = new StringBuilder();    //para almacenar la consulta e efectuar en la bd
        sql.append("SELECT * FROM usuarios WHERE EMAIL = '").append(email);   //cadena de consulta
        sql.append("'");
        
        try {
            ResultSet rs = this.conn.query(sql.toString());  //carga todos los registros que cumplen con la condicion del sql

            while (rs.next()){          //mientras haya registros cargados en el reseltset
                Usuarios.setEmail(rs.getString("EMAIL"));
                Usuarios.setNombre(rs.getString("NOMBRE"));
                Usuarios.setApellido(rs.getString("APELLIDO"));
                Usuarios.setContrasena(rs.getString("CONTRASENA"));
                Usuarios.setFoto(rs.getString("FOTO"));
                Usuarios.setTipoUsuario(rs.getInt("TIPOUSUARIO"));
                Usuarios.setFecha_nac(rs.getString("FECHA_NAC"));
            }
        } catch (Exception e) {
            
        } finally {
            this.conn.close();          //cierra la conexion
        }
        return Usuarios;             //devuelve el objeto creado
    }

    @Override
    public boolean save(Usuarios Usuarios) {
        this.conn = FactoryConexionDB.open();
        boolean save = true;        //bandera para indicar si se almacenaron los cambios
        
        try {
            boolean find = false;
                StringBuilder sqls = new StringBuilder();   //para crear la sentencia sql
            sqls.append("SELECT EMAIL FROM usuarios WHERE EMAIL = '").append(Usuarios.getEmail());  //construye la cadena de consulta
            sqls.append("'");
            ResultSet rs = this.conn.query(sqls.toString());  //ejecuta la consulta
        
            find = rs.next();
            
                if(find==false){
                    
                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
                sql.append("INSERT INTO Usuarios (EMAIL, NOMBRE, APELLIDO,  CONTRASENA, FOTO, TIPOUSUARIO, FECHA_NAC) VALUES ('").append(Usuarios.getEmail());
                sql.append("', '").append(Usuarios.getNombre());  
                sql.append("', '").append(Usuarios.getApellido());
                sql.append("', '").append(Usuarios.getContrasena());
                sql.append("', '").append(Usuarios.getFoto());
                sql.append("', '").append(Usuarios.getTipoUsuario());
                sql.append("', '").append(Usuarios.getFecha_nac()).append("')");      //crear la cadena de conexion

                this.conn.execute(sql.toString());      //ejecuta la query
            }else{
                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
                
                sql.append("UPDATE usuarios SET NOMBRE = '").append(Usuarios.getNombre());
                sql.append("', APELLIDO = '").append(Usuarios.getApellido());
                sql.append("', FOTO = '").append(Usuarios.getFoto());
                sql.append("', FECHA_NAC = '").append(Usuarios.getFecha_nac()).append("' WHERE EMAIL = '").append(Usuarios.getEmail());
                sql.append("'");//crear la cadena de conexion
                this.conn.execute(sql.toString());      //ejecuta la query 
            }
            
            save = true;                                //cambia estado de bandera
        } catch(Exception e){
            
        } finally {
           this.conn.close();      //cerrar la conexion
        }
        return save;
    }

    @Override
    public boolean cambiarAdmin(Usuarios Usuarios, int tipo) {
        this.conn = FactoryConexionDB.open();
        boolean save = false;        //bandera para indicar si se almacenaron los cambios

        try {
            StringBuilder sqls = new StringBuilder();   //para crear la sentencia sql
            sqls.append("SELECT EMAIL FROM usuarios WHERE EMAIL = '").append(Usuarios.getEmail());  //construye la cadena de consulta
            sqls.append("'");
            ResultSet rs = this.conn.query(sqls.toString());  //ejecuta la consulta

                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql

                sql.append("UPDATE usuarios SET TIPOUSUARIO = ").append(tipo);
                sql.append(" WHERE EMAIL = '").append(Usuarios.getEmail());
                sql.append("'");
                this.conn.execute(sql.toString());      //ejecuta la query

            save = true;                                //cambia estado de bandera
        } catch(Exception e){

        } finally {
            this.conn.close();      //cerrar la conexion
        }
        return save;
    }

    @Override
    public boolean delete(String email) {
        boolean delete = false;                     //bandera que indica resultado de operacion

        this.conn = FactoryConexionDB.open();    //abrir la conexion con bd mysql
        try{
            StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
            sql.append("DELETE FROM usuarios WHERE EMAIL = '").append(email);    //crea la sentencia de borrado
            sql.append("'");
            this.conn.execute(sql.toString());              //ejecuta sentencia sql
            delete = true;
        } catch (Exception e) {
            
        } finally {
            this.conn.close();                  //cierra la conexion
        }
        return delete;                              //devuelve el valor de la bandera
    }

    @Override
    public boolean sesion(String email, String contraseña) {
        this.conn = FactoryConexionDB.open();
        boolean band = true;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM usuarios WHERE EMAIL = '").append(email);  //construye la cadena de consulta
        sql.append("' AND CONTRASENA = '").append(contraseña);
        sql.append("'");
        try{

            ResultSet rs = this.conn.query(sql.toString());  //carga todos los registros que cumplen con la condicion del sql

            band = rs.next();

        } catch (SQLException e) {

        } finally {
            this.conn.close();      //cierra la conexion
        }
        return band;                              //devuelve el valor de la bandera
    }

    @Override
    public boolean search(String email) {
        this.conn = FactoryConexionDB.open();
        boolean band = true;
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM usuarios WHERE EMAIL = '").append(email);  //construye la cadena de consulta
        sql.append("'");            
        try{
            
            ResultSet rs = this.conn.query(sql.toString());  //carga todos los registros que cumplen con la condicion del sql

            band = rs.next();
            
        } catch (SQLException e) {
            
        } finally {
           this.conn.close();      //cierra la conexion
        }        
        return band;                              //devuelve el valor de la bandera
    }
}

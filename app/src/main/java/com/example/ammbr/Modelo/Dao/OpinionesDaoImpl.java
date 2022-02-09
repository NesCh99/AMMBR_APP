package com.example.ammbr.Modelo.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.ammbr.Modelo.Imagen;
import com.example.ammbr.Modelo.Opiniones;
import com.example.ammbr.Modelo.Factory.ConexionDB;
import com.example.ammbr.Modelo.Factory.FactoryConexionDB;

public class OpinionesDaoImpl implements OpinionesDao{
    	ConexionDB conn;
	 public OpinionesDaoImpl() {}
	@Override
	
	
	public List<Opiniones> list() {
		// TODO Auto-generated method stub
// TODO Auto-generated method stub
		
        this.conn = FactoryConexionDB.open();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Opiniones ");
        
        List<Opiniones> list  = new ArrayList<>();
        
        try{
            ResultSet rs = this.conn.query(sql.toString());  //ejecuta la consulta
            while (rs.next()){  //mientras haya registros en la tabla
            	Opiniones Opiniones = new Opiniones();
            	Opiniones.setIdHospedaje(rs.getString("idHospedaje"));
            	Opiniones.setEstrellas(rs.getInt(1));
            	Opiniones.setComentario(rs.getString("comentario"));
                
               list.add(Opiniones);
         
                //añade el objeto temporal en la lista
            }
        } catch (Exception e) {
            
        } finally {
           this.conn.close();      //cierra la conexion
        }
        
		return list;
	}

	@Override
	public Opiniones edit(int idOpinion) {
		// TODO Auto-generated method stub

		  this.conn = FactoryConexionDB.open();
		  Opiniones Opiniones = new Opiniones();
		  
		  StringBuilder sql = new StringBuilder();    //para almacenar la consulta e efectuar en la bd
	       sql.append("SELECT * FROM opiniones WHERE IDOPINION = ").append(idOpinion);   //cadena de consulta
	        
	       try {
	            ResultSet rs = this.conn.query(sql.toString());  //carga todos los registros que cumplen con la condicion del sql

	            while (rs.next()){ 
	            	Opiniones.setIdHospedaje(rs.getString("idHospedaje"));
	            	Opiniones.setEstrellas(rs.getInt(1));
	            	Opiniones.setComentario(rs.getString("comentario"));
	            	
	            	
	               
	            }
	        } catch (Exception e) {
	            
	        } finally {
	            this.conn.close();          //cierra la conexion
	        }
	                  //devuelve el objeto creado
		// TODO Auto-generated method stub
		return Opiniones;
	}

	@Override
	public boolean save(Opiniones Opiniones) {
		// TODO Auto-generated method stub
		this.conn = FactoryConexionDB.open();
        boolean save = true;        //bandera para indicar si se almacenaron los cambios
        
        try {
            boolean find = false;
            StringBuilder sqls = new StringBuilder();   //para crear la sentencia sql
            sqls.append("SELECT * FROM opiniones where EMAILHUESPED = '").append(Opiniones.getEmailHuesped());  //construye la cadena de consulta
            sqls.append("' HAVING IDHOSPEDAJE = '").append(Opiniones.getIdHospedaje());
            sqls.append("'");
            ResultSet rs = this.conn.query(sqls.toString());  //ejecuta la consulta
            find = rs.next();

            find = rs.next();
            if(find==false){
                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
                sql.append("INSERT INTO opiniones (IDHOSPEDAJE, ESTRELLAS, COMENTARIO, EMAILHUESPED) VALUES ('").append(Opiniones.getIdHospedaje());
                sql.append("', '").append(Opiniones.getEstrellas());      //crear la cadena de conexion
                sql.append("', '").append(Opiniones.getComentario());
                sql.append("', '").append(Opiniones.getEmailHuesped()).append("')");
                this.conn.execute(sql.toString());      //ejecuta la query     //ejecuta la query
                save=true;
            }else if(Opiniones.getIdOpinion()>0){
                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
                sql.append("UPDATE opiniones SET COMENTARIO = '").append(Opiniones.getComentario());      //crear la cadena de conexion
                sql.append("' , ESTRELLAS = '").append(Opiniones.getEstrellas());
                sql.append("' WHERE EMAILHUESPED = '").append(Opiniones.getEmailHuesped());      //crear la cadena de conexion
                sql.append("'");
                save=false;
                this.conn.execute(sql.toString());     //ejecuta la query
            }
                                           //cambia estado de bandera
        } catch(Exception e){
            
        } finally {
           this.conn.close();      //cerrar la conexion
        }
        return save;
	}

    @Override
    public void editar(Opiniones Opiniones) {
        // TODO Auto-generated method stub
        this.conn = FactoryConexionDB.open();

        try {
            boolean find = false;
            StringBuilder sqls = new StringBuilder();   //para crear la sentencia sql
            sqls.append("SELECT * FROM opiniones where EMAILHUESPED = '").append(Opiniones.getEmailHuesped());  //construye la cadena de consulta
            sqls.append("'");

            ResultSet rs = this.conn.query(sqls.toString());  //ejecuta la consulta
            find = rs.next();
            if(find==false){
            }else if(Opiniones.getIdOpinion()>0){
                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
                sql.append("UPDATE opiniones SET COMENTARIO = '").append(Opiniones.getComentario());      //crear la cadena de conexion
                sql.append("' , ESTRELLAS = '").append(Opiniones.getEstrellas());
                sql.append("' WHERE EMAILHUESPED = '").append(Opiniones.getEmailHuesped());      //crear la cadena de conexion
                sql.append("'");
                this.conn.execute(sql.toString());     //ejecuta la query
            }

            //cambia estado de bandera
        } catch(Exception e){

        } finally {
            this.conn.close();      //cerrar la conexion
        }
    }


    @Override
	public boolean delete(int idopinion, String emailhuesped) {
		// TODO Auto-generated method stub
		boolean delete = false;                     //bandera que indica resultado de operacion

        this.conn = FactoryConexionDB.open();    //abrir la conexion con bd mysql
        
        try{
            boolean find = false;
                StringBuilder sqls = new StringBuilder();   //para crear la sentencia sql
            sqls.append("SELECT * FROM opiniones where EMAILHUESPED= '").append(emailhuesped);  //construye la cadena de consulta
            sqls.append("' HAVING IDOPINION = '").append(idopinion);
            sqls.append("'");
            
            ResultSet rs = this.conn.query(sqls.toString());  //ejecuta la consulta
            
                find = rs.next();
                
            if(find==true){
                StringBuilder sql = new StringBuilder();   //para crear la sentencia sql
                sql.append("DELETE FROM opiniones WHERE IDOPINION = ").append(idopinion);    //crea la sentencia de borrado
                this.conn.execute(sql.toString());              //ejecuta sentencia sql
                delete = true;
            }
            
        } catch (Exception e) {
            
        } finally {
            this.conn.close();                  //cierra la conexion
        }
        return delete;                              //devuelve el valor de la bandera
	}

    @Override
    public List<Opiniones> listarxHospedaje(String idHospedaje) {
        this.conn = FactoryConexionDB.open();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM opiniones WHERE IDHOSPEDAJE = '").append(idHospedaje);
        sql.append("'");

        List<Opiniones> list  = new ArrayList<>();

        try{
            ResultSet rs = this.conn.query(sql.toString());  //ejecuta la consulta
            while (rs.next()){  //mientras haya registros en la tabla
                Opiniones Opiniones = new Opiniones();
                Opiniones.setIdOpinion(rs.getLong("IDOPINION"));
                Opiniones.setIdHospedaje(rs.getString("IDHOSPEDAJE"));
                Opiniones.setEstrellas(rs.getInt("ESTRELLAS"));
                Opiniones.setComentario(rs.getString("COMENTARIO"));
                Opiniones.setEmailHuesped(rs.getString("EMAILHUESPED"));

                list.add(Opiniones);

                //añade el objeto temporal en la lista
            }
        } catch (Exception e) {

        } finally {
            this.conn.close();      //cierra la conexion
        }

        return list;
    }

    @Override
    public double promedio(String idHospedaje) {
        this.conn = FactoryConexionDB.open();
        int prom = 0;
        int band = 0;
        int count = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ESTRELLAS FROM opiniones WHERE IDHOSPEDAJE = '").append(idHospedaje);
        sql.append("'");

        try{
            ResultSet rs = this.conn.query(sql.toString());  //ejecuta la consulta
            while (rs.next()){  //mientras haya registros en la tabla

                band = rs.getInt("ESTRELLAS");
                prom = band + prom;
                count = count+1;

                //añade el objeto temporal en la lista
            }
        } catch (Exception e) {

        } finally {
            this.conn.close();      //cierra la conexion
        }
        prom = prom/count;
        return prom;
    }

}

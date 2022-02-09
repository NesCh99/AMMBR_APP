package com.example.ammbr.Modelo.Dao;

import java.util.List;

import com.example.ammbr.Modelo.Imagen;

public interface ImagenDao {
	public List<Imagen> list();
	
	public Imagen edit(String idHospedaje);
	public boolean save(Imagen Imagen);
	public boolean delete(String idHospedaje);
	

}





 
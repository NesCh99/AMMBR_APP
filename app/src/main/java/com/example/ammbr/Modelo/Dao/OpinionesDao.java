package com.example.ammbr.Modelo.Dao;

import java.util.List;

import com.example.ammbr.Modelo.Opiniones;



public interface OpinionesDao {
	public List<Opiniones> list();
	public Opiniones edit(int idOpinion);
	public boolean save(Opiniones Opiniones);
	public void editar(Opiniones Opiniones);
	public boolean delete(int idOpinion, String emailhuesped);
	public List<Opiniones> listarxHospedaje(String idHospedaje);
	public double promedio(String idHospedaje);

}

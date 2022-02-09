package com.example.ammbr.Modelo.Dao;

import java.util.List;

import com.example.ammbr.Modelo.ServiciosExtra;

public interface ServiciosExtraDao {
	public List<ServiciosExtra> list();
	public List<ServiciosExtra> listCaracteristicas(String idHospedaje);
	public ServiciosExtra edit(int idServicioExtra);
	public boolean save(ServiciosExtra ServiciosExtra);
	public boolean delete(int idServicioExtra);
	public int ServiciosSitio(String idHospedaje);
}

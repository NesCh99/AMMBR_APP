package com.example.ammbr.Modelo;

public class Opiniones {
	private String idHospedaje;
        private long idOpinion;
	private int estrellas;
        private String emailHuesped;
	private String comentario;
	
	public String getIdHospedaje() {
		return idHospedaje;
	}
	public void setIdHospedaje(String idHospedaje) {
		this.idHospedaje = idHospedaje;
	}
	public int getEstrellas() {
		return estrellas;
	}
	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEmailHuesped() {
		return emailHuesped;
	}

	public void setEmailHuesped(String emailHuesped) {
		this.emailHuesped = emailHuesped;
	}

	public long getIdOpinion() {
        return idOpinion;
    }

    public void setIdOpinion(long idOpinion) {
        this.idOpinion = idOpinion;
    }
	

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo;

/**
 *
 * @author Nes Ch
 */
public class Habitacion {
    public Long idHabitacion;
    public String CategoriaHabitacion;
    public String idHospedaje;
    public int numeroCamas ;
    public String descripcion;
    public double precio;

    public Long getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getCategoriaHabitacion() {
        return CategoriaHabitacion;
    }

    public void setCategoriaHabitacion(String CategoriaHabitacion) {
        this.CategoriaHabitacion = CategoriaHabitacion;
    }

    public String getIdHospedaje() {
        return idHospedaje;
    }

    public void setIdHospedaje(String idHospedaje) {
        this.idHospedaje = idHospedaje;
    }

    public int getNumeroCamas() {
        return numeroCamas;
    }

    public void setNumeroCamas(int numeroCamas) {
        this.numeroCamas = numeroCamas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

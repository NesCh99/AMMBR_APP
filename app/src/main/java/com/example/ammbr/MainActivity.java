package com.example.ammbr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.example.ammbr.Modelo.CRUD.CRUDHabitacion;
import com.example.ammbr.Modelo.CRUD.CRUDSitioHospedaje;
import com.example.ammbr.Vista.Listar_Hospedaje;
import com.example.ammbr.Vista.VistaInicio;
import com.example.ammbr.Vista.VistaListarHospedajes;
import com.example.ammbr.Vista.VistaLogin;
import com.example.ammbr.Vista.VistaSitioHospedaje;
import com.example.ammbr.Vista.VistaUsuario;


public class MainActivity extends AppCompatActivity {

    Button irRegistrarSitio, irListarHospedaje;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        irListarHospedaje = findViewById(R.id.btnListarHospedaje);


    }


    public void Conectar(View View){
        CRUDSitioHospedaje sitio = new CRUDSitioHospedaje();
        sitio.listarSitiosHospedaje();

    }

    public void ListarHospedaje(View View){

        Intent MSH = new Intent(this, VistaSitioHospedaje.class);
        MSH.putExtra("Usuario", true);
        startActivity(MSH);

    }
    public void ListarHospedajes(View View){

        Intent MSH = new Intent(this, VistaListarHospedajes.class);
        startActivity(MSH);

    }

    public void Principal(View View){

        Intent MSH = new Intent(this, VistaInicio.class);
        startActivity(MSH);

    }
    public void Login(View View){

        Intent MSH = new Intent(this, VistaLogin.class);
        startActivity(MSH);

    }

    public void HospedajeMaestro(View View){
        Intent HM = new Intent(this, VistaSitioHospedaje.class);
        HM.putExtra("Maestro", true);
        startActivity(HM);
    }

    public void HospedajeRegistrar(View View){
        Intent HM = new Intent(this, VistaSitioHospedaje.class);
        HM.putExtra("Registrar", true);
        startActivity(HM);
    }

    public void HospedajeModificar(View View){
        Intent HM = new Intent(this, VistaSitioHospedaje.class);
        HM.putExtra("Modificar", true);
        startActivity(HM);
    }

    public void listarUsuario(View View){
        Intent HM = new Intent(this, VistaUsuario.class);
        HM.putExtra("ListarUsuario", true);
        startActivity(HM);
    }
    public void registrarUsuario(View View){
        Intent HM = new Intent(this, VistaUsuario.class);
        HM.putExtra("RegistrarUsuario", true);
        startActivity(HM);
    }
    public void modificarUsuario(View View){
        Intent HM = new Intent(this, VistaUsuario.class);
        HM.putExtra("ModificarUsuario", true);
        startActivity(HM);
    }
    public void UsuarioApp(View View){
        Intent HM = new Intent(this, VistaSitioHospedaje.class);
        HM.putExtra("Modificar", true);
        startActivity(HM);
    }
    public void UsuarioAdmin(View View){
        Intent HM = new Intent(this, VistaSitioHospedaje.class);
        startActivity(HM);
    }
    public void prueba(View View){
        Intent HM = new Intent(this, Listar_Hospedaje.class);
        startActivity(HM);
    }
}
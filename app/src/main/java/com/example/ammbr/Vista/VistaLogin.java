package com.example.ammbr.Vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ammbr.Modelo.CRUD.CRUDUsuarios;
import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.R;

public class VistaLogin extends AppCompatActivity {

    EditText txtEmailLogin, txtContraseña;
    Button btnContraseñaOlvidada, btnRegistroLogin, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_login);
        setAtributos();
        SharedPreferences shareFile = getSharedPreferences("DatosUsuario", this.MODE_PRIVATE);
        String Email = shareFile.getString("Email","vacio");
        Intent GoPrincipal = new Intent(this, VistaInicio.class);
        if(Email.equals("vacio")){

        }else{
            Toast.makeText(getApplicationContext(), R.string.SesionIniciada, Toast.LENGTH_LONG).show();
            startActivity(GoPrincipal);
        }
    }

    public void setAtributos(){
        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtContraseña = findViewById(R.id.txtContraseña);
        btnRegistroLogin = findViewById(R.id.btnRegistroLogin);



    }

    public void IniciarSesion(View View){
        Context context = this;
        SharedPreferences shareFile = getSharedPreferences("DatosUsuario", context.MODE_PRIVATE);
        Intent GoPrincipal = new Intent(this, VistaInicio.class);
        CRUDUsuarios usuarios = new CRUDUsuarios();
        if(usuarios.Sesion(txtEmailLogin.getText().toString().toLowerCase(),txtContraseña.getText().toString())){
            SharedPreferences.Editor editor = shareFile.edit();
            editor.putString("Email", txtEmailLogin.getText().toString());
            editor.putString("Contraseña", txtContraseña.getText().toString());
            editor.commit();
            startActivity(GoPrincipal);
        }else{
            Toast.makeText(VistaLogin.this, R.string.ErrorLogin, Toast.LENGTH_LONG).show();
        }
    }

    public void RegistrarUsuarioHuesped(View View){
        Intent GoRegistroUsuario = new Intent(this, VistaUsuario.class);
        GoRegistroUsuario.putExtra("RegistrarUsuario", "Huesped");
        startActivity(GoRegistroUsuario);
    }
}
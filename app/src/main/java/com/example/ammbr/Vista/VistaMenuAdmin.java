package com.example.ammbr.Vista;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammbr.Modelo.CRUD.CRUDUsuarios;
import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.R;

import java.util.ArrayList;
import java.util.List;

public class VistaMenuAdmin extends AppCompatActivity {

    Spinner spnAdmins;
    SearchView shAdministradores;
    ArrayAdapter<String> adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_menu_admin);

        //Pobla el combobox de administradores
        CRUDUsuarios usuarios = new CRUDUsuarios();
        spnAdmins = findViewById(R.id.spnAdmins);
        shAdministradores = findViewById(R.id.shAdministradores);
        try{
            List<Usuarios> listaUsuarios = usuarios.listarEmail(2);
            String emails[] = new String[listaUsuarios.size()];
            int i=0;
            for (Usuarios Usuarios: listaUsuarios){
                emails[i] = Usuarios.getEmail();
                i=i+1;
            }
            adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emails);
            spnAdmins.setAdapter(adap);
        }catch (Exception e){
            System.out.println(e);
            Toast.makeText(VistaMenuAdmin.this, R.string.ControlAdminSitio, Toast.LENGTH_LONG).show();
        }
        shAdministradores.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adap.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adap.getFilter().filter(s);

                return false;
            }
        });

        //termina
    }

    public void irNuevoAdmin(View View){
        mostrarListaUsuarios();
    }

    public void mostrarListaUsuarios(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.buscar_usuarios);
        Spinner spn = dialog.findViewById(R.id.spEmails);
        SearchView sv = dialog.findViewById(R.id.shBuscarNombre);
        Button guardar = dialog.findViewById(R.id.btnGuardar);
        Button cancel = dialog.findViewById(R.id.btnCancelar);
        TextView emailadmin = dialog.findViewById(R.id.txEmailAdmin);


        CRUDUsuarios us = new CRUDUsuarios();
        List<Usuarios> lista = us.listarEmail(3);
        String[] nombres = new String[lista.size()];
        String[] correos = new String[lista.size()];

        try{
            int i=0;
            for(Usuarios u : lista){
                nombres[i] = u.getNombre();
                correos[i] = u.getEmail();
                i=i+1;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, correos);
            spn.setAdapter(adapter);


            spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    emailadmin.setText(spn.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    try{
                        emailadmin.setText(spn.getSelectedItem().toString());
                    }catch(Exception e){}

                    return false;
                }
            });

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(emailadmin.getText().toString().isEmpty()){
                            Toast.makeText(VistaMenuAdmin.this, "Escoja un Email", Toast.LENGTH_LONG).show();
                        }else{
                            us.cambiarTipo(emailadmin.getText().toString(), 2);
                            dialog.cancel();
                            Intent Refresh = new Intent(getApplicationContext(),VistaMenuAdmin.class);
                            startActivity(Refresh);
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            dialog.show();

        }catch(Exception e){
            System.out.println(e);
        }



    }
    public void eliminarAdmin(View View){
        CRUDUsuarios usuarios = new CRUDUsuarios();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle("Eliminar Admin");
        dialog.setMessage(R.string.Eliminar);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    usuarios.cambiarTipo(spnAdmins.getSelectedItem().toString(), 3);
                    Intent Refresh = new Intent(getApplicationContext(),VistaMenuAdmin.class);
                    startActivity(Refresh);
                }catch(Exception e){
                    Toast.makeText(VistaMenuAdmin.this, R.string.Error, Toast.LENGTH_LONG).show();
                }

            }
        });
        dialog.show();

    }
}
package com.example.ammbr.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ammbr.Modelo.CRUD.CRUDSitioHospedaje;
import com.example.ammbr.Modelo.CRUD.CRUDUsuarios;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.R;

import java.util.List;

public class VistaMenuSitio extends AppCompatActivity {

    Button btnNuevoSitio, btnEliminarSitio, btnEditarSitio;
    Spinner spnSitios;
    SearchView shvHoteles;
    ArrayAdapter<String> adaptador1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_menu_sitio);
        setAtributos();

        CRUDSitioHospedaje sitio = new CRUDSitioHospedaje();
        spnSitios = findViewById(R.id.spnAdmins);
        shvHoteles = findViewById(R.id.shvHoteles);
        try{
            List<SitioHospedaje> listaSitios = sitio.listarSitiosHospedaje();
            String nombres[] = new String[listaSitios.size()];
            int i=0;
            for (SitioHospedaje Sitios: listaSitios){
                nombres[i] = Sitios.getNombre();
                i=i+1;
            }
            adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombres);
            spnSitios.setAdapter(adaptador1);
        }catch (Exception e){
            System.out.println(e);
            Toast.makeText(VistaMenuSitio.this, R.string.SinSitios, Toast.LENGTH_LONG).show();
        }
        shvHoteles.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adaptador1.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptador1.getFilter().filter(s);

                return false;
            }
        });

        //termina
    }

    public void setAtributos(){
        btnNuevoSitio = findViewById(R.id.btnEditarSitio);
        btnEliminarSitio = findViewById(R.id.btnEliminarSitio);
        btnEditarSitio = findViewById(R.id.btnEditarSitio);
        spnSitios = findViewById(R.id.spnSitios);
        shvHoteles = findViewById(R.id.shvHoteles);
    }

    public void RegistrarNuevoSitio(View View){
        Intent GoRegistroSitio = new Intent(this, VistaSitioHospedaje.class);
        GoRegistroSitio.putExtra("Registrar", true);
        startActivity(GoRegistroSitio);
    }
}
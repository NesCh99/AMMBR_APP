package com.example.ammbr.Vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ammbr.Modelo.CRUD.CRUDHabitacion;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDao;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDaoImpl;
import com.example.ammbr.Modelo.Habitacion;
import com.example.ammbr.Modelo.Negocio.Distancia;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.R;

import java.util.List;

public class Listar_Hospedaje extends AppCompatActivity {

    ImageButton btnGuardarOpinion;
    LinearLayout lyNuevaOpinion, lyEfectivo, lyCredito, lyElectronico, lyHabitaciones;
    RatingBar rtbEstrellas;
    EditText txtComentarioHospedaje;
    TextView txvNombreHospedaje, txvCategoriaHospedaje, txvDescripcionHospedaje, txvDistancia, txvTelefonoHospedaje;
    String distanciaKM;
    ImageView imgFacebook, imgWeb, imgWhatsapp, imgInstagram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_hospedaje);
        txvNombreHospedaje = findViewById(R.id.txvNombreHospedaje);
        txvCategoriaHospedaje = findViewById(R.id.txvCategoriaHospedaje);
        txvDescripcionHospedaje = findViewById(R.id.txvDescripcionHospedajeModificar);
        txvDistancia = findViewById(R.id.txvDistancia);
        txvNombreHospedaje = findViewById(R.id.txvNombreHospedaje);
        txvTelefonoHospedaje = findViewById(R.id.txvTelefonoHospedaje);
        rtbEstrellas = findViewById(R.id.rtbEstrellas);
        txtComentarioHospedaje = findViewById(R.id.txtComentarioHospedaje);
        lyNuevaOpinion = findViewById(R.id.lyPagos);
        lyEfectivo = findViewById(R.id.lyEfectivo);
        lyCredito = findViewById(R.id.lyCredito);
        lyElectronico = findViewById(R.id.lyElectronico);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgWeb = findViewById(R.id.imgWeb);
        imgWhatsapp = findViewById(R.id.imgWhatsapp);
        imgInstagram = findViewById(R.id.imgInstagram);
        lyHabitaciones = findViewById(R.id.lyHabitaciones);

        SitioHospedaje sitioHospedaje = new SitioHospedaje();
        SitioHospedajeDao sitioHospedajeDao = new SitioHospedajeDaoImpl();
        CRUDHabitacion habitacion = new CRUDHabitacion();
        sitioHospedaje = sitioHospedajeDao.searchId("HOT-001");
        Distancia distancia = new Distancia();

        /*PERMISO PARA ACCEDER AL GPS*/

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        SitioHospedaje finalSitioHospedaje = sitioHospedaje;
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                distanciaKM = String.valueOf(Math.round(distancia.distanciaCoordenadas(
                        Double.parseDouble(finalSitioHospedaje.getLatitud()),
                        Double.parseDouble(finalSitioHospedaje.getLongitud()),
                        location.getLatitude(),
                        location.getLongitude())*100.0)/100.0 ) + " KM";
                txvDistancia.setText(distanciaKM);
            }
        };
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        /*---------------------------------*/

        txvNombreHospedaje.setText(sitioHospedaje.getNombre());

        txvDescripcionHospedaje.setText(sitioHospedaje.getDescripcion());

        if(sitioHospedaje.getPagoEfectivo()==1){
            lyEfectivo.setVisibility(View.VISIBLE);
        }
        if(sitioHospedaje.getPagoTarjeta()==1){
            lyCredito.setVisibility(View.VISIBLE);
        }
        if (sitioHospedaje.getPagoElectronico()==1){
            lyCredito.setVisibility(View.VISIBLE);
        }

        if (sitioHospedaje.getSitioWebURL() !="" || sitioHospedaje.getSitioWebURL() != "NULL"){
            imgWeb.setVisibility(View.VISIBLE);
        }
        if (sitioHospedaje.getFanPageURL() !="" || sitioHospedaje.getFanPageURL() != "NULL"){
            imgFacebook.setVisibility(View.VISIBLE);
        }
        if (sitioHospedaje.getInstagramURL() !="" || sitioHospedaje.getInstagramURL() != "NULL"){
            imgFacebook.setVisibility(View.VISIBLE);
        }



        List<Habitacion> list = habitacion.listarHabitaciones(sitioHospedaje.getIdHospedaje());
        for(Habitacion habitacion1 : list){

            System.out.println(habitacion1.getDescripcion());
            TextView categoria = new TextView(getApplicationContext());
            TextView camas = new TextView(getApplicationContext());
            TextView descripcion = new TextView(getApplicationContext());
            TextView precio = new TextView(getApplicationContext());
            categoria.setText(habitacion1.getCategoriaHabitacion());
            camas.setText(habitacion1.getNumeroCamas());
            descripcion.setText(habitacion1.getDescripcion());
            //precio.setText(habitacion1.getPrecio());
            lyHabitaciones.addView(categoria);
            lyHabitaciones.addView(camas);
            lyHabitaciones.addView(descripcion);
            //lyHabitaciones.addView(precio);
        }
    }

    public void NuevaOpinion(View View){
        lyNuevaOpinion = findViewById(R.id.lyNuevaOpinion);
        lyNuevaOpinion.setVisibility(View.VISIBLE);
    }
    public void CancelarOpinion(View View){
        lyNuevaOpinion = findViewById(R.id.lyNuevaOpinion);
        rtbEstrellas.setRating(0);
        txtComentarioHospedaje.setText("");
        lyNuevaOpinion.setVisibility(View.GONE);
    }
}
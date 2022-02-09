package com.example.ammbr.Vista;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammbr.Modelo.CRUD.CRUDHabitacion;
import com.example.ammbr.Modelo.CRUD.CRUDOpiniones;
import com.example.ammbr.Modelo.CRUD.CRUDServiciosExtra;
import com.example.ammbr.Modelo.CRUD.CRUDSitioHospedaje;
import com.example.ammbr.Modelo.CRUD.CRUDUsuarios;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDao;
import com.example.ammbr.Modelo.Dao.SitioHospedajeDaoImpl;
import com.example.ammbr.Modelo.Habitacion;
import com.example.ammbr.Modelo.Negocio.Distancia;
import com.example.ammbr.Modelo.Negocio.generaCodigoHospedaje;
import com.example.ammbr.Modelo.Opiniones;
import com.example.ammbr.Modelo.ServiciosExtra;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

public class VistaSitioHospedaje extends AppCompatActivity {
    ImageView btnEditarNombreHospedaje, btnEditarCategoriaHospedaje, btnEditarDescripcion, btnEditarTelefono, btnEditarCaracteristicaHospedaje,
            btnNuevaCaracteristicaHospedaje, btnEditarRedes, btnBorrarCaracteristicaHospedaje, btnAgregarPago, btnAgregarHabitacion, btnFlechaCaracteristicas, imgCamas,
            btnFlechaPagos, btnFlechaHabitaciones, btnFlechaOpiniones, imgCheck, imgFotoPerfilUsuario, imgPortadaHospedaje, btnEditarOpinion,
    btnBorrarOpinion;

    TableRow tbrNuevoNombreHospedaje, tbrModificarCategoriaHospedaje, tbrNombreHospedaje, tbrCategoriaHospedaje;

    LinearLayout lyRedesSocialesHospedaje,tbrModificarDescripcionHospedaje, lyEditarTelefonoHospedaje, lyNuevaCaracteristicaHospedaje,
            lyCardsPagos, lyNuevaOpinion, lyAccesoRedesSociales, lyAccionesNombreSitio, lyAccionesCategoriaHospedaje, lyAccionesDescripcionHospedaje,
            lyTelefono, lyPagos, lyNuevaHabitacion, lyOpinionesHospedaje, lyAccionesTelefono, lyBotones, lyCaracteristicas, lyHabitaciones, lyServicios,
            lyInfoAdmin, lySocialesSitio, lyPrimerRegistro, lyModificarAdministradorSitio, lyUbicacion, lyCar, lyHabitacionesMostrar, lyInfoHabitacionContenedor, lyCamasHabitacion, lyHabitacionContenedor, lyInfoPerfilOpinion, lyInfoOpinion, lyOpinionesContenedor,
            lyInfoHabitacion, lyContenedorOpinion, lyAccionesCaracteristicas, lyAccionesOpinion;

    CardView cardEditarPortadaHosteria, cardCaracteristicaHospedaje, cardHabitacionHospedaje, cardOpinion, cardPagoEfectivo, cardPagoCredito,
            cardPagoElectronico, cdPortada, cdDistancia, cardFotoPerfilUsuario;
    TextView txvInformacionTitulo, txvDescripcionHospedajeModificar, txvNombreHospedajeModificar, txvTelefonoHospedajeModificar, txtCaracteristicaHospedaje, txvCategoriaHabitacionHospedaje, txvPrecioHabitacionHospedaje, txvDescripcionHabitacionHospedaje, txvNUmeroCamasHabitacion,  txvComentarioOpinion, txvNombreUsuarioOpinion, txvDistanciaSitio, txtDescripcionHabitacion, txtPrecioHabitacion, txvIdOpinion, txvIdOpinionEditar;

    CheckBox chbxFacebookHospedaje,  chbxInstagramHospedaje, chbxWebHospedaje, chbxPagoefectivo, chbxPagoCredito, chbxPagoElectronico,
            chbxWifiLobby, chbxWifiHabitacion, chbxPiscina, chbxSpa, chbxBar, chbxGym, chbxEstacionamiento, chbxMascotas, chbxAire, chbxRestaurante;
    EditText txtFacebookHospedaje,  txtInstagramHospedaje, txtWebHospedaje, txtNuevoNombreHospedaje, txtNuevaDescripcionHospedaje,
            txtNuevoTelefonoHospedaje, txtNuevaCaracteristicaHospedaje, txtLatitudSitio, txtLongitudSitio, txtComentarioHospedaje;
    Spinner cbxNuevaCategoriaHospedaje, spnAdministradoresSitio, spnNumeroCamas;

    String idHospedaje, distanciaKM, email;

    RatingBar rtbOpinionHospedaje, rtbEstrellas;

    Boolean Usuario, Maestro, Registrar, Editar, Cercano;

    Button btnSiguienteCaracteristicas, btnSiguienteSitio, btnCompletarRegistro, btnGuardarHabitacion;
    double lat, lon;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_sitio_hospedaje);
        SetAtributosEditar();
        Bundle main = getIntent().getExtras();
        SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
        email = share.getString("Email","no hay");
        Usuario = main.getBoolean("Usuario");
        Maestro = main.getBoolean("Maestro");
        Registrar = main.getBoolean("Registrar");
        Editar = main.getBoolean("Modificar");
        Cercano= main.getBoolean("Cercano");
        Distancia dis = new Distancia();
        if(Cercano){   /*PERMISO PARA ACCEDER AL GPS*/

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    lat = location.getLatitude();
                    lon = (location.getLongitude()*100.0)/100.0;

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
            idHospedaje = dis.MenorDistancia(lat,lon);
        }else{
            idHospedaje = main.getString("ID");
        }


        MostrarHotelUsuario("no", idHospedaje);


        //Pobla el combobox de administradores
        CRUDUsuarios usuarios = new CRUDUsuarios();
        try{
            List<Usuarios> listaUsuarios = usuarios.listarEmail(2);
            String emails[] = new String[listaUsuarios.size()];
            int i=0;
            for (Usuarios Usuarios: listaUsuarios){
                emails[i] = Usuarios.getEmail();
                i=i+1;
            }
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emails);
            spnAdministradoresSitio.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(VistaSitioHospedaje.this, R.string.ControlAdminSitio, Toast.LENGTH_LONG).show();
        }

        //termina


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RegistrarSitio(View View){
        CRUDSitioHospedaje sitio = new CRUDSitioHospedaje();
        generaCodigoHospedaje codigo = new generaCodigoHospedaje();

        btnSiguienteCaracteristicas.setVisibility(View.VISIBLE);
        btnSiguienteSitio.setVisibility(View.GONE);
        int tipoSitio=0;
        if(cbxNuevaCategoriaHospedaje.getSelectedItem().toString().equals("HOTEL")){
            tipoSitio=1;
        } else if (cbxNuevaCategoriaHospedaje.getSelectedItem().toString().equals("HOSTAL")) {
            tipoSitio=2;
        }else{
            tipoSitio=3;
        }

        if(txtNuevoNombreHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.ControlNombre, Toast.LENGTH_LONG).show();
            txtNuevoNombreHospedaje.requestFocus();
        }else if(txtNuevaDescripcionHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.ControlDescripcion, Toast.LENGTH_LONG).show();
            txtNuevaDescripcionHospedaje.requestFocus();
        }else if(txtLatitudSitio.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtLatitudSitio.requestFocus();
        }else if(txtLongitudSitio.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtLongitudSitio.requestFocus();
        }else if(txtNuevoTelefonoHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.ControlTelefono, Toast.LENGTH_LONG).show();
            txtNuevoTelefonoHospedaje.requestFocus();
        }else if(txtFacebookHospedaje.getVisibility()==View.VISIBLE && txtFacebookHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtFacebookHospedaje.requestFocus();
        }else if(txtInstagramHospedaje.getVisibility()==View.VISIBLE && txtInstagramHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtInstagramHospedaje.requestFocus();
        }else if(txtWebHospedaje.getVisibility()==View.VISIBLE && txtWebHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtWebHospedaje.requestFocus();
        }else if(chbxPagoefectivo.isChecked()==false && chbxPagoCredito.isChecked()==false && chbxPagoElectronico.isChecked()==false){
            Toast.makeText(VistaSitioHospedaje.this, R.string.ControlPagos, Toast.LENGTH_LONG).show();
            lyPagos.requestFocus();
        }else{
            //Registro del Sitio de Hospedaje
            String fb="";
            String wt="";
            String web="";
            String in="";
            int cred=0;
            int efe=0;
            int ele=0;
            if(txtFacebookHospedaje.getText().toString().isEmpty()==false){
                fb=txtFacebookHospedaje.getText().toString();
            }
            if(txtInstagramHospedaje.getText().toString().isEmpty()==false){
                in=txtInstagramHospedaje.getText().toString();
            }
            if(txtWebHospedaje.getText().toString().isEmpty()==false){
                web=txtWebHospedaje.getText().toString();
            }

            if(chbxPagoCredito.isChecked()){
                cred=1;
            }
            if(chbxPagoefectivo.isChecked()){
                efe=1;
            }
            if(chbxPagoElectronico.isChecked()){
                ele=1;
            }
            try{
                BitmapDrawable bt = (BitmapDrawable) imgPortadaHospedaje.getDrawable();
                Bitmap bit = bt.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String image = Base64.getEncoder().encodeToString(bytes);
                String admin = spnAdministradoresSitio.getSelectedItem().toString();
                idHospedaje = codigo.codigoHospedaje(tipoSitio);
                sitio.guardarSitioHospedaje(
                        idHospedaje,
                        admin,
                        image,
                        txtNuevoNombreHospedaje.getText().toString().toUpperCase(),
                        txtLatitudSitio.getText().toString(),
                        txtLongitudSitio.getText().toString(),
                        txtNuevaDescripcionHospedaje.getText().toString().toUpperCase(),
                        txtNuevoTelefonoHospedaje.getText().toString(),
                        web,
                        fb,
                        wt,
                        efe,
                        ele,
                        cred,
                        tipoSitio
                );
                MostrarCaracteristicas();
            }catch(Exception e){
                Toast.makeText(VistaSitioHospedaje.this, R.string.Error, Toast.LENGTH_LONG).show();
            }


        }
    }

    public void MostrarCaracteristicas(){
        lyCaracteristicas.setVisibility(View.VISIBLE);
        cdPortada.setVisibility(View.GONE);
        lySocialesSitio.setVisibility(View.GONE);
        lyPrimerRegistro.setVisibility(View.GONE);
        lyAccionesCaracteristicas.setVisibility(View.GONE);
    }
    public void mostrarHabitaciones(){
        lyCaracteristicas.setVisibility(View.GONE);
        lyHabitaciones.setVisibility(View.VISIBLE);
        txtDescripcionHabitacion.setText("");
        txtPrecioHabitacion.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void terminarRegistro(View View){
        MostrarHotelUsuario("admin", idHospedaje);
    }

    public void RegistrarCaracteristica(View View){
        CRUDServiciosExtra servicio = new CRUDServiciosExtra();
        btnSiguienteCaracteristicas.setVisibility(View.GONE);
        btnCompletarRegistro.setVisibility(View.VISIBLE);

        if(chbxWifiLobby.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxWifiLobby.getText().toString());
        }
        if(chbxWifiHabitacion.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxWifiHabitacion.getText().toString());
        }
        if(chbxPiscina.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxPiscina.getText().toString());
        }
        if(chbxSpa.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxSpa.getText().toString());
        }
        if(chbxBar.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxBar.getText().toString());
        }
        if(chbxGym.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxGym.getText().toString());
        }
        if(chbxEstacionamiento.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxEstacionamiento.getText().toString());
        }
        if(chbxMascotas.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxMascotas.getText().toString());
        }
        if(chbxAire.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxAire.getText().toString());
        }
        if(chbxRestaurante.isChecked()){
            servicio.nuevoServicioExtra(idHospedaje, chbxRestaurante.getText().toString());
        }
        mostrarHabitaciones();

    }

    public void RegistrarHabitacionSitio(View View){
        CRUDHabitacion habitacion = new CRUDHabitacion();
        if(txtDescripcionHabitacion.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtDescripcionHabitacion.requestFocus();
        }else if(txtPrecioHabitacion.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtPrecioHabitacion.requestFocus();
        }else{
            try{
                String cat[];

                cat = getResources().getStringArray(R.array.Camas);

                switch (spnNumeroCamas.getSelectedItem().toString()){
                    case "1":{
                        habitacion.nuevaHabitacion(cat[0],
                                idHospedaje,
                                1,
                                txtDescripcionHabitacion.getText().toString(),
                                Double.parseDouble(txtPrecioHabitacion.getText().toString()));
                        break;
                    }
                    case "2":{
                        habitacion.nuevaHabitacion(cat[1],
                                idHospedaje,
                                2,
                                txtDescripcionHabitacion.getText().toString(),
                                Double.parseDouble(txtPrecioHabitacion.getText().toString()));
                        break;
                    }
                    case "3":{
                        habitacion.nuevaHabitacion(cat[2],
                                idHospedaje,
                                3,
                                txtDescripcionHabitacion.getText().toString(),
                                Double.parseDouble(txtPrecioHabitacion.getText().toString()));
                        break;
                    }
                    case "4":{
                        habitacion.nuevaHabitacion(cat[3],
                                idHospedaje,
                                4,
                                txtDescripcionHabitacion.getText().toString(),
                                Double.parseDouble(txtPrecioHabitacion.getText().toString()));
                        break;
                    }
                }
                mostrarHabitaciones();
            }catch (Exception e){

            }
        }
    }

    public void MtbrNuevoNombreHospedaje(View View){
            if(tbrNuevoNombreHospedaje.getVisibility()==android.view.View.GONE){
                tbrNuevoNombreHospedaje.setVisibility(android.view.View.VISIBLE);
            }else{
                tbrNuevoNombreHospedaje.setVisibility(android.view.View.GONE);
            }
    }

    public void MlyPagos(View View){
        if(lyPagos.getVisibility()==android.view.View.GONE){
            lyPagos.setVisibility(android.view.View.VISIBLE);
        }else{
            lyPagos.setVisibility(android.view.View.GONE);
        }
    }

    public void MlyNuevaHabitacion(View View){
        if(lyNuevaHabitacion.getVisibility()==android.view.View.GONE){
            lyNuevaHabitacion.setVisibility(android.view.View.VISIBLE);
        }else{
            lyNuevaHabitacion.setVisibility(android.view.View.GONE);
        }
    }

    public void MlyNuevaCaracteristicaHospedaje(View View){
        if(lyNuevaCaracteristicaHospedaje.getVisibility()==android.view.View.GONE){
            lyNuevaCaracteristicaHospedaje.setVisibility(android.view.View.VISIBLE);
        }else{
            lyNuevaCaracteristicaHospedaje.setVisibility(android.view.View.GONE);
        }
    }


    public void MlyNuevaOpinion(View View){
        if(lyNuevaOpinion.getVisibility()==android.view.View.GONE){
            lyNuevaOpinion.setVisibility(android.view.View.VISIBLE);
        }else{
            lyNuevaOpinion.setVisibility(android.view.View.GONE);
        }
    }

    public void MtbrModificarCategoriaHospedaje(View View){
        if(tbrModificarCategoriaHospedaje.getVisibility()==android.view.View.GONE){
            tbrModificarCategoriaHospedaje.setVisibility(android.view.View.VISIBLE);
        }else{
            tbrModificarCategoriaHospedaje.setVisibility(android.view.View.GONE);
        }
    }
    public void MlyEditarTelefonoHospedaje(View View){
        if(lyEditarTelefonoHospedaje.getVisibility()==android.view.View.GONE){
            lyEditarTelefonoHospedaje.setVisibility(android.view.View.VISIBLE);
        }else{
            lyEditarTelefonoHospedaje.setVisibility(android.view.View.GONE);
        }
    }

    public void MlyRedesSocialesHospedaje(View View){
        if(lyRedesSocialesHospedaje.getVisibility()==android.view.View.GONE){
            lyRedesSocialesHospedaje.setVisibility(android.view.View.VISIBLE);
        }else{
            lyRedesSocialesHospedaje.setVisibility(android.view.View.GONE);
        }
    }

    public void chbxRedes(CheckBox chbxRedes, EditText red){
        chbxRedes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    red.setVisibility(View.VISIBLE);
                }else{
                    red.setVisibility(View.GONE);
                    red.setText("");

                }
            }
        });

    }

    public void chbxPagos(CheckBox chbxPagos, CardView pago, LinearLayout ly){
        if(ly.getVisibility()==View.GONE){
            ly.setVisibility(View.VISIBLE);
            chbxPagos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        pago.setVisibility(View.VISIBLE);
                    }else{
                        pago.setVisibility(View.GONE);

                    }
                }
            });
        }else{
            chbxPagos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        pago.setVisibility(View.VISIBLE);
                    }else{
                        pago.setVisibility(View.GONE);

                    }
                }
            });
        }


    }



    public void MtbrModificarDescripcionHospedaje(View View){
        if(tbrModificarDescripcionHospedaje.getVisibility()==android.view.View.GONE){
            tbrModificarDescripcionHospedaje.setVisibility(android.view.View.VISIBLE);
        }else{
            tbrModificarDescripcionHospedaje.setVisibility(android.view.View.GONE);
        }
    }

    public void listarPagos(View view){
        if(lyCardsPagos.getVisibility()== android.view.View.GONE){
            btnFlechaPagos.setRotation(0);
            lyCardsPagos.setVisibility(android.view.View.VISIBLE);
        }else{
            btnFlechaPagos.setRotation(90);
            lyCardsPagos.setVisibility(android.view.View.GONE);
        }
    }

    public void listarOpiniones(View view){
        if(lyOpinionesContenedor.getVisibility()== android.view.View.GONE){
            btnFlechaOpiniones.setRotation(0);
            lyOpinionesContenedor.setVisibility(android.view.View.VISIBLE);
        }else{
            btnFlechaOpiniones.setRotation(90);
            lyOpinionesContenedor.setVisibility(android.view.View.GONE);
        }
    }

    public void SetAtributosEditar(){
        btnEditarNombreHospedaje = findViewById(R.id.btnEditarNombreHospedaje);
        btnEditarCategoriaHospedaje = findViewById(R.id.btnEditarCategoriaHospedaje);
        btnEditarDescripcion = findViewById(R.id.btnEditarDescripcion);
        btnEditarTelefono = findViewById(R.id.btnEditarTelefono);
        btnEditarCaracteristicaHospedaje = findViewById(R.id.btnEditarCaracteristicaHospedaje);
        btnNuevaCaracteristicaHospedaje = findViewById(R.id.btnNuevaCaracteristicaHospedaje);
        tbrNuevoNombreHospedaje = findViewById(R.id.tbrNuevoNombreHospedaje);
        tbrModificarCategoriaHospedaje = findViewById(R.id.tbrModificarCategoriaHospedaje);
        lyRedesSocialesHospedaje = findViewById(R.id.lyRedesSocialesHospedaje);
        cardEditarPortadaHosteria = findViewById(R.id.cardEditarPortadaHosteria);
        btnEditarRedes = findViewById(R.id.btnEditarRedes);
        txvInformacionTitulo = findViewById(R.id.txvInformacionTitulo);
        tbrModificarDescripcionHospedaje = findViewById(R.id.tbrModificarDescripcionHospedaje);
        lyEditarTelefonoHospedaje = findViewById(R.id.lyEditarEmailUsuario);
        lyNuevaCaracteristicaHospedaje = findViewById(R.id.lyNuevaCaracteristicaHospedaje);
        btnBorrarCaracteristicaHospedaje = findViewById(R.id.btnBorrarCaracteristicaHospedaje);
        btnAgregarPago = findViewById(R.id.btnAgregarPago);
        btnAgregarHabitacion = findViewById(R.id.btnAgregarHabitacion);
        cardCaracteristicaHospedaje = findViewById(R.id.cardCaracteristicaHospedaje);
        btnFlechaCaracteristicas = findViewById(R.id.btnFlechaCaracteristicas);
        lyCardsPagos = findViewById(R.id.lyCardsPagos);
        btnFlechaPagos = findViewById(R.id.btnFlechaPagos);
        cardHabitacionHospedaje = findViewById(R.id.cardHabitacionHospedaje);
        btnFlechaHabitaciones = findViewById(R.id.btnFlechaHabitaciones);
        btnFlechaOpiniones = findViewById(R.id.btnFlechaOpiniones);
        cbxNuevaCategoriaHospedaje = findViewById(R.id.cbxNuevaCategoriaHospedaje);
        cardOpinion = findViewById(R.id.cardOpinion);
        lyNuevaOpinion = findViewById(R.id.lyNuevaOpinion);
        lyAccesoRedesSociales = findViewById(R.id.lyAccesoRedesSociales);
        tbrNombreHospedaje = findViewById(R.id.tbrNombreHospedaje);
        lyAccionesNombreSitio = findViewById(R.id.lyAccionesNombreSitio);
        tbrCategoriaHospedaje = findViewById(R.id.tbrCategoriaHospedaje);
        lyAccionesCategoriaHospedaje = findViewById(R.id.lyAccionesCategoriaHospedaje);
        txvDescripcionHospedajeModificar = findViewById(R.id.txvDescripcionHospedajeModificar);
        lyAccionesDescripcionHospedaje = findViewById(R.id.lyAccionesDescripcionHospedaje);
        lyTelefono = findViewById(R.id.lyTelefono);
        lyPagos = findViewById(R.id.lyPagos);
        lyNuevaHabitacion = findViewById(R.id.lyNuevaHabitacion);
        lyOpinionesHospedaje = findViewById(R.id.lyOpinionesHospedaje);
        lyAccionesTelefono = findViewById(R.id.lyAccionesTelefono);
        lyBotones = findViewById(R.id.lyBotones);
        chbxFacebookHospedaje = findViewById(R.id.chbxFacebookHospedaje);
        chbxInstagramHospedaje = findViewById(R.id.chbxInstagramHospedaje);
        chbxWebHospedaje = findViewById(R.id.chbxWebHospedaje);
        txtInstagramHospedaje = findViewById(R.id.txtInstagramHospedaje);
        txtWebHospedaje = findViewById(R.id.txtWebHospedaje);
        txtFacebookHospedaje = findViewById(R.id.txtFacebookHospedaje);
        chbxPagoefectivo = findViewById(R.id.chbxPagoefectivo);
        chbxPagoCredito = findViewById(R.id.chbxPagoCredito);
        chbxPagoElectronico = findViewById(R.id.chbxPagoElectronico);
        cardPagoEfectivo = findViewById(R.id.cardPagoEfectivo);
        cardPagoCredito = findViewById(R.id.cardPagoCredito);
        cardPagoElectronico = findViewById(R.id.cardPagoElectronico);
        txvNombreHospedajeModificar = findViewById(R.id.txvNombreHospedajeModificar);
        txtNuevoNombreHospedaje = findViewById(R.id.txtNuevoNombreHospedaje);
        txtNuevaDescripcionHospedaje = findViewById(R.id.txtNuevaDescripcionHospedaje);
        txtNuevoTelefonoHospedaje = findViewById(R.id.txtNuevoTelefonoHospedaje);
        txtLatitudSitio =  findViewById(R.id.txtLatitudSitio);
        txtLongitudSitio = findViewById(R.id.txtLongitudSitio);
        lyCaracteristicas = findViewById(R.id.lyCaracteristicas);
        lyHabitaciones = findViewById(R.id.lyHabitaciones);
        cdPortada = findViewById(R.id.cdPortada);
        lySocialesSitio = findViewById(R.id.lySocialesSitio);
        lyPrimerRegistro = findViewById(R.id.lyPrimerRegistro);
        cdDistancia = findViewById(R.id.cdDistancia);
        spnAdministradoresSitio = findViewById(R.id.spnAdministradoresSitio);
        lyModificarAdministradorSitio = findViewById(R.id.lyModificarAdministradorSitio);
        lyInfoAdmin = findViewById(R.id.lyInfoAdmin);
        lyUbicacion = findViewById(R.id.lyUbicacion);
        lyAccionesCaracteristicas = findViewById(R.id.lyAccionesCaracteristicas);
        txvTelefonoHospedajeModificar = findViewById(R.id.txvTelefonoHospedajeModificar);
        txvIdOpinionEditar = findViewById(R.id.txvIdOpinionEditar);
        imgCheck = findViewById(R.id.imgCheck);
        lyCar = findViewById(R.id.lyCar);
        txtCaracteristicaHospedaje = findViewById(R.id.txtCaracteristicaHospedaje);
        lyServicios = findViewById(R.id.lyServicios);
        lyHabitacionesMostrar = findViewById(R.id.lyHabitacionesMostrar);
        lyInfoHabitacionContenedor = findViewById(R.id.lyInfoHabitacionContenedor);
        txvCategoriaHabitacionHospedaje = findViewById(R.id.txvCategoriaHabitacionHospedaje);
        txvDescripcionHabitacionHospedaje = findViewById(R.id.txvDescripcionHabitacionHospedaje);
        lyCamasHabitacion = findViewById(R.id.lyCamasHabitacion);
        txvNUmeroCamasHabitacion = findViewById(R.id.txvNUmeroCamasHabitacion);
        imgCamas = findViewById(R.id.imgCamas);
        btnEditarOpinion = findViewById(R.id.btnEditarOpinion);
        btnBorrarOpinion = findViewById(R.id.btnBorrarOpinion);
        lyHabitacionContenedor=findViewById(R.id.lyHabitacionContenedor);
        rtbOpinionHospedaje = findViewById(R.id.rtbOpinionHospedaje);
        lyContenedorOpinion = findViewById(R.id.lyContenedorOpinion);
        lyOpinionesContenedor = findViewById(R.id.lyOpinionesContenedor);
        txvDistanciaSitio =  findViewById(R.id.txvDistanciaSitio);
        btnSiguienteCaracteristicas = findViewById(R.id.btnSiguienteCaracteristicas);
        btnSiguienteSitio = findViewById(R.id.btnSiguienteSitio);
        btnCompletarRegistro = findViewById(R.id.btnCompletarRegistro);
        txvComentarioOpinion = findViewById(R.id.txvComentarioOpinion);
        txvNombreUsuarioOpinion = findViewById(R.id.txvNombreUsuarioOpinion);
        lyInfoOpinion = findViewById(R.id.lyInfoOpinion);
        imgFotoPerfilUsuario = findViewById(R.id.imgFotoPerfilUsuario);
        cardFotoPerfilUsuario = findViewById(R.id.cardFotoPerfilUsuario);
        lyInfoPerfilOpinion = findViewById(R.id.lyInfoPerfilOpinion);
        imgPortadaHospedaje = findViewById(R.id.imgPortadaHospedaje);
        txtDescripcionHabitacion = findViewById(R.id.txtDescripcionHabitacion);
        txtPrecioHabitacion = findViewById(R.id.txtDescripcionHabitacion);
        spnNumeroCamas = findViewById(R.id.spnNumeroCamas);
        btnGuardarHabitacion = findViewById(R.id.btnGuardarHabitacion);
        txtComentarioHospedaje = findViewById(R.id.txtComentarioHospedaje);
        rtbEstrellas = findViewById(R.id.rtbEstrellas);
        lyAccionesOpinion = findViewById(R.id.lyAccionesOpinion);

        chbxWifiLobby = findViewById(R.id.chbxWifiLobby);
        chbxWifiHabitacion = findViewById(R.id.chbxWifiHabitacion);
        chbxPiscina = findViewById(R.id.chbxPiscina);
        chbxSpa = findViewById(R.id.chbxSpa);
        chbxBar = findViewById(R.id.chbxBar);
        chbxGym = findViewById(R.id.chbxGym);
        chbxEstacionamiento = findViewById(R.id.chbxEstacionamiento);
        chbxMascotas = findViewById(R.id.chbxMascotas);
        chbxAire = findViewById(R.id.chbxAire);
        chbxRestaurante = findViewById(R.id.chbxRestaurante);
        txvPrecioHabitacionHospedaje = findViewById(R.id.txvPrecioHabitacionHospedaje);
        lyInfoHabitacion = findViewById(R.id.lyInfoHabitacion);
        txvIdOpinion = findViewById(R.id.txvIdOpinion);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void MostrarHotelUsuario(String admin, String idHospedaje){

        if(Usuario){
            lyRedesSocialesHospedaje.setVisibility(View.GONE);
            cardEditarPortadaHosteria.setVisibility(View.GONE);
            btnEditarRedes.setVisibility(View.GONE);
            txvInformacionTitulo.setVisibility(View.GONE);
            btnEditarNombreHospedaje.setVisibility(View.GONE);
            btnEditarCategoriaHospedaje.setVisibility(View.GONE);
            btnEditarDescripcion.setVisibility(View.GONE);
            btnEditarTelefono.setVisibility(View.GONE);
            btnEditarCaracteristicaHospedaje.setVisibility(View.GONE);
            btnNuevaCaracteristicaHospedaje.setVisibility(View.GONE);
            tbrNuevoNombreHospedaje.setVisibility(View.GONE);
            tbrModificarCategoriaHospedaje.setVisibility(View.GONE);
            tbrModificarDescripcionHospedaje.setVisibility(View.GONE);
            lyEditarTelefonoHospedaje.setVisibility(View.GONE);
            lyNuevaCaracteristicaHospedaje.setVisibility(View.GONE);
            btnBorrarCaracteristicaHospedaje.setVisibility(View.GONE);
            btnAgregarPago.setVisibility(View.GONE);
            cardCaracteristicaHospedaje.setVisibility((View.GONE));
            lyCardsPagos.setVisibility(View.GONE);
            cardOpinion.setVisibility(View.GONE);
            lyPagos.setVisibility(View.GONE);
            lyNuevaHabitacion.setVisibility(View.GONE);
            lyBotones.setVisibility(View.GONE);
            lyInfoAdmin.setVisibility(View.GONE);
            lyUbicacion.setVisibility(View.GONE);

            CRUDSitioHospedaje sitio = new CRUDSitioHospedaje();
            SitioHospedaje sitioHospedaje;
            Distancia dis = new Distancia();
            sitioHospedaje = sitio.listarSitioHospedaje(idHospedaje);
            byte[] bytes = Base64.getDecoder().decode(sitioHospedaje.getPortada());
            Bitmap bit = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imgPortadaHospedaje.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imgPortadaHospedaje.setImageBitmap(bit);
            txvNombreHospedajeModificar.setText(sitioHospedaje.getNombre());
            txvDescripcionHospedajeModificar.setText(sitioHospedaje.getDescripcion());
            txvTelefonoHospedajeModificar.setText(sitioHospedaje.getTelefono());
//COMIENZA DISTANCIA
            /*PERMISO PARA ACCEDER AL GPS*/

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Distancia distancia = new Distancia();
            SitioHospedaje finalSitioHospedaje = sitioHospedaje;
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    distanciaKM = String.valueOf(Math.round(distancia.distanciaCoordenadas(
                            Double.parseDouble(finalSitioHospedaje.getLatitud()),
                            Double.parseDouble(finalSitioHospedaje.getLongitud()),
                            location.getLatitude(),
                            location.getLongitude())*100.0)/100.0 ) + " KM";
                    txvDistanciaSitio.setText(distanciaKM);
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
            if(sitioHospedaje.getPagoEfectivo()==1){
                cardPagoEfectivo.setVisibility(View.VISIBLE);
            }else{
                cardPagoEfectivo.setVisibility(View.GONE);
            }
            if(sitioHospedaje.getPagoTarjeta()==1){
                cardPagoCredito.setVisibility(View.VISIBLE);
            }else{
                cardPagoCredito.setVisibility(View.GONE);
            }
            if(sitioHospedaje.getPagoElectronico()==1){
                cardPagoElectronico.setVisibility(View.VISIBLE);
            }else{
                cardPagoElectronico.setVisibility(View.GONE);
            }

            //Distancia();
            mostrarCaracteristicas();
            mostrarHabitacionesUsuario();
            mostrarOpiniones();

        }else if(Registrar){
            lyAccesoRedesSociales.setVisibility(View.GONE);
            tbrNombreHospedaje.setVisibility(View.GONE);
            lyAccionesNombreSitio.setVisibility(View.GONE);
            tbrCategoriaHospedaje.setVisibility(View.GONE);
            lyAccionesCategoriaHospedaje.setVisibility(View.GONE);
            btnEditarDescripcion.setVisibility(View.GONE);
            txvDescripcionHospedajeModificar.setVisibility(View.GONE);
            lyAccionesDescripcionHospedaje.setVisibility(View.GONE);
            lyTelefono.setVisibility(View.GONE);
            btnFlechaCaracteristicas.setVisibility(View.GONE);
            cardCaracteristicaHospedaje.setVisibility(View.GONE);
            btnFlechaPagos.setVisibility(View.GONE);
            lyCardsPagos.setVisibility(View.GONE);
            cardHabitacionHospedaje.setVisibility(View.GONE);
            lyOpinionesHospedaje.setVisibility(View.GONE);
            lyAccionesTelefono.setVisibility(View.GONE);
            btnFlechaHabitaciones.setVisibility(View.GONE);
            lyNuevaCaracteristicaHospedaje.setVisibility(View.GONE);
            lyPagos.setVisibility(View.GONE);
            lyNuevaHabitacion.setVisibility(View.GONE);
            cardPagoEfectivo.setVisibility(View.GONE);
            cardPagoCredito.setVisibility(View.GONE);
            cardPagoElectronico.setVisibility(View.GONE);
            lyCaracteristicas.setVisibility(View.GONE);
            lyHabitaciones.setVisibility(View.GONE);
            cdDistancia.setVisibility(View.GONE);
            lyModificarAdministradorSitio.setVisibility(View.GONE);


            chbxRedes(chbxFacebookHospedaje,txtFacebookHospedaje);
            chbxRedes(chbxInstagramHospedaje,txtInstagramHospedaje);
            chbxRedes(chbxWebHospedaje,txtWebHospedaje);

            chbxPagos(chbxPagoefectivo, cardPagoEfectivo, lyCardsPagos);
            chbxPagos(chbxPagoCredito, cardPagoCredito, lyCardsPagos);
            chbxPagos(chbxPagoElectronico, cardPagoElectronico, lyCardsPagos);
        }else if(Editar){
            lyRedesSocialesHospedaje.setVisibility(View.GONE);
            txvInformacionTitulo.setVisibility(View.GONE);
            lyOpinionesHospedaje.setVisibility(View.GONE);
            lyBotones.setVisibility(View.GONE);
            tbrNuevoNombreHospedaje.setVisibility(View.GONE);
            tbrModificarCategoriaHospedaje.setVisibility(View.GONE);
            tbrModificarDescripcionHospedaje.setVisibility(View.GONE);
            lyEditarTelefonoHospedaje.setVisibility(View.GONE);
            lyNuevaCaracteristicaHospedaje.setVisibility(View.GONE);
            cardCaracteristicaHospedaje.setVisibility(View.GONE);
            lyPagos.setVisibility(View.GONE);
            lyCardsPagos.setVisibility(View.GONE);
            lyNuevaHabitacion.setVisibility(View.GONE);
            cardHabitacionHospedaje.setVisibility(View.GONE);
            lyOpinionesHospedaje.setVisibility(View.GONE);

            chbxRedes(chbxFacebookHospedaje,txtFacebookHospedaje);
            chbxRedes(chbxInstagramHospedaje,txtInstagramHospedaje);
            chbxRedes(chbxWebHospedaje,txtWebHospedaje);

            chbxPagos(chbxPagoefectivo, cardPagoEfectivo, lyCardsPagos);
            chbxPagos(chbxPagoCredito, cardPagoCredito, lyCardsPagos);
            chbxPagos(chbxPagoElectronico, cardPagoElectronico, lyCardsPagos);

        }else if(admin == "admin"){
            lyRedesSocialesHospedaje.setVisibility(View.GONE);
            cardEditarPortadaHosteria.setVisibility(View.GONE);
            btnEditarRedes.setVisibility(View.GONE);
            txvInformacionTitulo.setVisibility(View.GONE);
            btnEditarNombreHospedaje.setVisibility(View.GONE);
            btnEditarCategoriaHospedaje.setVisibility(View.GONE);
            btnEditarDescripcion.setVisibility(View.GONE);
            btnEditarTelefono.setVisibility(View.GONE);
            btnEditarCaracteristicaHospedaje.setVisibility(View.GONE);
            btnNuevaCaracteristicaHospedaje.setVisibility(View.GONE);
            tbrNuevoNombreHospedaje.setVisibility(View.GONE);
            tbrModificarCategoriaHospedaje.setVisibility(View.GONE);
            tbrModificarDescripcionHospedaje.setVisibility(View.GONE);
            lyEditarTelefonoHospedaje.setVisibility(View.GONE);
            lyNuevaCaracteristicaHospedaje.setVisibility(View.GONE);
            btnBorrarCaracteristicaHospedaje.setVisibility(View.GONE);
            btnAgregarPago.setVisibility(View.GONE);
            btnAgregarHabitacion.setVisibility(View.GONE);
            cardCaracteristicaHospedaje.setVisibility((View.GONE));
            lyCardsPagos.setVisibility(View.GONE);
            cardOpinion.setVisibility(View.GONE);
            lyPagos.setVisibility(View.GONE);
            lyNuevaHabitacion.setVisibility(View.GONE);
            lyBotones.setVisibility(View.GONE);

            CRUDSitioHospedaje sitio = new CRUDSitioHospedaje();
            SitioHospedaje sitioHospedaje;
            sitioHospedaje = sitio.listarSitioHospedaje(idHospedaje);
            txvNombreHospedajeModificar.setText(sitioHospedaje.getNombre());
        }
    }

    public void btnCaracteristicas(View View){
        if(lyServicios.getVisibility()== android.view.View.GONE){
            btnFlechaCaracteristicas.setRotation(0);
            lyServicios.setVisibility(android.view.View.VISIBLE);
        }else{
            btnFlechaCaracteristicas.setRotation(90);
            lyServicios.setVisibility(android.view.View.GONE);
        }

    }

    public void btnHabitaciones(View View){
        if(lyHabitacionesMostrar.getVisibility()== android.view.View.GONE){
            btnFlechaHabitaciones.setRotation(0);
            lyHabitacionesMostrar.setVisibility(android.view.View.VISIBLE);
        }else{
            btnFlechaHabitaciones.setRotation(90);
            lyHabitacionesMostrar.setVisibility(android.view.View.GONE);
        }
    }

    public void mostrarCaracteristicas(){
        CRUDServiciosExtra servicio = new CRUDServiciosExtra();
        List<ServiciosExtra> listaServicio = servicio.listaServicios(idHospedaje);
        lyCar.setVisibility(View.GONE);
        for (ServiciosExtra ServiciosExtra: listaServicio) {
            LinearLayout ly = new LinearLayout(this);
            CardView nuevaCaracteristica = new CardView(this);
            ImageView image = new ImageView(this);
            TextView tx = new TextView(this);
            Typeface tface = ResourcesCompat.getFont(this, R.font.open_sans_bold);
            nuevaCaracteristica.setLayoutParams(cardCaracteristicaHospedaje.getLayoutParams());
            ly.setLayoutParams(lyCar.getLayoutParams());
            image.setLayoutParams(imgCheck.getLayoutParams());
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_check_circle_24));
            tx.setLayoutParams(txtCaracteristicaHospedaje.getLayoutParams());
            tx.setTypeface(tface);
            tx.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            switch (ServiciosExtra.getDescripcion()){
                case "WIFI EN LOBBY":{
                    tx.setText(R.string.WifiLobby);
                    break;
                }
                case "WIFI EN LA HABITACION":{
                    tx.setText(R.string.WifiCuarto);
                    break;
                }
                case "PISCIA":{
                    tx.setText(R.string.Piscina);
                    break;
                }
                case "SPA":{
                    tx.setText(R.string.Spa);
                    break;
                }
                case "ESTACIONAMIENTO":{
                    tx.setText(R.string.Estacionamiento);
                    break;
                }
                case "MASCOTAS PERMITIDAS":{
                    tx.setText(R.string.Mascotas);
                    break;
                }
                case "RESTAURANTE":{
                    tx.setText(R.string.Restaurante);
                    break;
                }
                case "AIRE ACONDICIONADO":{
                    tx.setText(R.string.Aire);
                    break;
                }
                case "BAR":{
                    tx.setText(R.string.Bar);
                    break;
                }
                case "GIMNASIO":{
                    tx.setText(R.string.Gym);
                    break;
                }
            }
            nuevaCaracteristica.setRadius(50);
            nuevaCaracteristica.setCardBackgroundColor(getResources().getColor(R.color.card_back));
            nuevaCaracteristica.addView(ly);

            ly.addView(image);
            ly.addView(tx);
            lyServicios.addView(nuevaCaracteristica);

        }
    }

    public void mostrarHabitacionesUsuario(){
        CRUDHabitacion habitacion = new CRUDHabitacion();
        List<Habitacion> listaHabitacion = habitacion.listarHabitaciones(idHospedaje);
        cardHabitacionHospedaje.setVisibility(View.GONE);
        for(Habitacion Habitacion: listaHabitacion){
            CardView cdhabitacion = new CardView(this);
            LinearLayout lyContenedor = new LinearLayout(this);
            LinearLayout lyinfo = new LinearLayout(this);
            TextView precio = new TextView(this);
            LinearLayout info = new LinearLayout(this);
            TextView cat = new TextView(this);
            TextView des = new TextView(this);
            LinearLayout camas = new LinearLayout(this);
            TextView ncam = new TextView(this);
            ImageView icam = new ImageView(this);


            cdhabitacion.setLayoutParams(cardHabitacionHospedaje.getLayoutParams());

            lyContenedor.setLayoutParams(lyHabitacionContenedor.getLayoutParams());
            lyContenedor.setOrientation(LinearLayout.VERTICAL);
            lyContenedor.setPadding(10,10,10,10);
            lyinfo.setLayoutParams(lyInfoHabitacionContenedor.getLayoutParams());
            lyinfo.setOrientation(LinearLayout.HORIZONTAL);


            precio.setLayoutParams(txvPrecioHabitacionHospedaje.getLayoutParams());
            info.setLayoutParams(lyInfoHabitacion.getLayoutParams());
            info.setOrientation(LinearLayout.VERTICAL);

            cat.setLayoutParams(txvCategoriaHabitacionHospedaje.getLayoutParams());
            des.setLayoutParams(txvDescripcionHabitacionHospedaje.getLayoutParams());
            camas.setLayoutParams(lyCamasHabitacion.getLayoutParams());
            camas.setOrientation(LinearLayout.HORIZONTAL);

            ncam.setLayoutParams(txvNUmeroCamasHabitacion.getLayoutParams());
            icam.setLayoutParams(imgCamas.getLayoutParams());

            cdhabitacion.setRadius(50);
            cdhabitacion.setCardBackgroundColor(getResources().getColor(R.color.card_back));

            Typeface tface = ResourcesCompat.getFont(this, R.font.open_sans_bold);
            precio.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            precio.setTypeface(tface);
            precio.setTextColor(Color.BLACK);
            precio.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            precio.setMinWidth(300);
            precio.setText("$" + String.valueOf(Habitacion.getPrecio()));

            Typeface tcat = ResourcesCompat.getFont(this, R.font.open_sans_bold);
            cat.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            cat.setTypeface(tcat);
            cat.setTextColor(Color.BLACK);
            cat.setText(Habitacion.getCategoriaHabitacion());

            Typeface tdes = ResourcesCompat.getFont(this, R.font.open_sans);
            des.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            des.setTypeface(tdes);
            des.setTextColor(Color.BLACK);
            des.setText(Habitacion.getDescripcion());

            Typeface tncam = ResourcesCompat.getFont(this, R.font.open_sans);
            ncam.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            ncam.setTypeface(tncam);
            ncam.setText(String.valueOf(Habitacion.getNumeroCamas()));

            icam.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_single_bed_24));

            cdhabitacion.addView(lyContenedor);
            lyContenedor.addView(lyinfo);
            lyinfo.addView(precio);
            lyinfo.addView(info);
            info.addView(cat);
            info.addView(des);
            info.addView(camas);
            camas.addView(ncam);
            camas.addView(icam);
            lyHabitacionesMostrar.addView(cdhabitacion);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mostrarOpiniones(){
        CRUDOpiniones opiniones = new CRUDOpiniones();
        Usuarios usuarios;
        CRUDUsuarios usuario = new CRUDUsuarios();
        List<Opiniones> listaOpiniones = opiniones.listarOpiniones(idHospedaje);
        cardOpinion.setVisibility(View.GONE);
        for(Opiniones opinion: listaOpiniones){
            CardView cdContenedor = new CardView(this);
            LinearLayout lyContenedor = new LinearLayout(this);
            LinearLayout lyInfoPerfil = new LinearLayout(this);
            CardView cdFoto = new CardView(this);
            ImageView ifoto = new ImageView(this);
            ImageView bEditar = new ImageView(this);
            ImageView bCancelar = new ImageView(this);
            LinearLayout lyInfo = new LinearLayout(this);
            TextView txvNombre = new TextView(this);
            TextView txvComen = new TextView(this);
            RatingBar estr = new RatingBar(this);
            LinearLayout lyAcciones = new LinearLayout(this);
            TextView txvId = new TextView(this);
            txvId.setVisibility(View.GONE);
            txvId.setText(String.valueOf(opinion.getIdOpinion()));


            cdContenedor.setLayoutParams(cardOpinion.getLayoutParams());

            lyContenedor.setLayoutParams(lyContenedorOpinion.getLayoutParams());
            lyContenedor.setOrientation(LinearLayout.VERTICAL);
            lyContenedor.setPadding(10, 10, 10, 10);

            lyInfoPerfil.setLayoutParams(lyInfoPerfilOpinion.getLayoutParams());
            lyInfoPerfil.setOrientation(LinearLayout.HORIZONTAL);

            cdFoto.setLayoutParams(cardFotoPerfilUsuario.getLayoutParams());
            ifoto.setLayoutParams(imgFotoPerfilUsuario.getLayoutParams());
            lyInfo.setLayoutParams(lyInfoOpinion.getLayoutParams());
            lyInfo.setOrientation(LinearLayout.VERTICAL);

            txvNombre.setLayoutParams(txvNombreUsuarioOpinion.getLayoutParams());
            txvComen.setLayoutParams(txvComentarioOpinion.getLayoutParams());
            estr.setLayoutParams(rtbOpinionHospedaje.getLayoutParams());

            cdContenedor.setRadius(25);

            cdFoto.setRadius(100);

            SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
            String emailGuardado = share.getString("Email","nohay");
            lyAcciones.setLayoutParams(lyAccionesOpinion.getLayoutParams());
            bEditar.setLayoutParams(btnEditarOpinion.getLayoutParams());
            bCancelar.setLayoutParams(btnBorrarOpinion.getLayoutParams());
            bEditar.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_edit_24));
            bCancelar.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_delete_24));
            lyAcciones.addView(bEditar);
            lyAcciones.addView(bCancelar);

            if(emailGuardado.equals(opinion.getEmailHuesped())){
                lyAcciones.setVisibility(View.VISIBLE);
                bEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txvIdOpinionEditar.setText(String.valueOf(opinion.getIdOpinion()));
                        rtbEstrellas.setRating(opinion.getEstrellas());
                        txtComentarioHospedaje.setText(opinion.getComentario());

                        lyNuevaOpinion.setVisibility(View.VISIBLE);
                        txtComentarioHospedaje.requestFocus();

                    }
                });
                bCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                        dialog.setCancelable(true);
                        dialog.setTitle("Eliminar Opinion");
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
                                    opiniones.eliminarOpinion((int)opinion.getIdOpinion(),emailGuardado);
                                }catch(Exception e){
                                }

                            }
                        });
                        dialog.show();

                    }
                });
            }else{
                lyAcciones.setVisibility(View.GONE);
            }


            lyInfo.setOrientation(LinearLayout.VERTICAL);

            usuarios = usuario.usuario(opinion.getEmailHuesped());

            byte[] bytes = Base64.getDecoder().decode(usuarios.getFoto());
            Bitmap bit = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            ifoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ifoto.setImageBitmap(bit);

            Typeface tnom = ResourcesCompat.getFont(this, R.font.open_sans_condensed_bold);
            txvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            txvNombre.setText(usuarios.getNombre() + " " + usuarios.getApellido());
            txvNombre.setTextColor(Color.BLACK);
            txvNombre.setTypeface(tnom);

            Typeface tcomen = ResourcesCompat.getFont(this, R.font.open_sans);
            txvComen.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            txvComen.setText(opinion.getComentario());
            txvComen.setTextColor(Color.GRAY);
            txvComen.setTypeface(tcomen);

            estr.setNumStars(5);
            estr.setRating(opinion.getEstrellas());
            estr.setEnabled(false);
            TextView t = new TextView(this);
            cdContenedor.addView(lyContenedor);
            lyContenedor.addView(lyInfoPerfil);
            lyContenedor.addView(estr);
            lyContenedor.addView(lyAcciones);
            lyInfoPerfil.addView(cdFoto);
            lyInfoPerfil.addView(lyInfo);
            lyInfoPerfil.addView(t);
            cdFoto.addView(ifoto);
            lyInfo.addView(txvNombre);
            lyInfo.addView(txvComen);
            lyOpinionesContenedor.addView(cdContenedor);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registrarOpinion(View View){
        CRUDOpiniones opinion = new CRUDOpiniones();

        if(rtbEstrellas.getRating()<1){
            Toast.makeText(VistaSitioHospedaje.this, R.string.SinEstrellas, Toast.LENGTH_LONG).show();
            rtbEstrellas.requestFocus();
        }else if(txtComentarioHospedaje.getText().toString().isEmpty()){
            Toast.makeText(VistaSitioHospedaje.this, R.string.CampoVacio, Toast.LENGTH_LONG).show();
            txtComentarioHospedaje.requestFocus();
        }else{
            try{
                int est = (int )rtbEstrellas.getRating();
                if(opinion.nuevaOpinion(est,
                        txtComentarioHospedaje.getText().toString(),
                        idHospedaje,
                        email
                        )== true){
                    Toast.makeText(VistaSitioHospedaje.this, R.string.ExisteComentario, Toast.LENGTH_LONG).show();
                }else{
                    MostrarHotelUsuario("no", idHospedaje);
                }
            }catch(Exception e){
                Toast.makeText(VistaSitioHospedaje.this, R.string.Error, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancelarOpinion(View View){
        lyNuevaOpinion.setVisibility(android.view.View.GONE);
        rtbEstrellas.setRating(0);
        txtComentarioHospedaje.setText("");
    }



    public void cargarImagen(View View){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Imagen"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            imgPortadaHospedaje.setImageURI(path);
            imgPortadaHospedaje.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }
}
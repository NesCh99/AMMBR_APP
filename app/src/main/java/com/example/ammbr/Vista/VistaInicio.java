package com.example.ammbr.Vista;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammbr.Modelo.CRUD.CRUDOpiniones;
import com.example.ammbr.Modelo.CRUD.CRUDSitioHospedaje;
import com.example.ammbr.Modelo.CRUD.CRUDUsuarios;
import com.example.ammbr.Modelo.Negocio.Distancia;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.R;

import java.util.Base64;
import java.util.List;

public class VistaInicio extends AppCompatActivity {

    CardView cdPerfil, cdVistaHotel, cdPortadaHotel, cdPuntuacion, cdIrMiSitio, cdIrAdmin, cdIrSitioHospedaje, cdbtnInicio;
    TextView txvNombreVista, txvNombreHotelCard, txvIdHospedaje, txvPuntuacionSitioVista, txvRecomendacionVista, txvDist, txvDistanciaVista;
    ImageButton btnInformacionSitioVista;
    LinearLayout lyContenedorSitio, lyContenedorInfo, lyPuntuacionVista, lyDistanciaVista, lySitiosVista;
    ImageView imgPortadaHotel, imgUbi, imgFotoPerfilVista;
    String distanciaKM;
    LocationManager locationManager;
    Button btnIrSitioCercano;
    Distancia distancia = new Distancia();
    Usuarios usuario;
    CRUDUsuarios usuarios = new CRUDUsuarios();
    int User;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_inicio);
        setAttributes();
        SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
        usuario = usuarios.usuario(share.getString("Email","nohay"));
        User = usuario.getTipoUsuario();

        if(User==3){
            cdbtnInicio.setVisibility(View.GONE);
        }
        txvNombreVista.setText(usuario.getNombre());
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        ListarHospedajes();
        try{
            byte[] bytes = Base64.getDecoder().decode(usuario.getFoto());
            Bitmap bit = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imgFotoPerfilVista.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imgFotoPerfilVista.setImageBitmap(bit);
        }catch(Exception e){
            Toast.makeText(VistaInicio.this, R.string.Error, Toast.LENGTH_LONG).show();
        }


    }

    public void menu(View View) {
        switch (User) {
            case 1: {
                if(cdIrAdmin.getVisibility()== android.view.View.GONE){
                    cdIrAdmin.setVisibility(android.view.View.VISIBLE);
                    cdIrSitioHospedaje.setVisibility(android.view.View.VISIBLE);
                }else{
                    cdIrAdmin.setVisibility(android.view.View.GONE);
                    cdIrSitioHospedaje.setVisibility(android.view.View.GONE);
                }
                break;
            }
            case 2: {
                if(cdIrMiSitio.getVisibility()== android.view.View.GONE){
                    cdIrMiSitio.setVisibility(android.view.View.VISIBLE);
                }else{
                    cdIrMiSitio.setVisibility(android.view.View.GONE);
                }
                break;
            }

        }
    }

    public void setAttributes(){
        cdPerfil = findViewById(R.id.cdPerfil);
        imgFotoPerfilVista = findViewById(R.id.imgFotoPerfilVista);
        txvNombreVista = findViewById(R.id.txvNombreVista);
        cdVistaHotel = findViewById(R.id.cdVistaHotel);
        lyContenedorSitio = findViewById(R.id.lyContenedorSitio);
        cdPortadaHotel = findViewById(R.id.cdPortadaHotel);
        imgPortadaHotel = findViewById(R.id.imgPortadaHotel);
        lyContenedorInfo = findViewById(R.id.lyContenedorInfo);
        txvNombreHotelCard = findViewById(R.id.txvNombreHotelCard);
        txvIdHospedaje = findViewById(R.id.txvIdHospedaje);
        lyPuntuacionVista = findViewById(R.id.lyPuntuacionVista);
        txvPuntuacionSitioVista = findViewById(R.id.txvPuntuacionSitioVista);
        txvRecomendacionVista = findViewById(R.id.txvRecomendacionVista);
        lyDistanciaVista = findViewById(R.id.lyDistanciaVista);
        imgUbi = findViewById(R.id.imgUbi);
        txvDist = findViewById(R.id.txvDist);
        txvDistanciaVista = findViewById(R.id.txvDistanciaVista);
        btnInformacionSitioVista = findViewById(R.id.btnInformacionSitioVista);
        cdPuntuacion = findViewById(R.id.cdPuntuacion);
        lySitiosVista = findViewById(R.id.lySitiosVista);
        cdIrMiSitio = findViewById(R.id.cdIrMiSitio);
        cdIrAdmin = findViewById(R.id.cdIrAdmin);
        cdIrSitioHospedaje = findViewById(R.id.cdIrSitioHospedaje);
        cdbtnInicio = findViewById(R.id.cdbtnInicio);

        btnIrSitioCercano = findViewById(R.id.btnIrSitioCercano);

    }

    public void IrPerfil(View View){
        Intent GoUsuario = new Intent(this, VistaUsuario.class);
        switch(User){
            case 1:{
                GoUsuario.putExtra("Usuario", "Root");
                break;
            }
            case 2:{
                GoUsuario.putExtra("Usuario", "Admins");
                break;
            }
            case 3:{
                GoUsuario.putExtra("Usuario", "Huesped");
                break;
            }
        }
        GoUsuario.putExtra("ListarUsuario", true);
        startActivity(GoUsuario);
    }
    public void ListarHospedajeCercano(View View){

        Intent HospedajeCercano = new Intent(this, VistaSitioHospedaje.class);
        HospedajeCercano.putExtra("Usuario", true);
        HospedajeCercano.putExtra("Cercano", true);
        try{
            startActivity(HospedajeCercano);
        }catch(Exception e){
            Toast.makeText(VistaInicio.this, R.string.SinSitios, Toast.LENGTH_LONG).show();
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ListarHospedajes(){
        CRUDSitioHospedaje sitio = new CRUDSitioHospedaje();
        CRUDOpiniones opiniones = new CRUDOpiniones();
        List<SitioHospedaje> lista = sitio.listarSitiosHospedaje();
        SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
        usuario = usuarios.usuario(share.getString("Email","no hay"));
        int listasize = lista.size();
        cdVistaHotel.setVisibility(View.GONE);
        if(listasize>0){
            for (SitioHospedaje sitioHospedaje: lista){
                try{
                    System.out.println(sitioHospedaje.getNombre());
                    CardView cdHotel = new CardView(this);
                    LinearLayout lyContenedor = new LinearLayout(this);
                    CardView cdPortada = new CardView(this);
                    ImageView iPortada = new ImageView(this);
                    LinearLayout lyInfo = new LinearLayout(this);
                    TextView txvNombre = new TextView(this);
                    TextView txvID = new TextView(this);
                    LinearLayout lyPuntuacion = new LinearLayout(this);
                    CardView cdPun = new CardView(this);
                    TextView txvPuntuacion = new TextView(this);
                    TextView txvRecomendacion = new TextView(this);
                    LinearLayout lyDistancia = new LinearLayout(this);
                    ImageView imgU = new ImageView(this);
                    TextView txvDis = new TextView(this);
                    TextView txvDistancia = new TextView(this);
                    ImageButton btnInformacion = new ImageButton(this);

                    cdHotel.setLayoutParams(cdVistaHotel.getLayoutParams());
                    cdHotel.setCardBackgroundColor(getResources().getColor(R.color.card_back));
                    cdHotel.setRadius(50);

                    lyContenedor.setLayoutParams(lyContenedorSitio.getLayoutParams());
                    lyContenedor.setOrientation(LinearLayout.HORIZONTAL);

                    cdPortada.setLayoutParams(cdPortadaHotel.getLayoutParams());
                    cdPortada.setRadius(75);

                    iPortada.setLayoutParams(imgPortadaHotel.getLayoutParams());
                    byte[] bytes = Base64.getDecoder().decode(sitioHospedaje.getPortada());
                    Bitmap bit = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    iPortada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    iPortada.setImageBitmap(bit);

                    lyInfo.setLayoutParams(lyContenedorInfo.getLayoutParams());
                    lyInfo.setOrientation(LinearLayout.VERTICAL);


                    txvNombre.setLayoutParams(txvNombreHotelCard.getLayoutParams());
                    Typeface tnom = ResourcesCompat.getFont(this, R.font.open_sans_bold);
                    txvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                    txvNombre.setTextColor(Color.BLACK);
                    txvNombre.setTypeface(tnom);
                    txvNombre.setText(sitioHospedaje.getNombre());

                    txvID.setLayoutParams(txvIdHospedaje.getLayoutParams());
                    txvID.setText(sitioHospedaje.getIdHospedaje());
                    txvID.setVisibility(View.GONE);

                    lyPuntuacion.setLayoutParams(lyPuntuacionVista.getLayoutParams());
                    lyPuntuacion.setOrientation(LinearLayout.HORIZONTAL);

                    cdPun.setLayoutParams(cdPuntuacion.getLayoutParams());
                    cdPun.setCardBackgroundColor(getResources().getColor(R.color.card_back_2));
                    cdPun.setRadius(15);

                    txvPuntuacion.setLayoutParams(txvPuntuacionSitioVista.getLayoutParams());
                    double prom = opiniones.promCalif(txvID.getText().toString());
                    if(prom>0){
                        String band = prom + "/5";
                        txvPuntuacion.setText(band);
                    }else{
                        String band = prom + "/0";
                        txvPuntuacion.setText(band);
                    }
                    Typeface tpun = ResourcesCompat.getFont(this, R.font.open_sans_condensed_bold);
                    txvPuntuacion.setTypeface(tpun);
                    txvPuntuacion.setTextColor(Color.WHITE);


                    txvRecomendacion.setLayoutParams(txvRecomendacionVista.getLayoutParams());
                    Typeface trec = ResourcesCompat.getFont(this, R.font.open_sans_light);
                    txvRecomendacion.setTypeface(trec);
                    txvRecomendacion.setTextColor(Color.GRAY);
                    txvRecomendacion.setGravity(Gravity.CENTER);
                    txvRecomendacion.setText(R.string.TextoCalificacion);
                    txvRecomendacion.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    lyDistancia.setLayoutParams(lyDistanciaVista.getLayoutParams());
                    lyDistancia.setOrientation(LinearLayout.HORIZONTAL);

                    imgU.setLayoutParams(imgUbi.getLayoutParams());
                    imgU.setImageDrawable(getDrawable(R.drawable.ic_baseline_location_on_24));
                    imgU.setScaleType(ImageView.ScaleType.FIT_XY);

                    txvDis.setLayoutParams(txvDist.getLayoutParams());
                    Typeface tdis = ResourcesCompat.getFont(this, R.font.open_sans_semibold);
                    txvDis.setTypeface(tdis);
                    txvDis.setGravity(Gravity.BOTTOM);
                    txvDis.setText(R.string.Distancia);
                    txvDis.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                    txvDistancia.setLayoutParams(txvDistanciaVista.getLayoutParams());
                    Typeface tdist = ResourcesCompat.getFont(this, R.font.open_sans_bold);
                    txvDistancia.setTypeface(tdist);
                    txvDistancia.setGravity(Gravity.BOTTOM);
                    txvDistancia.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                    //COMIENZA DISTANCIA
                    /*PERMISO PARA ACCEDER AL GPS*/

                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {

                            distanciaKM = Math.round(distancia.distanciaCoordenadas(
                                    Double.parseDouble(sitioHospedaje.getLatitud()),
                                    Double.parseDouble(sitioHospedaje.getLongitud()),
                                    location.getLatitude(),
                                    location.getLongitude()) * 100.0) / 100.0 + " KM";
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
                    //TERMINA DISTANCIA

                    int[] attrs = new int[] { android.R.attr.selectableItemBackgroundBorderless /* index 0 */};
                    TypedArray ta = obtainStyledAttributes(attrs);
                    Drawable drawableFromTheme = ta.getDrawable(0 /* index */);
                    btnInformacion.setLayoutParams(btnInformacionSitioVista.getLayoutParams());
                    btnInformacion.setBackground(drawableFromTheme);
                    btnInformacion.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    btnInformacion.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_info_24));

                    btnInformacion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent MSH = new Intent(VistaInicio.this, VistaSitioHospedaje.class);
                            MSH.putExtra("Usuario", true);
                            MSH.putExtra("ID",txvID.getText().toString());
                            startActivity(MSH);
                        }
                    });

                    cdHotel.addView(lyContenedor);
                    lyContenedor.addView(cdPortada);
                    lyContenedor.addView(lyInfo);
                    lyContenedor.addView(btnInformacion);
                    cdPortada.addView(iPortada);
                    lyInfo.addView(txvNombre);
                    lyInfo.addView(txvID);
                    lyInfo.addView(lyPuntuacion);
                    lyInfo.addView(lyDistancia);
                    lyPuntuacion.addView(cdPun);
                    lyPuntuacion.addView(txvRecomendacion);
                    cdPun.addView(txvPuntuacion);
                    lyDistancia.addView(imgU);
                    lyDistancia.addView(txvDis);
                    lyDistancia.addView(txvDistancia);
                    lySitiosVista.addView(cdHotel);
                }catch(Exception e){
                    System.out.println("Error-------------" + e);
                }

            }
        }else{
            Toast.makeText(VistaInicio.this, R.string.SinSitios, Toast.LENGTH_LONG).show();
        }

    }

    public void irMenuAdmin(View view){
        Intent MenuAdmin = new Intent(this, VistaMenuAdmin.class);
        startActivity(MenuAdmin);
    }

    public void irMenuSitio(View View){
        Intent MenuSitio = new Intent(this, VistaMenuSitio.class);
        startActivity(MenuSitio);
    }

    public void irMiSitio(View View){
        Intent MiSitio = new Intent(this, VistaSitioHospedaje.class);
        startActivity(MiSitio);
    }

}
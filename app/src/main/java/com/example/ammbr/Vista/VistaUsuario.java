package com.example.ammbr.Vista;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammbr.Modelo.CRUD.CRUDSitioHospedaje;
import com.example.ammbr.Modelo.CRUD.CRUDUsuarios;
import com.example.ammbr.Modelo.Negocio.Seguridad.ControlIngresoDatos;
import com.example.ammbr.Modelo.SitioHospedaje;
import com.example.ammbr.Modelo.Usuarios;
import com.example.ammbr.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.util.Base64;
import java.util.Calendar;

public class VistaUsuario extends AppCompatActivity {
    CardView cardEditarFotoPerfil;
    LinearLayout lyModificarNombreUsuario, lyAccionesNombreUsuario, lyAccionesApellidoUsuario, lyTituloContraseña, lyContraseña, lyNuevaContraseña,
            lyModificarContraseña, lyModificarCategoriaUsuario, lyAccionesEmailUsuario, lyEditarTelefonoHospedaje, lyModificarTelefonoUsuario,
            lyAccionesTelefonoUsuario,lyModificarFechaNacimiento, lyAccionesFechaNacimiento, lyHospedajeAdmin, lyModificarAccionesHospedajeAdmin,
            lyAccionesSitioAdmin, lyBotones, lyInfoEmail, lyInfoTelefonoUsuario, lyInfoFechaNac, lyNombreUsuario, lyAccionesModificarNombreUsuario,
            lyAccionesModificarApellidoUsuario, lyCategoriaUsuario, lyEditarEmailUsuario, lyModificarNombre, lyModificarApellido, lyNuevaCategoriaUsuario;
    ImageView btnModificarNombreUsuario, btnModificarEmail, btnModificarTelefono, btnModificarFechaNacimiento, btnModificarHospedajeAdmin,
            btnEditarContraseñaUsuario, imgFotoPerfil;
    EditText txtEmailUsuario, txtNombreUsuario, txtApellidoUsuario, txtContraseñaUsuario, txtConfirmarContraseñaUsuario, txtFechaNacimiento;
    TextView txvInformacionUsuario, txvEmail, txvNombreUsuario, txvFechaNacimiento, txtSitioHospedaje, txvCategoriaUsuario;
    Button btnEditarUsuario, btnRegistrarUsuario, btnGuardarEdicion;
    ImageButton btnEditarCategoriaUsuario;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String Usuario;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);

        Bundle main = getIntent().getExtras();
        Usuario = main.getString("RegistrarUsuario");
        SetAtributos();
        MostrarUsuario();
        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        VistaUsuario.this,
                        AlertDialog.THEME_HOLO_LIGHT,
                        onDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String fecha;
                if(month<10){
                    fecha = year + "-0" + month + "-" + day;
                    txtFechaNacimiento.setText(fecha);
                }
                if(day<10){
                    fecha = year + "-" + month + "-0" + day;
                    txtFechaNacimiento.setText(fecha);
                }
                if(month<10 && day<10){
                    fecha = year + "-0" + month + "-0" + day;
                    txtFechaNacimiento.setText(fecha);
                }
            }
        };
    }

    public void cerrarSesion(View View){
        SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.clear();
        editor.putString("Email","vacio");
        editor.putString("Contraseña","vacio");
        editor.commit();
        Intent GoLogin = new Intent(this, VistaLogin.class);
        startActivity(GoLogin);
    }

    public void irModificar(View View){
        Usuarios usuario;
        CRUDUsuarios usuarios = new CRUDUsuarios();
        SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
        usuario = usuarios.usuario(share.getString("Email","no hay"));
        lyCategoriaUsuario.setVisibility(View.GONE);
        lyNombreUsuario.setVisibility(View.GONE);
        lyInfoFechaNac.setVisibility(View.GONE);
        txtSitioHospedaje.setVisibility(View.GONE);
        txtNombreUsuario.setVisibility(View.VISIBLE);
        txtApellidoUsuario.setVisibility(View.VISIBLE);
        txtFechaNacimiento.setVisibility(View.VISIBLE);
        lyModificarNombre.setVisibility(View.VISIBLE);
        lyAccionesModificarNombreUsuario.setVisibility(View.GONE);
        lyModificarApellido.setVisibility(View.VISIBLE);
        cardEditarFotoPerfil.setVisibility(View.VISIBLE);
        lyAccionesModificarApellidoUsuario.setVisibility(View.GONE);
        lyModificarFechaNacimiento.setVisibility(View.VISIBLE);
        lyAccionesFechaNacimiento.setVisibility(View.GONE);
        txtNombreUsuario.setText(usuario.getNombre());
        txtApellidoUsuario.setText(usuario.getApellido());
        txvEmail.setText(usuario.getEmail());
        txtFechaNacimiento.setText(usuario.getFecha_nac());
        btnEditarUsuario.setVisibility(View.GONE);
        btnGuardarEdicion.setVisibility(View.VISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RegistrarUsuarioHuesped(View view){
        CRUDUsuarios nuevousuario = new CRUDUsuarios();
        ControlIngresoDatos control = new ControlIngresoDatos();
        int tipo = 0;
        switch (Usuario){
            case "Huesped":{
                tipo = 3;
                break;
            }
            case "Admin":{
                tipo = 2;
                break;
            }
            case  "Root":{
                tipo = 1;
                break;
            }

        }
        if(control.email(txtEmailUsuario.getText().toString())==false){
            Toast.makeText(VistaUsuario.this, R.string.ControlEmail, Toast.LENGTH_LONG).show();
            txtEmailUsuario.requestFocus();
        }else if(txtNombreUsuario.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlNombre, Toast.LENGTH_LONG).show();
            txtNombreUsuario.requestFocus();
        }else if(txtApellidoUsuario.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlApellido, Toast.LENGTH_LONG).show();
            txtApellidoUsuario.requestFocus();
        }else if(txtContraseñaUsuario.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlContraseña, Toast.LENGTH_LONG).show();
            txtContraseñaUsuario.requestFocus();
        }else if(txtConfirmarContraseñaUsuario.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlContraseña2, Toast.LENGTH_LONG).show();
            txtConfirmarContraseñaUsuario.requestFocus();
        }
        else if(txtContraseñaUsuario.getText().toString().equals(txtConfirmarContraseñaUsuario.getText().toString())==false){
            Toast.makeText(VistaUsuario.this, R.string.ControlConfContraseña, Toast.LENGTH_LONG).show();
            txtConfirmarContraseñaUsuario.requestFocus();
        }else if(txtFechaNacimiento.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlFecha, Toast.LENGTH_LONG).show();
            txtFechaNacimiento.requestFocus();
        }else if(control.MayorEdad(txtFechaNacimiento.getText().toString())==false){
            Toast.makeText(VistaUsuario.this, R.string.ControlEdad, Toast.LENGTH_LONG).show();
            txtFechaNacimiento.requestFocus();
        }else if(control.existeUsuario(txtEmailUsuario.getText().toString())==true){
            Toast.makeText(VistaUsuario.this, R.string.UsuarioExiste, Toast.LENGTH_LONG).show();
            txtEmailUsuario.requestFocus();
        }
        else{
            try{
                BitmapDrawable bt = (BitmapDrawable) imgFotoPerfil.getDrawable();
                Bitmap bit = bt.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String image = Base64.getEncoder().encodeToString(bytes);

                nuevousuario.nuevoUsuario(
                        txtNombreUsuario.getText().toString(),
                        txtApellidoUsuario.getText().toString(),
                        txtEmailUsuario.getText().toString(),
                        image,
                        txtContraseñaUsuario.getText().toString(),
                        tipo,
                        txtFechaNacimiento.getText().toString()
                );


                if(tipo==3){
                    Intent GoPrincipal = new Intent(this, VistaInicio.class);
                    SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.clear();
                    editor.putString("Email", txtEmailUsuario.getText().toString());
                    editor.putString("Contraseña", txtContraseñaUsuario.getText().toString());
                    editor.commit();
                    GoPrincipal.putExtra("Usuario", "Huesped");
                    startActivity(GoPrincipal);
                }else if(tipo==2){
                    Intent GoMenuAdmin = new Intent(this, VistaInicio.class);
                    startActivity(GoMenuAdmin);
                }else{
                    Intent GoMenuAdmin = new Intent(this, VistaInicio.class);
                    startActivity(GoMenuAdmin);
                }

            }catch (Exception e){
                System.out.println(e);
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ModificarUsuarioHuesped(View View){
        CRUDUsuarios nuevousuario = new CRUDUsuarios();
        ControlIngresoDatos control = new ControlIngresoDatos();
        if(txtNombreUsuario.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlNombre, Toast.LENGTH_LONG).show();
            txtNombreUsuario.requestFocus();
        }else if(txtApellidoUsuario.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlApellido, Toast.LENGTH_LONG).show();
            txtApellidoUsuario.requestFocus();
        }else if(txtFechaNacimiento.getText().toString().isEmpty()){
            Toast.makeText(VistaUsuario.this, R.string.ControlFecha, Toast.LENGTH_LONG).show();
            txtFechaNacimiento.requestFocus();
        }else if(control.MayorEdad(txtFechaNacimiento.getText().toString())==false){
            Toast.makeText(VistaUsuario.this, R.string.ControlEdad, Toast.LENGTH_LONG).show();
            txtFechaNacimiento.requestFocus();
        }else{
            try{
                BitmapDrawable bt = (BitmapDrawable) imgFotoPerfil.getDrawable();
                Bitmap bit = bt.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String image = Base64.getEncoder().encodeToString(bytes);

                nuevousuario.editarUsuario(
                        txvEmail.getText().toString(),
                        txtNombreUsuario.getText().toString(),
                        txtApellidoUsuario.getText().toString(),
                        image,
                        txtFechaNacimiento.getText().toString()
                );
                Intent GoPrincipal = new Intent(this, VistaInicio.class);
                    GoPrincipal.putExtra("Huesped", true);
                    startActivity(GoPrincipal);

            }catch (Exception e){
                System.out.println(e);
            }

        }
    }

    public void SetAtributos(){
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtApellidoUsuario = findViewById(R.id.txtApellidoUsuario);
        txtEmailUsuario = findViewById(R.id.txtEmailUsuario);
        txtContraseñaUsuario = findViewById(R.id.txtContraseñaUsuario);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtConfirmarContraseñaUsuario = findViewById(R.id.txtConfirmarContraseñaUsuario);


        cardEditarFotoPerfil = findViewById(R.id.cardEditarFotoPerfil);
        btnModificarNombreUsuario = findViewById(R.id.btnModificarNombreUsuario);
        lyTituloContraseña = findViewById(R.id.lyTituloContraseña);
        lyContraseña = findViewById(R.id.lyContraseña);
        lyNuevaContraseña = findViewById(R.id.lyNuevaContraseña);
        lyModificarContraseña = findViewById(R.id.lyModificarContraseña);
        lyModificarCategoriaUsuario = findViewById(R.id.lyModificarCategoriaUsuario);
        lyNombreUsuario = findViewById(R.id.lyNombreUsuario);
        btnModificarEmail = findViewById(R.id.btnModificarEmail);
        lyAccionesEmailUsuario = findViewById(R.id.lyAccionesEmailUsuario);
        btnModificarFechaNacimiento = findViewById(R.id.btnModificarFechaNacimiento);
        lyModificarFechaNacimiento = findViewById(R.id.lyModificarFechaNacimiento);
        lyAccionesFechaNacimiento = findViewById(R.id.lyAccionesFechaNacimiento);
        lyHospedajeAdmin = findViewById(R.id.lyHospedajeAdmin);
        lyEditarTelefonoHospedaje = findViewById(R.id.lyEditarEmailUsuario);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        txvCategoriaUsuario = findViewById(R.id.txvCategoriaUsuario);
        lyBotones = findViewById(R.id.lyBotones);
        lyBotones = findViewById(R.id.lyBotones);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);
        txvInformacionUsuario = findViewById(R.id.txvInformacionUsuario);
        lyInfoEmail = findViewById(R.id.lyInfoEmail);
        lyInfoFechaNac = findViewById(R.id.lyInfoFechaNac);
        btnEditarContraseñaUsuario = findViewById(R.id.btnEditarContraseñaUsuario);
        lyAccionesModificarNombreUsuario = findViewById(R.id.lyAccionesModificarNombreUsuario);
        lyAccionesModificarApellidoUsuario = findViewById(R.id.lyAccionesModificarApellidoUsuario);
        lyCategoriaUsuario = findViewById(R.id.lyCategoriaUsuario);
        lyEditarEmailUsuario = findViewById(R.id.lyEditarEmailUsuario);
        lyModificarNombre = findViewById(R.id.lyModificarNombre);
        lyModificarApellido = findViewById(R.id.lyModificarApellido);
        lyNuevaCategoriaUsuario = findViewById(R.id.lyNuevaCategoriaUsuario);
        txvEmail = findViewById(R.id.txvEmail);
        txvNombreUsuario = findViewById(R.id.txvNombreUsuario);
        txvFechaNacimiento = findViewById(R.id.txvFechaNacimiento);
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtSitioHospedaje = findViewById(R.id.txtSitioHospedaje);
        btnGuardarEdicion = findViewById(R.id.btnGuardarEdicion);
        btnEditarCategoriaUsuario = findViewById(R.id.btnEditarCategoriaUsuario);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void MostrarUsuario(){
        Bundle main = getIntent().getExtras();
        Boolean ListarUsuario = main.getBoolean("ListarUsuario");
        String Usuario = "";
        Usuario = main.getString("Usuario");
        String RegistrarUsuario = "";
        RegistrarUsuario = main.getString("RegistrarUsuario");
        Boolean ModificarUsuario = main.getBoolean("ModificarUsuario");
        ModificarUsuario = false;

        Usuarios usuario;
        CRUDUsuarios usuarios = new CRUDUsuarios();
        SharedPreferences share = getSharedPreferences("DatosUsuario", MODE_PRIVATE);
        usuario = usuarios.usuario(share.getString("Email","no hay"));

        if(ListarUsuario){
            cardEditarFotoPerfil.setVisibility(View.GONE);
            lyEditarEmailUsuario.setVisibility(View.GONE);
            btnModificarNombreUsuario.setVisibility(View.GONE);
            lyContraseña.setVisibility(View.GONE);
            lyModificarCategoriaUsuario.setVisibility(View.GONE);
            btnModificarEmail.setVisibility(View.GONE);
            lyModificarNombre.setVisibility(View.GONE);
            lyModificarApellido.setVisibility(View.GONE);
            lyNuevaCategoriaUsuario.setVisibility(View.GONE);
            btnModificarFechaNacimiento.setVisibility(View.GONE);
            lyModificarFechaNacimiento.setVisibility(View.GONE);
            lyHospedajeAdmin.setVisibility(View.GONE);
            btnRegistrarUsuario.setVisibility(View.GONE);
            txvInformacionUsuario.setVisibility(View.GONE);
            btnGuardarEdicion.setVisibility(View.GONE);
            txvInformacionUsuario.setVisibility(View.VISIBLE);

            try{
                byte[] bytes = Base64.getDecoder().decode(usuario.getFoto());
                Bitmap bit = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgFotoPerfil.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imgFotoPerfil.setImageBitmap(bit);
                txvFechaNacimiento.setText(usuario.getFecha_nac());
                txvEmail.setText(usuario.getEmail());
                txvNombreUsuario.setText(usuario.getNombre() + " " + usuario.getApellido());
                if(Usuario.equals("Huesped") || Usuario.equals("Root")){
                    lyCategoriaUsuario.setVisibility(View.GONE);
                    lyHospedajeAdmin.setVisibility(View.GONE);
                }else if(Usuario.equals("Admin")){
                    lyCategoriaUsuario.setVisibility(View.VISIBLE);
                    lyHospedajeAdmin.setVisibility(View.VISIBLE);
                    lyModificarCategoriaUsuario.setVisibility(View.VISIBLE);
                    btnEditarCategoriaUsuario.setVisibility(View.GONE);
                    txvCategoriaUsuario.setText(R.string.Administrador);
                    CRUDSitioHospedaje sitioHospedaje = new CRUDSitioHospedaje();
                    SitioHospedaje sitio;
                    sitio = sitioHospedaje.listarSitioxAdmin(usuario.getEmail());
                    txtSitioHospedaje.setText(sitio.getNombre());
                }
            }catch(Exception e){
                System.out.println(e);
            }


        }else if(RegistrarUsuario.equals("Huesped") || RegistrarUsuario.equals("Admin")){
            lyModificarContraseña.setVisibility(View.GONE);
            lyModificarCategoriaUsuario.setVisibility(View.GONE);
            lyInfoEmail.setVisibility(View.GONE);
            lyAccionesEmailUsuario.setVisibility(View.GONE);
            lyInfoFechaNac.setVisibility(View.GONE);
            lyAccionesFechaNacimiento.setVisibility(View.GONE);
            lyHospedajeAdmin.setVisibility(View.GONE);
            lyNombreUsuario.setVisibility(View.GONE);
            lyAccionesModificarNombreUsuario.setVisibility(View.GONE);
            btnEditarContraseñaUsuario.setVisibility(View.GONE);
            lyAccionesModificarApellidoUsuario.setVisibility(View.GONE);
            lyCategoriaUsuario.setVisibility(View.GONE);
            btnModificarEmail.setVisibility(View.GONE);
            btnModificarFechaNacimiento.setVisibility(View.GONE);
            btnGuardarEdicion.setVisibility(View.GONE);
            btnEditarUsuario.setVisibility(View.GONE);


        }

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
            imgFotoPerfil.setImageURI(path);
            imgFotoPerfil.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }
}
package com.example.appi_002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SegundoActivity extends AppCompatActivity {
    private EditText editTextNumero;
    private EditText editText;
    private ImageButton btnnumero;
    private  ImageButton  botonexplorador;

    private final int PHONE_CALL_CODE=100;
    private final int CAMERA_CALL_CODE=120;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);
        editTextNumero=(EditText) findViewById(R.id.editTextPhone);
        editText=(EditText) findViewById(R.id.editTextTelefono);
        btnnumero=  (ImageButton)  findViewById(R.id.btnLlamar);
        botonexplorador=(ImageButton) findViewById(R.id.btnBuscar);

        btnnumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num= editTextNumero.getText().toString();
                if(num!=null)
                {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);
                    }
                    else {
                        versionesAnteriores(num);
                    }
                }
            }
            private  void  versionesAnteriores(String num){
                Intent intentllamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num));
                if(verificarpermisos(Manifest.permission.CALL_PHONE))
                {
                    startActivity(intentllamada);
                }else {
                    Toast.makeText(SegundoActivity.this,"Configure permisos", Toast.LENGTH_SHORT).show();
                }
            }
            private  void  versionNueva()
            {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];
                if(permission.equals(Manifest.permission.CALL_PHONE)){


                    if(result==PackageManager.PERMISSION_GRANTED)
                    {
                        String phoneNumber=editTextNumero.getText().toString();
                        Intent llamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED);
                        startActivity(llamada);
                    }
                    else {
                        Toast.makeText(this, "No aceptaste el permiso", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    private  boolean verificarpermisos (String permiso)
    {
        int resultado = this.checkCallingOrSelfPermission(permiso);
        return  resultado== PackageManager.PERMISSION_GRANTED;
    }
}
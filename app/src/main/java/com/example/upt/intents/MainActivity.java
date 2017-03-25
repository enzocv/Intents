package com.example.upt.intents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static int CAMERA_PIC_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Funcionalidad del botón btn_call
         */
        Button btn_llamar = (Button) findViewById(R.id.btn_call);

        btn_llamar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri number = Uri.parse("tel:954682847");
                Intent callIntent = new Intent(Intent.ACTION_DIAL,number);
                startActivity(callIntent);
            }
        });

        /**
         * Funcionalidad del botón btn_location
         */
//        Button btn_location = (Button) findViewById(R.id.btn_location);
//
//        btn_location.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Uri location = Uri.parse("geo:0,0?q=Unirsidad+Privada+de+Tacna+Granada,+Tacna");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW,location);
//                startActivity(mapIntent);
//            }
//        });

        // MODIFICADO
        Button btn_location = (Button) findViewById(R.id.btn_location);

        btn_location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri location = Uri.parse("geo:0,0?q=Unirsidad+Privada+de+Tacna+Granada,+Tacna");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW,location);

                if (mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent);
                }else{
                    Toast.makeText(MainActivity.this,"No existe Actividad para esta acción",Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * Funcionalidad para el botón btn_web
         */

        Button btn_web =(Button)findViewById(R.id.btn_web);

        btn_web.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Uri webpage = Uri.parse("http://www.upt.edu.pe");
                Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);

                //Verifica que se pueda llamar
                //a alguna actividad
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> actividades = packageManager.queryIntentActivities(webIntent,PackageManager.MATCH_DEFAULT_ONLY);

                boolean isIntentSafe = actividades.size()>0;

                String titulo = getResources().getString(R.string.chooser_title);
                Intent wChooser= Intent.createChooser(webIntent,titulo);
                if (isIntentSafe){
                    startActivity(wChooser);
                }
            }
        });

        /**
         * Funcionalidad para el botón btn_camara
         */

        Button btn_camera = (Button)findViewById(R.id.btn_camara);

        btn_camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      if (requestCode == CAMERA_PIC_REQUEST){
          if (resultCode == RESULT_OK){
              Bitmap imagen = (Bitmap)data.getExtras().get("data");
              ImageView foto = (ImageView)findViewById(R.id.img_prev);
              foto.setImageBitmap(imagen);
          }
      }
    }
}

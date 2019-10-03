package com.example.pickphoto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imagen= (ImageView) findViewById(R.id.imagemId);
    }

    public void onclick(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"seleccione la aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path=data.getData();
            try {
                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                // create output bitmap
                Bitmap bmOut = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
                // pixel information
                int A, R, G, B;
                int pixel;

                // get image size
                int width = bitmap1.getWidth();
                int height = bitmap1.getHeight();

                // scan through every single pixel
                for(int x = 0; x < width; ++x) {
                    for(int y = 0; y < height; ++y) {
                        // get one pixel color
                        pixel = bitmap1.getPixel(x, y);
                        // retrieve color of all channels
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        // take conversion up to one single value
                        R = G = B = (int)((R+G+B)/3);
                        // set new pixel color to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));

                    }
                }

                imagen.setImageBitmap(bmOut);


            } catch (IOException e) {
                e.printStackTrace();
            }

            //imagen.setImageURI(path);
        }
    }
}

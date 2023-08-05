package com.example.segundoparcial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ReproductorActivity extends AppCompatActivity {

    private VideoView videoView;
    private String selectedCategory;
    private int currentPosition = 0;
    private boolean isVideoPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        String gender = intent.getStringExtra("gender");
        selectedCategory = intent.getStringExtra("category");


        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewGender = findViewById(R.id.textViewGender);
        ImageView imageViewProfilePhoto = findViewById(R.id.imageViewProfilePhoto);

        textViewName.setText("Nombre: " + name);
        textViewAge.setText("Edad: " + age);
        textViewGender.setText("Género: " + gender);


        if (datos.profilePhotoBitmap != null) {
            imageViewProfilePhoto.setImageBitmap(datos.profilePhotoBitmap);
        }


        videoView = findViewById(R.id.videoView);
        playVideo(selectedCategory);


        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVideoPaused) {
                    videoView.seekTo(currentPosition);
                    videoView.start();
                    isVideoPaused = false;
                } else {
                    // Reproducir el video desde el inicio
                    playVideo(selectedCategory);
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pausar la reproducción del video
                if (videoView.isPlaying()) {
                    videoView.pause();
                    currentPosition = videoView.getCurrentPosition();
                    isVideoPaused = true;
                }
            }
        });

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Regresar a la pantalla de Menú
                videoView.stopPlayback();
                finish(); // Cierra la pantalla de reproductor y regresa al menú
            }
        });
    }

    private void playVideo(String category) {

        if ("Caricaturas".equals(category)) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ELEMENTOS);
        } else if ("Adolescentes".equals(category)) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.lanochedeldemonio);
        } else if ("Terror".equals(category)) {
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.RápidosyFuriososx);
        }

        // Iniciar la reproducción del video
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar el video y guardar la posición actual en onPause()
        if (videoView.isPlaying()) {
            videoView.pause();
            currentPosition = videoView.getCurrentPosition();
            isVideoPaused = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Continuar reproduciendo el video desde la posición pausada en onResume()
        if (isVideoPaused) {
            videoView.seekTo(currentPosition);
            videoView.start();
            isVideoPaused = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar los recursos del VideoView en onDestroy()
        videoView.stopPlayback();
    }
}
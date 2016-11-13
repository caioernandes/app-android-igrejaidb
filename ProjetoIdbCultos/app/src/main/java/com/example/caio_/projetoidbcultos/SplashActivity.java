package com.example.caio_.projetoidbcultos;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.caio_.projetoidbcultos.activities.CultoActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int TEMPO_SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(SplashActivity.this, CultoActivity.class);
                startActivity(it);
                finish();
            }
        }, TEMPO_SPLASH);
    }
}

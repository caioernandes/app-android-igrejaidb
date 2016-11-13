package com.example.caio_.projetoidbcultos.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.caio_.projetoidbcultos.fragments.DetalheCultoFragment;
import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.model.Culto;

import org.parceler.Parcels;

public class DetalheCultoActivity extends AppCompatActivity {

    public static final String EXTRA_CULTO = "culto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_culto_activity);

        Culto culto = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_CULTO));
        DetalheCultoFragment detalheCultoFragment = DetalheCultoFragment.newInstance(culto);
        getSupportFragmentManager().beginTransaction().replace(R.id.detalhe, detalheCultoFragment, "detalhe").commit();
    }
}

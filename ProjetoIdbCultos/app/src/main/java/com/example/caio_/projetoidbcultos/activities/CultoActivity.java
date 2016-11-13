package com.example.caio_.projetoidbcultos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.fragments.DetalheCultoFragment;
import com.example.caio_.projetoidbcultos.fragments.ListaCultoFragment;
import com.example.caio_.projetoidbcultos.fragments.ListaFavoritoFragment;
import com.example.caio_.projetoidbcultos.interfaces.CliqueNoCultoListiner;
import com.example.caio_.projetoidbcultos.model.Culto;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CultoActivity extends AppCompatActivity implements CliqueNoCultoListiner {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culto);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);

        mViewPager.setAdapter(new CultoPager(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.endereco) {
            Intent it = new Intent(CultoActivity.this, MapsActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    class CultoPager extends FragmentPagerAdapter {

        public CultoPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new ListaCultoFragment();
            } else {
                return new ListaFavoritoFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getString(R.string.aba_web);
            } else {
                return getString(R.string.aba_favorito);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void CultoFoiClicado(Culto culto) {
        if(getResources().getBoolean(R.bool.tablet)){
            DetalheCultoFragment detalheCultoFragment = DetalheCultoFragment.newInstance(culto);
            getSupportFragmentManager().beginTransaction().replace(R.id.detalhe, detalheCultoFragment, "detalhe").commit();
        } else {
            Intent it = new Intent(this, DetalheCultoActivity.class);
            Parcelable p = Parcels.wrap(culto);
            it.putExtra(DetalheCultoActivity.EXTRA_CULTO, p);
            startActivity(it);
        }
    }
}

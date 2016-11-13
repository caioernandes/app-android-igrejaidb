package com.example.caio_.projetoidbcultos.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.database.CultoDAL;
import com.example.caio_.projetoidbcultos.infraestrutura.CultoApp;
import com.example.caio_.projetoidbcultos.model.Culto;
import com.example.caio_.projetoidbcultos.youtube.YoutubeActivity;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetalheCultoFragment extends Fragment {

    private static final String EXTRA_CULTO = "param1";

    @BindView(R.id.text_pregador)
    TextView txtPregador;
    @BindView(R.id.text_tema)
    TextView txtTema;
    @BindView(R.id.textNomeOriginalYoutube)
    TextView txtNomeOriginalYoutube;
    @BindView(R.id.textData)
    TextView txtData;
    @BindView(R.id.image_culto)
    ImageView imageCulto;
    @BindView(R.id.fab_favorito)
    FloatingActionButton mFabFavorito;
    @BindView(R.id.fab_assistir)
    FloatingActionButton mFabAssistir;

    CultoDAL mDAL;

    private Culto mCulto;
    private Unbinder unbinder;
    private ShareActionProvider mShareActionProvider;

    public static DetalheCultoFragment newInstance(Culto culto) {
        DetalheCultoFragment fragment = new DetalheCultoFragment();
        Bundle args = new Bundle();
        Parcelable p = Parcels.wrap(culto);
        args.putParcelable(EXTRA_CULTO, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDAL = new CultoDAL(getActivity());

        if (getArguments() != null) {
            Parcelable p = getArguments().getParcelable(EXTRA_CULTO);
            mCulto = Parcels.unwrap(p);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_culto, container, false);
        unbinder = ButterKnife.bind(this, view);
        ButterKnife.bind(this, view);

        mFabAssistir.setImageResource(R.drawable.ic_play);
        mFabAssistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), YoutubeActivity.class);
                it.putExtra(YoutubeActivity.YOUTUBE_PARAM, mCulto.getLinkYoutube());
                it.putExtra(YoutubeActivity.NOME_YOUTUBE_PARAM, mCulto.getNomeOriginalYoutube());
                startActivity(it);
            }
        });

        txtNomeOriginalYoutube.setText(mCulto.getNomeOriginalYoutube());
        txtPregador.setText(mCulto.getPregador());
        txtTema.setText(mCulto.getTema());
        txtData.setText(mCulto.getData());
        Glide.with(getActivity()).load(mCulto.getFoto()).into(imageCulto);
        toggleFavorito();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_detalhe, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent it = new Intent(Intent.ACTION_SEND);
        String corpoMensagem = mCulto.getNomeOriginalYoutube() + "\n\n" + "https://www.youtube.com/watch?v=" + mCulto.getLinkYoutube();
        it.putExtra(Intent.EXTRA_TEXT, corpoMensagem);
        it.setType("text/plain");
        mShareActionProvider.setShareIntent(it);
    }

    private void toggleFavorito() {
        boolean favorito = mDAL.isFavorito(mCulto);

        mFabFavorito.setImageResource(R.drawable.estrela);
        mFabFavorito.setBackgroundTintList(
                favorito ? ColorStateList.valueOf(getResources().getColor(R.color.yellow))
                        : ColorStateList.valueOf(getResources().getColor(R.color.silver))
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_favorito)
    public void favoritoClick() {
        if(mDAL.isFavorito(mCulto)) {
            mDAL.excluir(mCulto);
        } else {
            mDAL.inserir(mCulto);
        }
        mFabFavorito.animate().scaleX(0).scaleY(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toggleFavorito();
                mFabFavorito.animate().scaleX(1).scaleY(1).setListener(null);
            }
        });

        ((CultoApp)getActivity().getApplication()).getEventBus().post(mCulto);
    }
}

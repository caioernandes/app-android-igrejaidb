package com.example.caio_.projetoidbcultos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.database.CultoDAL;
import com.example.caio_.projetoidbcultos.infraestrutura.CultoAdapter;
import com.example.caio_.projetoidbcultos.infraestrutura.CultoApp;
import com.example.caio_.projetoidbcultos.interfaces.CliqueNoCultoListiner;
import com.example.caio_.projetoidbcultos.model.Culto;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ListaFavoritoFragment extends Fragment {

    @BindView(R.id.list_culto)
    ListView mListView;

    @BindView(R.id.no_favoritos)
    View mEmpty;

    List<Culto> mCultos;
    ArrayAdapter<Culto> mAdapter;
    CultoDAL mCultoDAL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mCultoDAL = new CultoDAL(getActivity());
        mCultos = mCultoDAL.listar();

        ((CultoApp)getActivity().getApplication()).getEventBus().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((CultoApp)getActivity().getApplication()).getEventBus().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lista_favorito, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new CultoAdapter(getContext(), mCultos);
        mListView.setEmptyView(mEmpty);
        mListView.setAdapter(mAdapter);

        return layout;
    }

    @OnItemClick(R.id.list_culto)
    void onItemSelected(int position) {
        Culto culto = mCultos.get(position);
        if (getActivity() instanceof CliqueNoCultoListiner) {
            CliqueNoCultoListiner listiner = (CliqueNoCultoListiner) getActivity();
            listiner.CultoFoiClicado(culto);
        }
    }

    @Subscribe
    public void atualizar(Culto culto) {
        mCultos.clear();
        mCultos.addAll(mCultoDAL.listar());
        mAdapter.notifyDataSetChanged();
    }
}

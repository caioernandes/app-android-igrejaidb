package com.example.caio_.projetoidbcultos.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.infraestrutura.ConstantesJson;
import com.example.caio_.projetoidbcultos.infraestrutura.CultoAdapter;
import com.example.caio_.projetoidbcultos.interfaces.CliqueNoCultoListiner;
import com.example.caio_.projetoidbcultos.model.Categoria;
import com.example.caio_.projetoidbcultos.model.Culto;
import com.example.caio_.projetoidbcultos.model.Igreja;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListaCultoFragment extends Fragment {

    @BindView(R.id.list_culto)
    ListView mListView;

    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;

    @BindView(R.id.no_connection_list)
    View mEmpty;

    @BindView(R.id.txtNoConnection)
    TextView txtNoConnection;

    List<Culto> mCultos;
    ArrayAdapter<Culto> mAdapter;
    CultosTask mTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mCultos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lista_culto, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new CultoAdapter(getContext(), mCultos);
        mListView.setAdapter(mAdapter);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadJson();
            }
        });

        mSwipe.setColorSchemeResources(R.color.colorPrimary);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCultos.size() == 0 && (mTask == null)){
            downloadJson();
        } else if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            showProgress();
        }
    }

    private void downloadJson() {
        ConnectivityManager cm = ((ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isConnected()) {
            mEmpty.setVisibility(View.INVISIBLE);
            txtNoConnection.setText("");
            mTask = new CultosTask();
            mTask.execute();
        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.erro_conexao_toast),
                    Toast.LENGTH_SHORT).show();

            if (mCultos.size() == 0) {
                mEmpty.setVisibility(View.VISIBLE);
                txtNoConnection.setText(getResources().getString(R.string.erro_conexao_view));
            }

            mSwipe.setRefreshing(false);
        }
    }

    private void showProgress() {
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }

    @OnItemClick(R.id.list_culto)
    void onItemSelected(int position) {
        Culto culto = mCultos.get(position);
        if (getActivity() instanceof CliqueNoCultoListiner) {
            CliqueNoCultoListiner listiner = (CliqueNoCultoListiner) getActivity();
            listiner.CultoFoiClicado(culto);
        }
    }

    public class CultosTask extends AsyncTask<Void, Void, Igreja> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected Igreja doInBackground(Void... params) {

            OkHttpClient client = new OkHttpClient();
            String url = ConstantesJson.CULTOS_DOMINGO;
            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();
                Gson gson = new Gson();
                Igreja igreja = gson.fromJson(jsonString, Igreja.class);
                return igreja;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Igreja igreja) {
            super.onPostExecute(igreja);

            if (igreja != null) {
                mCultos.clear();
                for (Categoria categoria : igreja.getCategorias()) {
                    mCultos.addAll(categoria.getCultos());
                }
                mAdapter.notifyDataSetChanged();
                if (getResources().getBoolean(R.bool.tablet) && mCultos.size() > 0) {
                    onItemSelected(0);
                }
            }
            mSwipe.setRefreshing(false);
        }
    }
}

package com.example.caio_.projetoidbcultos.infraestrutura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caio_.projetoidbcultos.R;
import com.example.caio_.projetoidbcultos.model.Culto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CultoAdapter extends ArrayAdapter<Culto> {

    public CultoAdapter(Context context, List<Culto> cultoList) {
        super(context, 0, cultoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Culto culto = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_culto, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (culto != null) {
            Glide.with(getContext()).load(culto.getFoto()).into(viewHolder.imageView);
            viewHolder.txtTema.setText(culto.getTema());
            viewHolder.txtPregador.setText(culto.getPregador());
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image_culto)
        ImageView imageView;
        @BindView(R.id.text_tema)
        TextView txtTema;
        @BindView(R.id.text_pregador)
        TextView txtPregador;
        @BindView(R.id.img_aovivo)
        ImageView imageAoVivo;

        public ViewHolder(View parent) {
            ButterKnife.bind(this, parent);
            parent.setTag(this);
        }
    }
}

package com.example.seminar04_1075;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImagineVagonAdapter extends BaseAdapter {

    private List<ImagineVagon> lista;
    private Context context;
    private int resursaLayout;

    public ImagineVagonAdapter(List<ImagineVagon> lista,
                               Context context,
                               int resursaLayout) {
        this.lista = lista;
        this.context = context;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(context);

            convertView = inflater.inflate(
                    resursaLayout, parent, false);
        }

        ImageView imagineIV =
                convertView.findViewById(R.id.imagineIV);

        TextView descriereTV =
                convertView.findViewById(R.id.descriereTV);

        ImagineVagon imagineVagon = lista.get(position);

        imagineIV.setImageBitmap(imagineVagon.getImagine());

        descriereTV.setText(imagineVagon.getTextAfisat());

        return convertView;
    }
}

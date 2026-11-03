package com.example.seminar04_1075;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class VagonAdapter extends BaseAdapter {

    private List<Vagon> listaVagoane;
    private Context context;
    private int resursaLayout;

    public VagonAdapter(List<Vagon> listaVagoane,
                        Context context,
                        int resursaLayout) {
        this.listaVagoane = listaVagoane;
        this.context = context;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return listaVagoane.size();
    }

    @Override
    public Object getItem(int position) {
        return listaVagoane.get(position);
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

        TextView tipVagonTv =
                convertView.findViewById(R.id.tipVagon_tv);
        TextView greutateTv =
                convertView.findViewById(R.id.greutate_tv);
        TextView confortTv =
                convertView.findViewById(R.id.confort_tv);
        CheckBox functionalCb =
                convertView.findViewById(R.id.functional_cb);
        TextView dimensiuneTv =
                convertView.findViewById(R.id.dimensiune_tv);

        Vagon vagon = listaVagoane.get(position);

        tipVagonTv.setText(vagon.getTipVagon());
        greutateTv.setText(
                "Greutate: " + vagon.getGreutate() + " tone");
        confortTv.setText(
                "Confort: " + vagon.getGradConfort() + " / 5");
        functionalCb.setChecked(vagon.isEsteFunctional());
        dimensiuneTv.setText(
                "Dimensiune: " + vagon.getDimensiune() + " m");

        return convertView;
    }
}

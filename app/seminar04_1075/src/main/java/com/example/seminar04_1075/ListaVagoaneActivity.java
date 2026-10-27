
package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaVagoaneActivity extends AppCompatActivity {

    private ListView listaVagoaneLv;
    private ArrayList<Vagon> listaVagoane;
    private ArrayAdapter<Vagon> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vagoane);


        listaVagoaneLv = findViewById(R.id.listview);


        Bundle bundle = getIntent().getExtras();

        listaVagoane = new ArrayList<>();

        if (bundle != null) {
            ArrayList<Vagon> listaPrimita =
                    (ArrayList<Vagon>) bundle.getSerializable("lista_vagoane");

            if (listaPrimita != null) {
                listaVagoane = listaPrimita;
            }
        }


        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaVagoane
        );


        listaVagoaneLv.setAdapter(adapter);

        listaVagoaneLv.setOnItemClickListener((parent, view, position, id) -> {

            Vagon vagonSelectat = listaVagoane.get(position);

            Toast.makeText(
                    ListaVagoaneActivity.this,
                    vagonSelectat.toString(),
                    Toast.LENGTH_LONG
            ).show();

        });



        listaVagoaneLv.setOnItemLongClickListener((parent, view, position, id) -> {
            listaVagoane.remove(position);
            adapter.notifyDataSetChanged();

            Intent intentRezultat = new Intent();
            intentRezultat.putExtra("lista_vagoane", listaVagoane);

            setResult(RESULT_OK, intentRezultat);

            Toast.makeText(
                    ListaVagoaneActivity.this,
                    "Vagon sters!",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });


    }
}

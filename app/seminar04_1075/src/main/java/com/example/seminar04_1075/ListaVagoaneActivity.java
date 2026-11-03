package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.ArrayList;

public class ListaVagoaneActivity extends AppCompatActivity {

    private ListView listaVagoaneLv;
    private ArrayList<Vagon> listaVagoane;
    private VagonAdapter adapter;

    private int pozitieSelectata = -1;

    private final ActivityResultLauncher<Intent> editVagonLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            Vagon vagonModificat =
                                    result.getData().getParcelableExtra(
                                            AddVagonActivity.EXTRA_VAGON_OBJECT
                                    );

                            if (vagonModificat != null
                                    && pozitieSelectata >= 0
                                    && pozitieSelectata < listaVagoane.size()) {


                                listaVagoane.set(
                                        pozitieSelectata,
                                        vagonModificat
                                );

                                adapter.notifyDataSetChanged();

                                trimiteListaActualizata();

                                Toast.makeText(
                                        this,
                                        "Vagon modificat cu succes!",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vagoane);

        listaVagoaneLv = findViewById(R.id.listview);

        listaVagoane = getIntent()
                .getParcelableArrayListExtra("lista_vagoane");

        if (listaVagoane == null) {
            listaVagoane = new ArrayList<>();
        }

        adapter = new VagonAdapter(
                listaVagoane,
                this,
                R.layout.item_vagon
        );

        listaVagoaneLv.setAdapter(adapter);


        listaVagoaneLv.setOnItemClickListener(
                (parent, view, position, id) -> {

                    pozitieSelectata = position;

                    Vagon vagonSelectat =
                            listaVagoane.get(position);

                    Intent intent = new Intent(
                            ListaVagoaneActivity.this,
                            AddVagonActivity.class
                    );

                    intent.putExtra(
                            AddVagonActivity.EXTRA_VAGON_OBJECT,
                            vagonSelectat
                    );

                    editVagonLauncher.launch(intent);
                }
        );


        listaVagoaneLv.setOnItemLongClickListener(
                (parent, view, position, id) -> {

                    listaVagoane.remove(position);
                    adapter.notifyDataSetChanged();

                    trimiteListaActualizata();

                    Toast.makeText(
                            this,
                            "Vagon șters!",
                            Toast.LENGTH_SHORT
                    ).show();

                    return true;
                }
        );
    }

    private void trimiteListaActualizata() {
        Intent intentRezultat = new Intent();

        intentRezultat.putParcelableArrayListExtra(
                "lista_vagoane",
                listaVagoane
        );

        setResult(RESULT_OK, intentRezultat);
    }
}

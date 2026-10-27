
package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button culegeBtn;
    private Button culegeAiBtn;
    private Button veziListaBtn;

    private ArrayList<Vagon> listaVagoane = new ArrayList<>();

    private final ActivityResultLauncher<Intent> addVagonLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            Bundle bundle = result.getData().getExtras();

                            if (bundle != null) {

                                Vagon vagonPrimit = (Vagon)
                                        bundle.getSerializable(
                                                AddVagonActivity.EXTRA_VAGON_OBJECT
                                        );

                                if (vagonPrimit != null) {

                                    listaVagoane.add(vagonPrimit);

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Vagon adaugat! Total: " + listaVagoane.size(),
                                            Toast.LENGTH_LONG
                                    ).show();


                                }
                            }
                        }
                    });

    private final ActivityResultLauncher<Intent> listaVagoaneLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            ArrayList<Vagon> listaActualizata =
                                    (ArrayList<Vagon>) result.getData()
                                            .getSerializableExtra("lista_vagoane");

                            if (listaActualizata != null) {
                                listaVagoane.clear();
                                listaVagoane.addAll(listaActualizata);

                                Toast.makeText(
                                        MainActivity.this,
                                        "Vagoane ramase: " + listaVagoane.size(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        culegeBtn = findViewById(R.id.culege_btn);
        culegeAiBtn = findViewById(R.id.culege_ai_btn);
        veziListaBtn = findViewById(R.id.vezi_lista_btn);


        culegeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddVagonActivity.class
            );
            addVagonLauncher.launch(intent);
        });


        culegeAiBtn.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddVagonAIActivity.class
            );
            addVagonLauncher.launch(intent);
        });



        veziListaBtn.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ListaVagoaneActivity.class
            );

            intent.putExtra("lista_vagoane", listaVagoane);

            listaVagoaneLauncher.launch(intent);
        });

    }
}



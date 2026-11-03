package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


public class AddVagonActivity extends AppCompatActivity {

    public static final String EXTRA_VAGON_OBJECT = "extra_vagon_object";


    private Vagon vagonDeEditat;

    private EditText tipVagonEt, greutateEt;
    private Spinner garnituraSp;
    private RatingBar confortRb;
    private CheckBox esteFunctionalCb;
    private Switch areClimatizareSw;
    private RadioGroup dimensiuneRg;
    private Button salveazaBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagon);


        tipVagonEt = findViewById(R.id.tipVagon_et);
        greutateEt = findViewById(R.id.greutate_et);
        garnituraSp = findViewById(R.id.garnitura_sp);
        confortRb = findViewById(R.id.confort_rb);
        esteFunctionalCb = findViewById(R.id.esteFunctional_cb);
        areClimatizareSw = findViewById(R.id.areClimatizare_sw);
        dimensiuneRg = findViewById(R.id.dimensiune_rg);
        salveazaBtn = findViewById(R.id.salveaza_btn);


        vagonDeEditat = getIntent().getParcelableExtra(
                EXTRA_VAGON_OBJECT
        );

        if (vagonDeEditat != null) {

            tipVagonEt.setText(vagonDeEditat.getTipVagon());

            greutateEt.setText(
                    String.valueOf(vagonDeEditat.getGreutate())
            );

            confortRb.setRating(
                    vagonDeEditat.getGradConfort()
            );

            esteFunctionalCb.setChecked(
                    vagonDeEditat.isEsteFunctional()
            );

            if (vagonDeEditat.getDimensiune() == 26.4) {
                dimensiuneRg.check(R.id.dimensiune26_rb);
            } else {
                dimensiuneRg.check(R.id.dimensiune20_rb);
            }

            salveazaBtn.setText("Salvează modificările");
        }


        String[] optiuniGarnitura = {"InterCity", "Regio", "InterRegio", "Marfar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, optiuniGarnitura);
        garnituraSp.setAdapter(adapter);


        salveazaBtn.setOnClickListener(v -> salveazaDate());
    }


    private void salveazaDate() {
        String tipVagon = tipVagonEt.getText().toString().trim();
        String greutateStr = greutateEt.getText().toString().trim();

        if (tipVagon.isEmpty() || greutateStr.isEmpty()) {
            Toast.makeText(this,
                    "Completați toate câmpurile text!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double greutate;

        try {
            greutate = Double.parseDouble(greutateStr);
        } catch (NumberFormatException e) {
            greutateEt.setError("Introduceți o greutate validă!");
            return;
        }

        float gradConfort = confortRb.getRating();
        boolean esteFunctional = esteFunctionalCb.isChecked();

        double dimensiune = 20.0;

        if (dimensiuneRg.getCheckedRadioButtonId()
                == R.id.dimensiune26_rb) {
            dimensiune = 26.4;
        }

        Vagon vagonRezultat;

        if (vagonDeEditat != null) {

            vagonDeEditat.setTipVagon(tipVagon);
            vagonDeEditat.setGreutate(greutate);
            vagonDeEditat.setGradConfort(gradConfort);
            vagonDeEditat.setEsteFunctional(esteFunctional);
            vagonDeEditat.setDimensiune(dimensiune);

            vagonRezultat = vagonDeEditat;
        } else {

            vagonRezultat = new Vagon(
                    tipVagon,
                    greutate,
                    gradConfort,
                    esteFunctional,
                    dimensiune
            );
        }

        Intent intentRezultat = new Intent();

        intentRezultat.putExtra(
                EXTRA_VAGON_OBJECT,
                vagonRezultat
        );

        setResult(RESULT_OK, intentRezultat);
        finish();
    }

}
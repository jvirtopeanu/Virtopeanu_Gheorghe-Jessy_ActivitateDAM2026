package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddVagonAIActivity extends AppCompatActivity {

    public static final String EXTRA_VAGON_OBJECT = "extra_vagon_object";

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

        setContentView(R.layout.activity_vagon_ai);

        tipVagonEt = findViewById(R.id.tipVagon_et);
        greutateEt = findViewById(R.id.greutate_et);
        garnituraSp = findViewById(R.id.garnitura_sp);
        confortRb = findViewById(R.id.confort_rb);
        esteFunctionalCb = findViewById(R.id.esteFunctional_cb);
        areClimatizareSw = findViewById(R.id.areClimatizare_sw);
        dimensiuneRg = findViewById(R.id.dimensiune_rg);
        salveazaBtn = findViewById(R.id.salveaza_btn);

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
            Toast.makeText(this, "Completați toate campurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        double greutate = Double.parseDouble(greutateStr);
        float gradConfort = confortRb.getRating();
        boolean esteFunctional = esteFunctionalCb.isChecked();

        double dimensiune = (dimensiuneRg.getCheckedRadioButtonId() == R.id.dimensiune26_rb) ? 26.4 : 20.0;

        Vagon vagonNou = new Vagon(tipVagon, greutate, gradConfort, esteFunctional, dimensiune);

        Intent intentRezultat = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_VAGON_OBJECT, vagonNou);
        intentRezultat.putExtras(bundle);

        setResult(RESULT_OK, intentRezultat);
        finish();
    }
}
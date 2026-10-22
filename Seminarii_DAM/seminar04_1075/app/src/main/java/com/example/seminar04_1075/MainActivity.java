package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button culegeBtn;
    private Button culegeAiBtn;
    private TextView rezultatTv;


    private final ActivityResultLauncher<Intent> addVagonLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    if (bundle != null) {

                        Vagon vagonPrimit = (Vagon) bundle.getSerializable(AddVagonAIActivity.EXTRA_VAGON_OBJECT);

                        if (vagonPrimit != null) {
                            Toast.makeText(MainActivity.this,
                                    "Vagon salvat: " + vagonPrimit.getTipVagon(),
                                    Toast.LENGTH_LONG).show();
                            rezultatTv.setText(vagonPrimit.toString());
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        culegeBtn = findViewById(R.id.culege_btn);
        culegeAiBtn = findViewById(R.id.culege_ai_btn);
        rezultatTv = findViewById(R.id.rezultat_tv);


        culegeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddVagonActivity.class);
            addVagonLauncher.launch(intent);
        });


        culegeAiBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddVagonAIActivity.class);
            addVagonLauncher.launch(intent);
        });
    }
}
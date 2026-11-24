
package com.example.seminar09_1075;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VremeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_vreme);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.jessy_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText orasET = findViewById(R.id.jessy_editText_oras);
        Button cautaBTN = findViewById(R.id.jessy_button_cauta);
        TextView rezultatTV = findViewById(R.id.jessy_textView_rezultat);
        TextView temperaturiTV = findViewById(R.id.jessy_textView_temperaturi);
        Spinner zileSpinner = findViewById(R.id.prognoza_spinner);

        cautaBTN.setOnClickListener(v -> {
            String oras = orasET.getText().toString().trim();

            if (oras.isEmpty()) {
                orasET.setError(getString(R.string.introdu_oras));
                return;
            }

            int nrZile = zileSpinner.getSelectedItemPosition() == 0 ? 1 : 5;

            cautaOras(oras, rezultatTV, temperaturiTV, nrZile);
        });

    }

    private void cautaOras(String oras, TextView rezultatTV,
                           TextView temperaturiTV, int nrZile) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            try {
                String apiKey = "CHEIE";

                String link = "https://dataservice.accuweather.com"
                        + "/locations/v1/cities/search"
                        + "?apikey=" + apiKey
                        + "&q=" + URLEncoder.encode(oras, "UTF-8");

                URL url = new URL(link);

                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder builder = new StringBuilder();
                String linie;

                while ((linie = reader.readLine()) != null) {
                    builder.append(linie);
                }

                reader.close();
                conn.disconnect();

                JSONArray vector = new JSONArray(builder.toString());

                String codOras = "";

                if (vector.length() > 0) {
                    JSONObject obiect = vector.getJSONObject(0);
                    codOras = obiect.getString("Key");
                }

                String rezultat = codOras;

                if (!codOras.isEmpty()) {
                    afiseazaTemperaturi(codOras, temperaturiTV, nrZile);
                }

                handler.post(() -> {
                    rezultatTV.setText(rezultat.isEmpty()
                            ? getString(R.string.oras_negasit)
                            : getString(R.string.cod_oras, rezultat));
                });

            } catch (Exception e) {

                handler.post(() -> {
                    rezultatTV.setText(
                            getString(R.string.eroare_api, e.getMessage())
                    );
                });
            }

        });

        executor.shutdown();
    }

    private void afiseazaTemperaturi(String codOras,
                                     TextView temperaturiTV, int nrZile) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            HttpURLConnection conn = null;

            try {
                String apiKey = "cheie";

                String link = "https://dataservice.accuweather.com"
                        + "/forecasts/v1/daily/" + nrZile + "day/" + codOras
                        + "?apikey=" + apiKey
                        + "&metric=true";

                URL url = new URL(link);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder builder = new StringBuilder();
                String linie;

                while ((linie = reader.readLine()) != null) {
                    builder.append(linie);
                }

                reader.close();

                JSONObject obiect = new JSONObject(builder.toString());

                JSONArray prognoze = obiect.getJSONArray("DailyForecasts");

                StringBuilder rezultat = new StringBuilder();

                for (int i = 0; i < prognoze.length(); i++) {

                    JSONObject zi = prognoze.getJSONObject(i);

                    JSONObject temperatura = zi.getJSONObject("Temperature");

                    double minima = temperatura
                            .getJSONObject("Minimum")
                            .getDouble("Value");

                    double maxima = temperatura
                            .getJSONObject("Maximum")
                            .getDouble("Value");

                    rezultat.append("Ziua ").append(i + 1).append("\n");
                    rezultat.append("Temperatura minimă: ")
                            .append(minima).append(" °C\n");
                    rezultat.append("Temperatura maximă: ")
                            .append(maxima).append(" °C\n\n");
                }

                handler.post(() -> {
                    temperaturiTV.setText(rezultat.toString());
                });

            } catch (Exception e) {
                handler.post(() -> {
                    temperaturiTV.setText("Eroare prognoză: " + e.getMessage());
                });
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });

        executor.shutdown();
    }
}

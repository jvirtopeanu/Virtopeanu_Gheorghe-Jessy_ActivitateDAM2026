package com.example.seminar04_1075;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GalerieVagoaneActivity extends AppCompatActivity {

    private ListView listaImagini;
    private ArrayList<ImagineVagon> lista = new ArrayList<>();
    private ImagineVagonAdapter adapter;

    private String[] linkuriImagini = {
            "https://commons.wikimedia.org/wiki/Special:FilePath/17b_IRN_402_interior_261124.jpg",
            "https://commons.wikimedia.org/wiki/Special:FilePath/Dining_car.jpg",
            "https://commons.wikimedia.org/wiki/Special:FilePath/SNCB_Double_deck_coach.jpg",
            "https://commons.wikimedia.org/wiki/Special:FilePath/Tank_Wagon_(BTPNM).jpg",
            "https://commons.wikimedia.org/wiki/Special:FilePath/Boxcar,_CP_Rail.jpg"
    };

    private String[] descrieri = {
            "Vagon de dormit - destinat calatoriilor de noapte.",
            "Vagon restaurant - pentru servirea mesei in tren.",
            "Vagon etajat - transportul pasagerilor pe doua niveluri.",
            "Vagon cisterna - transportul lichidelor si gazelor.",
            "Vagon de marfa - transportul marfurilor."
    };

    private String[] linkuriWeb = {
            "https://en.wikipedia.org/wiki/Sleeping_car",
            "https://en.wikipedia.org/wiki/Dining_car",
            "https://en.wikipedia.org/wiki/Bilevel_rail_car",
            "https://en.wikipedia.org/wiki/Tank_car",
            "https://en.wikipedia.org/wiki/Boxcar"
    };

    private ExecutorService executor =
            Executors.newSingleThreadExecutor();

    private Handler handler =
            new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galerie_vagoane);

        listaImagini = findViewById(R.id.imagini);

        descarcaImagini();

        listaImagini.setOnItemClickListener(
                (parent, view, position, id) -> {

                    ImagineVagon imagineSelectata =
                            lista.get(position);

                    Intent intent = new Intent(
                            GalerieVagoaneActivity.this,
                            DetaliiVagonActivity.class
                    );

                    intent.putExtra(
                            "link",
                            imagineSelectata.getLink()
                    );

                    startActivity(intent);
                }
        );
    }


    private void descarcaImagini() {

        executor.execute(() -> {

            ArrayList<ImagineVagon> listaDescarcata =
                    new ArrayList<>();

            for (int i = 0; i < linkuriImagini.length; i++) {

                Bitmap imagine =
                        descarcaImagine(linkuriImagini[i]);

                ImagineVagon obiect = new ImagineVagon(
                        descrieri[i],
                        imagine,
                        linkuriWeb[i]
                );

                listaDescarcata.add(obiect);
            }

            handler.post(() -> {

                if (isFinishing() || isDestroyed()) {
                    return;
                }

                lista.addAll(listaDescarcata);

                adapter = new ImagineVagonAdapter(
                        lista,
                        GalerieVagoaneActivity.this,
                        R.layout.item_imagine_vagon
                );

                listaImagini.setAdapter(adapter);

                Toast.makeText(
                        GalerieVagoaneActivity.this,
                        "Galeria a fost incarcata!",
                        Toast.LENGTH_SHORT
                ).show();
            });
        });
    }

    private Bitmap descarcaImagine(String link) {

        HttpURLConnection conexiune = null;

        try {
            URL url = new URL(link);

            conexiune =
                    (HttpURLConnection) url.openConnection();

            conexiune.setConnectTimeout(15000);
            conexiune.setReadTimeout(15000);
            conexiune.setInstanceFollowRedirects(true);
            conexiune.setRequestProperty(
                    "User-Agent",
                    "DAM-Vagoane/1.0 (Android educational project)"
            );

            conexiune.connect();

            try (InputStream inputStream =
                         conexiune.getInputStream()) {

                return BitmapFactory.decodeStream(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (conexiune != null) {
                conexiune.disconnect();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}

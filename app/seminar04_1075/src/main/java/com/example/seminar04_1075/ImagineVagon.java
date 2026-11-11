package com.example.seminar04_1075;

import android.graphics.Bitmap;

public class ImagineVagon {

    private String textAfisat;
    private Bitmap imagine;
    private String link;

    public ImagineVagon(String textAfisat,
                        Bitmap imagine,
                        String link) {
        this.textAfisat = textAfisat;
        this.imagine = imagine;
        this.link = link;
    }

    public String getTextAfisat() {
        return textAfisat;
    }

    public Bitmap getImagine() {
        return imagine;
    }

    public String getLink() {
        return link;
    }
}

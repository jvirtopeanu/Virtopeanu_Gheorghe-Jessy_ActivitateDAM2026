package com.example.seminar04_1075;

import java.io.Serializable;

public class Vagon implements Serializable {
    String tipVagon;
    double greutate;
    float gradConfort;
    boolean esteFunctional;
    double dimensiune;

    public Vagon(String tipVagon, double greutate, float gradConfort, boolean esteFunctional, double dimensiune) {
        this.tipVagon = tipVagon;
        this.greutate = greutate;
        this.gradConfort = gradConfort;
        this.esteFunctional = esteFunctional;
        this.dimensiune = dimensiune;
    }

    public Vagon() {
        this.tipVagon = "Necunoscut";
        this.greutate = 0.0;
        this.gradConfort = 0.0f;
        this.esteFunctional = false;
        this.dimensiune = 0.0;
    }

    public String getTipVagon() {
        return tipVagon;
    }

    public void setTipVagon(String tipVagon) {
        this.tipVagon = tipVagon;
    }

    public double getGreutate() {
        return greutate;
    }

    public void setGreutate(double greutate) {
        this.greutate = greutate;
    }

    public float getGradConfort() {
        return gradConfort;
    }

    public void setGradConfort(float gradConfort) {
        this.gradConfort = gradConfort;
    }

    public boolean isEsteFunctional() {
        return esteFunctional;
    }

    public void setEsteFunctional(boolean esteFunctional) {
        this.esteFunctional = esteFunctional;
    }

    public double getDimensiune() {
        return dimensiune;
    }

    public void setDimensiune(double dimensiune) {
        this.dimensiune = dimensiune;
    }

    @Override
    public String toString() {
        return "Vagon{" +
                "tipVagon='" + tipVagon + '\'' +
                ", greutate=" + greutate +
                ", gradConfort=" + gradConfort +
                ", esteFunctional=" + esteFunctional +
                ", dimensiune=" + dimensiune +
                '}';
    }
}

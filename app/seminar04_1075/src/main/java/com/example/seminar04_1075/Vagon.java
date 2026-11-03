package com.example.seminar04_1075;


import android.os.Parcel;
import android.os.Parcelable;

public class Vagon implements Parcelable {
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

    protected Vagon(Parcel in) {
        tipVagon = in.readString();
        greutate = in.readDouble();
        gradConfort = in.readFloat();
        esteFunctional = in.readByte() != 0;
        dimensiune = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipVagon);
        dest.writeDouble(greutate);
        dest.writeFloat(gradConfort);
        dest.writeByte((byte) (esteFunctional ? 1 : 0));
        dest.writeDouble(dimensiune);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Vagon> CREATOR = new Creator<Vagon>() {
        @Override
        public Vagon createFromParcel(Parcel in) {
            return new Vagon(in);
        }

        @Override
        public Vagon[] newArray(int size) {
            return new Vagon[size];
        }
    };

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

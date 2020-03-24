package aplications.villadiego.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private String nombre;
    private String director;
    private String idioma;
    private String genero;

    public Movies(String nombre, String director, String idioma, String genero) {
        this.nombre = nombre;
        this.director = director;
        this.idioma = idioma;
        this.genero = genero;
    }

    public Movies (Parcel in){
        this.nombre = in.readString();
        this.director = in.readString();
        this.idioma = in.readString();
        this.genero = in.readString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return  nombre
                + "\n" +
                director
                + "\n" +
                idioma
                + "\n" +
                genero;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.director);
        dest.writeString(this.idioma);
        dest.writeString(this.genero);
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}


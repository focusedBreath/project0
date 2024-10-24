package com.revature.models;

//We want this class to directly mirror our roles table
public class Band {

    //Fields should mirror the Db table columns
    private int band_id;
    private String band_name;
    private String band_hometown;
    private String band_best_song;

    //boilerplate code---------------------------

    //Remember, boilerplate code is any blocks of code that are repeated in multiple places
    //Constructors, getters/setters, toString (remember right click -> generate)

    //no args constructor
    public Band() {
    }

    //all args constructor
    public Band(int band_id, String band_name, String band_hometown, String band_best_song) {
        this.band_id = band_id;
        this.band_name = band_name;
        this.band_hometown = band_hometown;
        this.band_best_song = band_best_song;
    }

    //getters/setters
    public int getBand_id() {
        return band_id;
    }

    public void setBand_id(int band_id) {
        this.band_id = band_id;
    }

    public String getBand_name() {
        return band_name;
    }

    public void setBand_name(String band_name) {
        this.band_name = band_name;
    }

    public String getBand_hometown() {
        return band_hometown;
    }

    public void setBand_hometown(String band_hometown) {
        this.band_hometown = band_hometown;
    }

    public String getBand_best_song() {
        return band_best_song;
    }

    public void setBand_best_song(String band_best_song) {
        this.band_best_song = band_best_song;
    }


    //toString
    @Override
    public String toString() {
        return "Band{" +
                "band_id=" + band_id +
                ", band_name='" + band_name + '\'' +
                ", band_hometown='" + band_hometown + '\'' +
                ", band_best_song='" + band_best_song + '\'' +
                '}';
    }

}

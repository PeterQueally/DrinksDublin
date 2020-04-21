package com.example.myapplication;


public class BarDatabase {

    public static String name;
    public String address;
    public Double budweiser, carlsberg, coorslight, corona, desperado, guinness, heineken, miller,
            peroni, tiger;

    public BarDatabase() {

    }

    public BarDatabase(String address, String name, Double budweiser, Double carlsberg, Double coorslight,Double corona,Double desperado,
               Double guinness, Double heineken, Double miller, Double peroni, Double tiger){
        this.name = name;
        this.address = address;
        this.budweiser = budweiser;
        this.carlsberg = carlsberg;
        this.coorslight = coorslight;
        this.corona = corona;
        this.desperado = desperado;
        this.guinness = guinness;
        this.heineken = heineken;
        this.miller = miller;
        this.peroni = peroni;
        this.tiger = tiger;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBudweiser() {
        return budweiser;
    }

    public void setBudweiser(Double budweiser) {
        this.budweiser = budweiser;
    }

    public Double getCarlsberg() {
        return carlsberg;
    }

    public void setCarlsberg(Double carlsberg) {
        this.carlsberg = carlsberg;
    }

    public Double getCoorslight() {
        return coorslight;
    }

    public void setCoorslight(Double coorslight) {
        this.coorslight = coorslight;
    }

    public Double getCorona() {
        return corona;
    }

    public void setCorona(Double corona) {
        this.corona = corona;
    }

    public Double getDesperado() {
        return desperado;
    }

    public void setDesperado(Double desperado) {
        this.desperado = desperado;
    }

    public Double getGuinness() {
        return guinness;
    }

    public void setGuinness(Double guinness) {
        this.guinness = guinness;
    }

    public Double getHeineken() {
        return heineken;
    }

    public void setHeineken(Double heineken) {
        this.heineken = heineken;
    }

    public Double getMiller() {
        return miller;
    }

    public void setMiller(Double miller) {
        this.miller = miller;
    }

    public Double getPeroni() {
        return peroni;
    }

    public void setPeroni(Double peroni) {
        this.peroni = peroni;
    }

    public Double getTiger() {
        return tiger;
    }

    public void setTiger(Double tiger) {
        this.tiger = tiger;
    }
}

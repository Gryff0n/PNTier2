package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele;

import java.util.UUID;

public class Evenement {
    private final int identifiant;
    private String nom;
    private String lieu;
    private String date;

    public Evenement(int identifiant, String nom, String lieu, String date) {
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.identifiant = identifiant;
    }

    public int getIdentifiant() {
        return identifiant;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto;

public class EvenementDTO {
    private int identifiant;
    private String nom;
    private String lieu;
    private String date;

    public EvenementDTO(int identifiant, String nom, String lieu, String date) {
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.identifiant = identifiant;
    }

    public EvenementDTO() {
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

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}

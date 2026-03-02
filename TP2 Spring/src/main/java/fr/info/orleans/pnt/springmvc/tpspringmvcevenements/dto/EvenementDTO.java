package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EvenementDTO {
    private int identifiant=0;

    @NotBlank(message = "Le nom ne doit pas être vide")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @NotBlank(message = "Le lieu ne doit pas être vide")
    @Size(max = 50, message = "Le lieu ne doit pas dépasser 50 caractères")
    private String lieu;

    @NotBlank(message = "La date ne doit pas être vide")
    private String date;

    public EvenementDTO() {
    }

    public EvenementDTO(int identifiant, String nom, String lieu, String date) {
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.identifiant = identifiant;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
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

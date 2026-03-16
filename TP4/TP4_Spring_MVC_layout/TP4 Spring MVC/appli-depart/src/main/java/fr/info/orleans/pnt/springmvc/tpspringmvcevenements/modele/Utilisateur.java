package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele;

public class Utilisateur {
    private String email;
    private String nom;
    private String prenom;
    private String motDePasse;


    public Utilisateur(String email, String nom, String prenom, String motDePasse) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

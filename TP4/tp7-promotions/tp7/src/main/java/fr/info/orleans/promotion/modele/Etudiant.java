package fr.info.orleans.promotion.modele;

public class Etudiant {
    private String numeroEtudiant;
    private String nom;
    private String prenom;

    public Etudiant(String numeroEtudiant, String nom, String prenom) {
        this.numeroEtudiant = numeroEtudiant;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNumeroEtudiant() { return numeroEtudiant; }
    public void setNumeroEtudiant(String numeroEtudiant) { this.numeroEtudiant = numeroEtudiant; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}

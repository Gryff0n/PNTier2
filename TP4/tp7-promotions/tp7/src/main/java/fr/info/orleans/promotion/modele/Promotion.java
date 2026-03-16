package fr.info.orleans.promotion.modele;

import java.io.InputStream;
import java.util.Collection;

public class Promotion {
    private String identifiantPromotion;
    private String anneeScolaire;
    private Collection<Etudiant> etudiants;

    public Promotion(String identifiantPromotion, String anneeScolaire, InputStream fichierPromotion) {
        this.identifiantPromotion = identifiantPromotion;
        this.anneeScolaire = anneeScolaire;
        this.etudiants = Utils.parseEtudiants(fichierPromotion);
    }

    public Promotion(String identifiantPromotion, String anneeScolaire, Collection<Etudiant> etudiants) {
        this.identifiantPromotion = identifiantPromotion;
        this.anneeScolaire = anneeScolaire;
        this.etudiants = etudiants;
    }

    public String getIdentifiantPromotion() { return identifiantPromotion; }
    public String getAnneeScolaire() { return anneeScolaire; }
    public Collection<Etudiant> getEtudiants() { return etudiants; }
}

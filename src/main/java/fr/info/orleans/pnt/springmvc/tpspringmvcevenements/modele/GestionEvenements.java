package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele;

import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.EvenementDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GestionEvenements {
    private List<Evenement> evenements = new ArrayList<>();
    private int compteur = 0;
    public int enregistrer(String nom, String lieu, String date) {
        Evenement e = new Evenement(compteur,nom, lieu, date);
        compteur++;
        evenements.add(e);
        return e.getIdentifiant();
    }
    public Evenement trouverParId(int id) throws EvenementInconnuException {
        return evenements.stream()
                .filter(e -> e.getIdentifiant()==id)
                .findFirst()
                .orElseThrow(() -> new EvenementInconnuException("Événement inconnu : " + id));
    }
    public List<Evenement> lister() {
        return evenements;
    }

    public int enregistrer(EvenementDTO e) {
        Evenement evenement = new Evenement(compteur,e.getNom(), e.getLieu(), e
                .getDate());
        compteur++;
        e.setIdentifiant(evenement.getIdentifiant());
        evenements.add(evenement);
        return evenement.getIdentifiant();
    }
}

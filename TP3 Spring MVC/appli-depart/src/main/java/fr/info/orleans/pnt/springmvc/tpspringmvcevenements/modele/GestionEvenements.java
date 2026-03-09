package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele;

import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.AuthentificationDTO;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.EvenementDTO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GestionEvenements {
    private int compteur = 0;
    private List<Evenement> evenements = new ArrayList<>();
    private Map<String,Utilisateur> utilisateurs = new HashMap<>();
    private Collection<LaisserPasser> laisserPassers = new ArrayList<>();
    public GestionEvenements() {
        utilisateurs.put("alice@gmail.com", new Utilisateur("alice@gmail.com",
                "Classique", "Alice", "alice"));
        utilisateurs.put("bob@gmail.com", new Utilisateur("bob@gmail.com", "Classique", "Bob", "bob"));
    }
    public LaisserPasser authentifier(AuthentificationDTO dto) throws
            UtilisateurInconnuException {
        Utilisateur utilisateur = utilisateurs.get(dto.getEmail());
        if (utilisateur != null && utilisateur.getMotDePasse().equals(dto
                .getMotDePasse())) {
            LaisserPasser laisserPasser = new LaisserPasser(utilisateur.getEmail());
            laisserPassers.add(laisserPasser);
            return laisserPasser;
        }
        throw new UtilisateurInconnuException("Utilisateur inconnu ou mot de passe incorrect");
    }
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurs.get(email);
    }
    public int enregistrer(LaisserPasser laisserPasser, EvenementDTO dto) throws
            LaisserPasserInvalideException {
        if (!laisserPassers.contains(laisserPasser)) throw new
                LaisserPasserInvalideException();
        Evenement evenement = new Evenement(compteur, dto.getNom(), dto.getLieu(), dto.getDate());
        compteur++;
        dto.setIdentifiant(evenement.getIdentifiant());
        evenements.add(evenement);
        return evenement.getIdentifiant();
    }
    public int enregistrer(LaisserPasser laisserPasser, String nom, String lieu, String date) throws LaisserPasserInvalideException {
        if (!laisserPassers.contains(laisserPasser)) throw new LaisserPasserInvalideException();
        Evenement e = new Evenement(compteur, nom, lieu, date);
        compteur++;
        evenements.add(e);
        return e.getIdentifiant();
    }
    public Evenement trouverParId(LaisserPasser laisserPasser, int id) throws
            EvenementInconnuException, LaisserPasserInvalideException {
        if (!laisserPassers.contains(laisserPasser)) throw new LaisserPasserInvalideException();
        return evenements.stream()
                .filter(e -> e.getIdentifiant() == id)
                .findFirst()
                .orElseThrow(() -> new EvenementInconnuException("Événement inconnu : " +
                        id));
    }
    public List<Evenement> lister(LaisserPasser laisserPasser) throws
            LaisserPasserInvalideException {
        if (!laisserPassers.contains(laisserPasser)) throw new
                LaisserPasserInvalideException();
        return evenements;
    }
    public void logout(LaisserPasser laisserPasser) {
        laisserPassers.remove(laisserPasser);
    }
}
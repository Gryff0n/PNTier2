package fr.info.orleans.promotion.modele;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

    /**
     * Parse un fichier CSV (séparateur point-virgule) en une collection d'étudiants.
     * La première ligne (en-tête) est ignorée.
     * Format attendu : numeroEtudiant;nom;prenom
     */
    public static Collection<Etudiant> parseEtudiants(InputStream fichierPromotion) {
        List<Etudiant> etudiants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(fichierPromotion, StandardCharsets.UTF_8))) {
            String ligne;
            boolean premiereLigne = true;
            while ((ligne = reader.readLine()) != null) {
                if (premiereLigne) {
                    premiereLigne = false;
                    continue; // skip header
                }
                String[] colonnes = ligne.split(";");
                if (colonnes.length >= 3) {
                    etudiants.add(new Etudiant(
                            colonnes[0].trim(),
                            colonnes[1].trim(),
                            colonnes[2].trim()
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV", e);
        }
        return etudiants;
    }
}

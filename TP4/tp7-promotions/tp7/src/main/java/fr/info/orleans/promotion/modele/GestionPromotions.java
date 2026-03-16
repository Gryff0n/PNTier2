package fr.info.orleans.promotion.modele;

import fr.info.orleans.promotion.exception.ConflitPromotionException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

@Service
public class GestionPromotions implements FacadeLogiciel {

    private final Map<String, Promotion> promotions = new HashMap<>();

    /**
     * Au démarrage, recharge automatiquement les CSV sauvegardés dans target/classes.
     */
    public GestionPromotions() {
        try {
            URL resource = getClass().getClassLoader().getResource(".");
            if (resource != null) {
                Path dir = Paths.get(resource.toURI());
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.csv")) {
                    for (Path csvPath : stream) {
                        String filename = csvPath.getFileName().toString();
                        // format: identifiant__annee.csv
                        String baseName = filename.replace(".csv", "");
                        String[] parts = baseName.split("__");
                        if (parts.length == 2) {
                            String identifiant = parts[0];
                            String annee = parts[1].replace("_", "-");
                            try (InputStream is = Files.newInputStream(csvPath)) {
                                String cle = cle(identifiant, annee);
                                promotions.put(cle, new Promotion(identifiant, annee, is));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Pas de CSV existants, on démarre proprement
        }
    }

    @Override
    public void creationNouvellePromotion(String identifiantPromotion, String anneeScolaire,
                                          InputStream fichierPromotion)
            throws ConflitPromotionException {

        String cle = cle(identifiantPromotion, anneeScolaire);
        if (promotions.containsKey(cle)) {
            throw new ConflitPromotionException(
                    "Une promotion avec le même identifiant et la même année scolaire existe déjà");
        }

        // Lire le contenu du flux pour pouvoir le sauvegarder et le parser
        byte[] contenu;
        try {
            contenu = fichierPromotion.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier", e);
        }

        // Sauvegarde sur disque dans target/classes
        try {
            URL resource = getClass().getClassLoader().getResource(".");
            if (resource != null) {
                Path dir = Paths.get(resource.toURI());
                String nomFichier = identifiantPromotion + "__" + anneeScolaire.replace("-", "_") + ".csv";
                Path dest = dir.resolve(nomFichier);
                Files.write(dest, contenu);
            }
        } catch (Exception e) {
            // La persistance échoue silencieusement si le répertoire n'est pas accessible
        }

        Promotion p = new Promotion(identifiantPromotion, anneeScolaire,
                new ByteArrayInputStream(contenu));
        promotions.put(cle, p);
    }

    @Override
    public Promotion getPromotion(String identifiantPromotion, String anneeScolaire) {
        return promotions.get(cle(identifiantPromotion, anneeScolaire));
    }

    @Override
    public Collection<Promotion> getPromotionsLibelles() {
        return promotions.values();
    }

    private String cle(String identifiant, String annee) {
        return identifiant + "__" + annee;
    }
}

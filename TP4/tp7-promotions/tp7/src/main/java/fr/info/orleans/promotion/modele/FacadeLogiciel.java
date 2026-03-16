package fr.info.orleans.promotion.modele;

import fr.info.orleans.promotion.exception.ConflitPromotionException;

import java.io.InputStream;
import java.util.Collection;

public interface FacadeLogiciel {

    /**
     * Crée une nouvelle promotion à partir d'un fichier CSV.
     * @throws ConflitPromotionException si une promotion avec le même identifiant et la même année existe déjà
     */
    void creationNouvellePromotion(String identifiantPromotion, String anneeScolaire, InputStream fichierPromotion)
            throws ConflitPromotionException;

    /**
     * Récupère une promotion par son identifiant et son année scolaire.
     */
    Promotion getPromotion(String identifiantPromotion, String anneeScolaire);

    /**
     * Renvoie toutes les promotions connues.
     */
    Collection<Promotion> getPromotionsLibelles();
}

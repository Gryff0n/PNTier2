package fr.info.orleans.promotion.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class PromotionDTO {

    @NotBlank(message = "L'identifiant de la promotion est obligatoire")
    private String identifiantPromotion;

    @NotBlank(message = "L'année scolaire est obligatoire")
    private String anneeScolaire;

    private MultipartFile fichierPromotion;

    public String getIdentifiantPromotion() { return identifiantPromotion; }
    public void setIdentifiantPromotion(String identifiantPromotion) {
        this.identifiantPromotion = identifiantPromotion.trim().toUpperCase();
    }

    public String getAnneeScolaire() { return anneeScolaire; }
    public void setAnneeScolaire(String anneeScolaire) { this.anneeScolaire = anneeScolaire.trim(); }

    public MultipartFile getFichierPromotion() { return fichierPromotion; }
    public void setFichierPromotion(MultipartFile fichierPromotion) { this.fichierPromotion = fichierPromotion; }
}

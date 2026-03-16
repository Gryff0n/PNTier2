package fr.info.orleans.promotion.controleur;

import fr.info.orleans.promotion.dto.PromotionDTO;
import fr.info.orleans.promotion.exception.ConflitPromotionException;
import fr.info.orleans.promotion.modele.FacadeLogiciel;
import fr.info.orleans.promotion.modele.Promotion;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/promotions")
public class PromotionControleur {

    private final FacadeLogiciel facade;

    public PromotionControleur(FacadeLogiciel facade) {
        this.facade = facade;
    }

    // Redirige / vers le formulaire de gestion
    @GetMapping("/")
    public String index() {
        return "redirect:/promotions/gestion";
    }

    /**
     * Affiche le formulaire de création ET la liste des promotions existantes.
     * Correspond à management_promotion.html dans la carte de navigation.
     */
    @GetMapping("/gestion")
    public String afficherGestion(Model model) {
        model.addAttribute("promotionDTO", new PromotionDTO());
        model.addAttribute("promotions", facade.getPromotionsLibelles());
        return "management_promotion";
    }

    /**
     * Traite la soumission du formulaire de création de promotion.
     */
    @PostMapping("/creation")
    public String creerPromotion(@Valid @ModelAttribute("promotionDTO") PromotionDTO dto,
                                  BindingResult bindingResult,
                                  Model model) {

        // Validation du fichier
        if (dto.getFichierPromotion() == null || dto.getFichierPromotion().isEmpty()) {
            bindingResult.rejectValue("fichierPromotion", "fichier.vide",
                    "Veuillez sélectionner un fichier CSV");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("promotions", facade.getPromotionsLibelles());
            return "management_promotion";
        }

        try {
            facade.creationNouvellePromotion(
                    dto.getIdentifiantPromotion(),
                    dto.getAnneeScolaire(),
                    dto.getFichierPromotion().getInputStream()
            );
        } catch (ConflitPromotionException e) {
            model.addAttribute("erreurConflit", e.getMessage());
            model.addAttribute("promotions", facade.getPromotionsLibelles());
            return "management_promotion";
        } catch (IOException e) {
            model.addAttribute("erreurConflit", "Erreur lors de la lecture du fichier");
            model.addAttribute("promotions", facade.getPromotionsLibelles());
            return "management_promotion";
        }

        // Redirige vers le récap en passant l'identifiant et l'année
        return "redirect:/promotions/recap?identifiant=" + dto.getIdentifiantPromotion()
                + "&annee=" + dto.getAnneeScolaire();
    }

    /**
     * Affiche le récapitulatif : liste des promotions + étudiants de la dernière promotion créée.
     * Correspond à recap_promotion.html dans la carte de navigation.
     */
    @GetMapping("/recap")
    public String afficherRecap(@RequestParam(required = false) String identifiant,
                                 @RequestParam(required = false) String annee,
                                 Model model) {
        model.addAttribute("promotions", facade.getPromotionsLibelles());

        if (identifiant != null && annee != null) {
            Promotion p = facade.getPromotion(identifiant, annee);
            model.addAttribute("promotionSelectionnee", p);
        }

        return "recap_promotion";
    }

    /**
     * Affiche les étudiants d'une promotion cliquée depuis le tableau.
     */
    @GetMapping("/detail")
    public String afficherDetail(@RequestParam String identifiant,
                                  @RequestParam String annee,
                                  Model model) {
        model.addAttribute("promotions", facade.getPromotionsLibelles());
        Promotion p = facade.getPromotion(identifiant, annee);
        model.addAttribute("promotionSelectionnee", p);
        return "recap_promotion";
    }
}

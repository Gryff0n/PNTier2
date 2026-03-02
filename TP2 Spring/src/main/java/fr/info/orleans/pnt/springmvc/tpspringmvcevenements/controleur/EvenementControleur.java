package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.controleur;

import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.EvenementDTO;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.Evenement;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.EvenementInconnuException;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.GestionEvenements;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mesevenements")
public class EvenementControleur {
    private final GestionEvenements gestion;
    public EvenementControleur(GestionEvenements gestion) {
        this.gestion = gestion;
    }

    /*
    @PostMapping("/creation")
    public String ajouter(
            @RequestParam String nom,
            @RequestParam String lieu,
            @RequestParam String date,
            Model model
    ) {
        int identifiant = gestion.enregistrer(nom, lieu, date);
        model.addAttribute("message", "Événement ajouté avec succès !");
        model.addAttribute("identifiant", identifiant);
        return "resume";
    }
    */

    @PostMapping("/creation")
    public String ajouterViaModel(@Valid @ModelAttribute EvenementDTO evenementDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "creation";
        }
        int identifiant = gestion.enregistrer(evenementDTO);
        model.addAttribute("message", "Événement ajouté avec succès !");
        model.addAttribute("identifiant", identifiant);
        return "resume";
    }

    @GetMapping("/evenement/{id}")
    public String afficherUn(@PathVariable int id, Model model) {
        try {
            Evenement evenement = gestion.trouverParId(id);
            EvenementDTO evenementDTO = new EvenementDTO();
            evenementDTO.setIdentifiant(evenement.getIdentifiant());
            evenementDTO.setNom(evenement.getNom());
            evenementDTO.setLieu(evenement.getLieu());
            evenementDTO.setDate(evenement.getDate());
            model.addAttribute("evenement", evenementDTO);
            model.addAttribute("id", id);
            return "detail";
        } catch (EvenementInconnuException e) {
            model.addAttribute("erreur", "Événement introuvable.");
            return "erreur";
        }
    }


    @GetMapping("/evenements")
    public String lister(Model model) {
        model.addAttribute("evenements", gestion.lister());
        return "liste";
    }

    @GetMapping("/creationForm")
    public String gotoCreation(Model model) {
        model.addAttribute("evenementDTO", new EvenementDTO());
        return "creation";
    }
}
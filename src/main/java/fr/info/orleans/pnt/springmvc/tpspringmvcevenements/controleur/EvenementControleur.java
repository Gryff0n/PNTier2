package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.controleur;

import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.EvenementDTO;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.Evenement;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.EvenementInconnuException;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.GestionEvenements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mesevenements")
public class EvenementControleur {
    private final GestionEvenements gestion;
    public EvenementControleur(GestionEvenements gestion) {
        this.gestion = gestion;
    }
    @GetMapping("/ajouter")
    public String ajouter(
            @RequestParam String nom,
            @RequestParam String lieu,
            @RequestParam String date,
            Model model
    ) {
        EvenementDTO e = new EvenementDTO();
        e.setNom(nom);
        e.setLieu(lieu);
        e.setDate(date);
        int identifiant = gestion.enregistrer(e);
        model.addAttribute("message", "Événement ajouté avec succès !");
        model.addAttribute("identifiant", identifiant);
        return "resume";
    }

    @GetMapping("/ajouter2")
    public String ajouterViaModel(@ModelAttribute EvenementDTO evenement, Model model) {
        int identifiant = gestion.enregistrer(evenement);
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
}
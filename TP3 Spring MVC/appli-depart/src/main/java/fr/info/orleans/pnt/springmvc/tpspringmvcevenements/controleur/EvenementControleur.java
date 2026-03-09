package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.controleur;

import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.AuthentificationDTO;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto.EvenementDTO;
import fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mesevenements")
public class EvenementControleur {

    private final GestionEvenements gestion;


    public EvenementControleur(GestionEvenements gestion) {
        this.gestion = gestion;
    }


    @GetMapping("/")
    public String index() {
        return "redirect:/mesevenements/loginform";
    }


    @GetMapping("/creationform")
    public String gotoCreationForm(Model model) {
        model.addAttribute("evenementDTO", new EvenementDTO());
        return "creation";
    }


    @GetMapping("/loginform")
    public String goToLoginForm(Model model) {
        model.addAttribute("authentificationDTO", new AuthentificationDTO());
        return "login";
    }


    @GetMapping("/home")
    public String gotoHome(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        LaisserPasser laisserPasser = (LaisserPasser) session.getAttribute("laisserPasser");
        model.addAttribute("laisserPasser", laisserPasser);
        return "home";
    }


    @PostMapping("/login")
    public String authentifier(
            @Valid @ModelAttribute AuthentificationDTO authentificationDTO,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            LaisserPasser laisserPasser = gestion.authentifier(authentificationDTO);
            session.setAttribute("laisserPasser", laisserPasser);
            return "redirect:/mesevenements/home";
        } catch (UtilisateurInconnuException e) {
            model.addAttribute("erreur", e.getMessage());
            return "login";
        }
    }


    @PostMapping("/creation")
    public String ajouterViaModel(@Valid@ModelAttribute EvenementDTO evenementDTO,
                                  BindingResult bindingResult, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "creation";
        }
        LaisserPasser laisserPasser = (LaisserPasser) session.getAttribute("laisserPasser");
        int identifiant = 0;
        try {
            identifiant = gestion.enregistrer(laisserPasser,evenementDTO);
            model.addAttribute("message", "Événement ajouté avec succès !");
            model.addAttribute("identifiant", identifiant);
            return "resume";
        } catch (LaisserPasserInvalideException e) {
            redirectAttributes.addFlashAttribute("erreur", "Laisser-passer non valide, veuillez vous reconnecter.");
            return "redirect:/mesevenements/loginform";
        }
    }
    @GetMapping("/evenement/{id}")
    public String afficherUn(@PathVariable int id, Model model, RedirectAttributes
            redirectAttributes, HttpSession session) {
        LaisserPasser laisserPasser = (LaisserPasser) session.getAttribute(
                "laisserPasser");
        try {
            Evenement evenement = gestion.trouverParId(laisserPasser,id);
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
        } catch (LaisserPasserInvalideException e) {
            redirectAttributes.addFlashAttribute("erreur", "Laisser-passer non valide, veuillez vous reconnecter.");
            return "redirect:/mesevenements/loginform";
        }
    }
    @GetMapping("/evenements")
    public String lister(Model model, HttpSession session, RedirectAttributes
            redirectAttributes) {
        LaisserPasser laisserPasser = (LaisserPasser) session.getAttribute("laisserPasser");
        try {
            model.addAttribute("evenements", gestion.lister(laisserPasser));
            return "liste";
        } catch (LaisserPasserInvalideException e) {
            redirectAttributes.addFlashAttribute("erreur", "Laisser-passer non valide, veuillez vous reconnecter.");
            return "redirect:/mesevenements/loginform";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        LaisserPasser laisserPasser = (LaisserPasser) session.getAttribute(
                "laisserPasser");
        gestion.logout(laisserPasser);
        session.invalidate();
        return "redirect:/mesevenements/";
    }
}
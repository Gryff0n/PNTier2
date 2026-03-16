package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthentificationDTO {
    @NotNull(message="L'email ne peut pas être vide")
    @NotBlank(message="L'email ne peut pas être vide")
    private String email;
    @NotNull(message="Le mot de passe ne peut pas être vide")
    @NotBlank(message="Le mot de passe ne peut pas être vide")
    private String motDePasse;

    public AuthentificationDTO() {
    }

    public AuthentificationDTO(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

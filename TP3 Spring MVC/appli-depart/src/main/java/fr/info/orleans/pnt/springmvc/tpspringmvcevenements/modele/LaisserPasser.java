package fr.info.orleans.pnt.springmvc.tpspringmvcevenements.modele;

import java.util.UUID;

public class LaisserPasser {
    private String email;
    private String cleSession;
    public LaisserPasser(String email) {
        this.email = email;
        this.cleSession = UUID.randomUUID().toString();
    }
    public String getEmail() {
        return email;
    }
    public String getCleSession() {
        return cleSession;
    }
}

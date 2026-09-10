package com.example.salle_sport.exception;

public class AbonnementNotFoundException extends RuntimeException {

    public AbonnementNotFoundException(Long id) {
        super("Abonnement introuvable avec l'id : " + id);
    }
}
package com.example.salle_sport.exception;

public class AdherentNotFoundException extends RuntimeException {

    public AdherentNotFoundException(Long id) {
        super("Adhérent introuvable avec l'id : " + id);
    }
}
package com.example.repository;

import com.example.entites.Livre;
import java.util.ArrayList;
import java.util.List;

public class LivreRepository implements Repository<Livre> {

    private List<Livre> livres = new ArrayList<>();

    public void save(Livre objet) {
        livres.add(objet);
    }

    public Livre findById(Long id) {
        for (Livre livre : livres) {
            if (livre.getId().equals(id)) {
                return livre;
            }
        }
        return null;
    }

    public List<Livre> findAll() {
        return livres;
    }
}
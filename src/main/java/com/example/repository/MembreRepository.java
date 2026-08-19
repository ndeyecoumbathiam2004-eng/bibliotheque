package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.entites.Membre;

public class MembreRepository implements Repository<Membre> {

    private List<Membre> membres = new ArrayList<>();

    public void save(Membre objet) {
        membres.add(objet);
    }

    public Membre findById(Long id) {
        for (Membre membre : membres) {
            if (membre.getId().equals(id)) {
                return membre;
            }
        }
        return null;
    }

    public List<Membre> findAll() {
        return membres;
    }
}
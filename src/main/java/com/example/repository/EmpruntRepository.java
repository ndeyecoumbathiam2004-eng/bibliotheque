package com.example.repository;
import java.util.ArrayList;
import java.util.List;
import com.example.entites.Emprunt;

public class EmpruntRepository implements Repository<Emprunt> {

    private List<Emprunt> emprunts = new ArrayList<>();

    public void save(Emprunt objet) {
        emprunts.add(objet);
    }

    public Emprunt findById(Long id) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getId().equals(id)) {
                return emprunt;
            }
        }
        return null;
    }

    public List<Emprunt> findAll() {
        return emprunts;
    }
}
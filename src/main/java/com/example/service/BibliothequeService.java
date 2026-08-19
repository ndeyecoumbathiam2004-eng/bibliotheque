package com.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.entites.Categorie;
import com.example.entites.Emprunt;
import com.example.entites.EtatLivre;
import com.example.entites.Livre;
import com.example.entites.Membre;
import com.example.repository.EmpruntRepository;

public class BibliothequeService {

    private EmpruntRepository empruntRepository;

    public BibliothequeService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    public void enregistrerEmprunt(Emprunt emprunt) throws QuotaEmpruntException {

        Livre livre = emprunt.getLivre();
        Membre membre = emprunt.getMembre();

        if (livre.getEtat() != EtatLivre.DISPONIBLE) {
            return;
        }

        int nombreEmprunts = 0;

        for (Emprunt e : empruntRepository.findAll()) {
            if (e.getMembre().getId().equals(membre.getId())) {
                nombreEmprunts++;
            }
        }

        if (nombreEmprunts >= 3) {
            throw new QuotaEmpruntException("Le membre a déjà 3 emprunts en cours.");
        }

        livre.setEtat(EtatLivre.EMPRUNTE);
        empruntRepository.save(emprunt);
    }

    public void enregistrerRetour(Emprunt emprunt) {
        emprunt.getLivre().setEtat(EtatLivre.DISPONIBLE);
    }

    public List<Emprunt> listerEmpruntsMembre(Membre membre) {

        List<Emprunt> resultats = new ArrayList<>();

        for (Emprunt emprunt : empruntRepository.findAll()) {
            if (emprunt.getMembre().getId().equals(membre.getId())) {
                resultats.add(emprunt);
            }
        }

        return resultats;
    }

    public List<Livre> listerLivresEnRetard() {

        List<Livre> resultats = new ArrayList<>();

        for (Emprunt emprunt : empruntRepository.findAll()) {
            if (emprunt.getDateRetourPrevue().isBefore(LocalDate.now())
                    && emprunt.getLivre().getEtat() == EtatLivre.EMPRUNTE) {
                resultats.add(emprunt.getLivre());
            }
        }

        return resultats;
    }

    public Map<Categorie, Long> nombreEmpruntsParCategorie() {
        return empruntRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getLivre().getCategorie(),
                        Collectors.counting()
                ));
    }
}
package com.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.entites.Categorie;
import com.example.entites.Emprunt;
import com.example.entites.EtatLivre;
import com.example.entites.Livre;
import com.example.entites.Membre;
import com.example.repository.Repository;

public class BibliothequeService {

    private Repository<Livre> livreRepository;
    private Repository<Membre> membreRepository;
    private Repository<Emprunt> empruntRepository;

    public BibliothequeService(
            Repository<Livre> livreRepository,
            Repository<Membre> membreRepository,
            Repository<Emprunt> empruntRepository) {

        this.livreRepository = livreRepository;
        this.membreRepository = membreRepository;
        this.empruntRepository = empruntRepository;
    }

    public void enregistrerEmprunt(
            Long membreId,
            Long livreId,
            LocalDate dateRetourPrevue) throws QuotaEmpruntException {

        Membre membre = membreRepository.findById(membreId);
        Livre livre = livreRepository.findById(livreId);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        if (livre == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        if (livre.getEtat() != EtatLivre.DISPONIBLE) {
            System.out.println("Ce livre est déjà emprunté.");
            return;
        }

        long nombreEmprunts = empruntRepository.findAll()
                .stream()
                .filter(e -> e.getMembre().getId().equals(membreId))
                .count();

        if (nombreEmprunts >= 3) {
            throw new QuotaEmpruntException(
                    "Le membre a déjà 3 emprunts simultanés."
            );
        }

        Long nouvelId = (long) (empruntRepository.findAll().size() + 1);

        Emprunt emprunt = new Emprunt(
                nouvelId,
                membre,
                livre,
                LocalDate.now(),
                dateRetourPrevue
        );

        livre.setEtat(EtatLivre.EMPRUNTE);
        empruntRepository.save(emprunt);

        System.out.println("Emprunt enregistré avec succès.");
    }

    public void enregistrerRetour(Long empruntId) {

        Emprunt emprunt = empruntRepository.findById(empruntId);

        if (emprunt == null) {
            System.out.println("Emprunt introuvable.");
            return;
        }

        emprunt.getLivre().setEtat(EtatLivre.DISPONIBLE);

        System.out.println("Livre retourné avec succès.");
    }

    public List<Emprunt> listerEmpruntsMembre(Long membreId) {

        List<Emprunt> resultats = new ArrayList<>();

        for (Emprunt emprunt : empruntRepository.findAll()) {

            if (emprunt.getMembre().getId().equals(membreId)
                    && emprunt.getLivre().getEtat() == EtatLivre.EMPRUNTE) {

                resultats.add(emprunt);
            }
        }

        resultats.sort(
                Comparator.comparing(Emprunt::getDateRetourPrevue)
        );

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
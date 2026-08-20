package com.example;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.entites.Categorie;
import com.example.entites.Emprunt;
import com.example.entites.EtatLivre;
import com.example.entites.Livre;
import com.example.entites.Membre;
import com.example.repository.InMemoryRepository;
import com.example.repository.Repository;
import com.example.service.BibliothequeService;
import com.example.service.QuotaEmpruntException;

public class BibliothequeServiceTest {

    @Test
    void emprunterLivre() throws QuotaEmpruntException {

        Repository<Livre> livreRepository =
                new InMemoryRepository<>(Livre::getId);

        Repository<Membre> membreRepository =
                new InMemoryRepository<>(Membre::getId);

        Repository<Emprunt> empruntRepository =
                new InMemoryRepository<>(Emprunt::getId);

        BibliothequeService service =
                new BibliothequeService(
                        livreRepository,
                        membreRepository,
                        empruntRepository
                );

        Livre livre = new Livre(
                1L,
                "Java",
                "Auteur",
                Categorie.INFORMATIQUE
        );

        Membre membre = new Membre(
                1L,
                "Ali"
        );

        livreRepository.save(livre);
        membreRepository.save(membre);

        service.enregistrerEmprunt(
                1L,
                1L,
                LocalDate.now().plusDays(14)
        );

        assertEquals(
                EtatLivre.EMPRUNTE,
                livre.getEtat()
        );
    }

    @Test
    void retourLivre() throws QuotaEmpruntException {

        Repository<Livre> livreRepository =
                new InMemoryRepository<>(Livre::getId);

        Repository<Membre> membreRepository =
                new InMemoryRepository<>(Membre::getId);

        Repository<Emprunt> empruntRepository =
                new InMemoryRepository<>(Emprunt::getId);

        BibliothequeService service =
                new BibliothequeService(
                        livreRepository,
                        membreRepository,
                        empruntRepository
                );

        Livre livre = new Livre(
                1L,
                "Java",
                "Auteur",
                Categorie.INFORMATIQUE
        );

        Membre membre = new Membre(
                1L,
                "Ali"
        );

        livreRepository.save(livre);
        membreRepository.save(membre);

        service.enregistrerEmprunt(
                1L,
                1L,
                LocalDate.now().plusDays(14)
        );

        service.enregistrerRetour(1L);

        assertEquals(
                EtatLivre.DISPONIBLE,
                livre.getEtat()
        );
    }

    @Test
    void maximumTroisEmprunts() throws QuotaEmpruntException {

        Repository<Livre> livreRepository =
                new InMemoryRepository<>(Livre::getId);

        Repository<Membre> membreRepository =
                new InMemoryRepository<>(Membre::getId);

        Repository<Emprunt> empruntRepository =
                new InMemoryRepository<>(Emprunt::getId);

        BibliothequeService service =
                new BibliothequeService(
                        livreRepository,
                        membreRepository,
                        empruntRepository
                );

        Membre membre = new Membre(
                1L,
                "Ali"
        );

        membreRepository.save(membre);

        for (int i = 1; i <= 3; i++) {

            Livre livre = new Livre(
                    (long) i,
                    "Livre " + i,
                    "Auteur",
                    Categorie.INFORMATIQUE
            );

            livreRepository.save(livre);

            service.enregistrerEmprunt(
                    1L,
                    (long) i,
                    LocalDate.now().plusDays(14)
            );
        }

        Livre livre4 = new Livre(
                4L,
                "Livre 4",
                "Auteur",
                Categorie.INFORMATIQUE
        );

        livreRepository.save(livre4);

        assertThrows(
                QuotaEmpruntException.class,
                () -> service.enregistrerEmprunt(
                        1L,
                        4L,
                        LocalDate.now().plusDays(14)
                )
        );
    }
}
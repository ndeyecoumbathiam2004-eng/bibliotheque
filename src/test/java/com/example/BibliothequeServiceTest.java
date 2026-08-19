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
import com.example.repository.EmpruntRepository;
import com.example.service.BibliothequeService;
import com.example.service.QuotaEmpruntException;

public class BibliothequeServiceTest {

    @Test
    void emprunterLivre() throws QuotaEmpruntException {

    EmpruntRepository repository = new EmpruntRepository();
    BibliothequeService service = new BibliothequeService(repository);
    Livre livre = new Livre(1L, "Java", "Auteur", Categorie.INFORMATIQUE);
    Membre membre = new Membre(1L, "Ali");

        Emprunt emprunt = new Emprunt(
                1L, membre, livre,
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );

        service.enregistrerEmprunt(emprunt);
        assertEquals(EtatLivre.EMPRUNTE, livre.getEtat());
    }

    @Test
    void retourLivre() throws QuotaEmpruntException {

        EmpruntRepository repository = new EmpruntRepository();
        BibliothequeService service = new BibliothequeService(repository);

        Livre livre = new Livre(1L, "Java", "Auteur", Categorie.INFORMATIQUE);
        Membre membre = new Membre(1L, "Ali");

        Emprunt emprunt = new Emprunt(
                1L, membre, livre,
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );

        service.enregistrerEmprunt(emprunt);
        service.enregistrerRetour(emprunt);

        assertEquals(EtatLivre.DISPONIBLE, livre.getEtat());
    }

    @Test
    void maximumTroisEmprunts() throws QuotaEmpruntException {

        EmpruntRepository repository = new EmpruntRepository();
        BibliothequeService service = new BibliothequeService(repository);

        Membre membre = new Membre(1L, "Ali");

        for (int i = 1; i <= 3; i++) {
            Livre livre = new Livre(
                    (long) i,
                    "Livre " + i,
                    "Auteur",
                    Categorie.INFORMATIQUE
            );

            service.enregistrerEmprunt(new Emprunt(
                    (long) i,
                    membre,
                    livre,
                    LocalDate.now(),
                    LocalDate.now().plusDays(14)
            ));
        }

        Livre livre4 = new Livre(
                4L, "Livre 4", "Auteur", Categorie.INFORMATIQUE
        );

        assertThrows(
                QuotaEmpruntException.class,
                () -> service.enregistrerEmprunt(
                        new Emprunt(
                                4L,
                                membre,
                                livre4,
                                LocalDate.now(),
                                LocalDate.now().plusDays(14)
                        )
                )
        );
    }
}
package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.entites.Categorie;
import com.example.entites.Emprunt;
import com.example.entites.EtatLivre;
import com.example.entites.Livre;
import com.example.entites.Membre;
import com.example.repository.InMemoryRepository;
import com.example.repository.Repository;
import com.example.service.BibliothequeService;
import com.example.service.QuotaEmpruntException;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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

        int choix;

        do {

            System.out.println();
            System.out.println("========== MENU ==========");
            System.out.println("1. Ajouter un membre");
            System.out.println("2. Ajouter un livre");
            System.out.println("3. Enregistrer un emprunt");
            System.out.println("4. Retourner un livre");
            System.out.println("5. Voir les emprunts d'un membre");
            System.out.println("6. Voir les livres en retard");
            System.out.println("7. Voir les statistiques");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                System.out.println("Choix invalide. Entrez un chiffre.");
                scanner.next();
                System.out.print("Votre choix : ");
            }

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:

                    System.out.println("\n===== AJOUTER UN MEMBRE =====");

                    Long membreId;

                    do {

                        System.out.print(
                                "Entrez l'ID du membre (0 pour annuler) : "
                        );

                        while (!scanner.hasNextLong()) {
                            System.out.println(
                                    "ID invalide. Entrez un nombre."
                            );
                            scanner.next();
                            System.out.print(
                                    "Entrez l'ID du membre (0 pour annuler) : "
                            );
                        }

                        membreId = scanner.nextLong();
                        scanner.nextLine();

                        if (membreId < 0) {
                            System.out.println(
                                    "L'ID ne peut pas être négatif."
                            );
                        }

                    } while (membreId < 0);

                    if (membreId == 0) {
                        System.out.println("Opération annulée.");
                        break;
                    }

                    System.out.print("Entrez le nom du membre : ");
                    String nom = scanner.nextLine();

                    Membre membre =
                            new Membre(membreId, nom);

                    membreRepository.save(membre);

                    System.out.println(
                            "Membre enregistré avec succès."
                    );

                    break;

                case 2:

                    System.out.println("\n===== AJOUTER UN LIVRE =====");

                    Long livreId =
                            (long) (livreRepository.findAll().size() + 1);

                    System.out.println(
                            "ID du livre généré automatiquement : "
                                    + livreId
                    );

                    System.out.print("Entrez le titre du livre : ");
                    String titre = scanner.nextLine();

                    System.out.print("Entrez l'auteur : ");
                    String auteur = scanner.nextLine();

                    System.out.println("Choisissez une catégorie :");

                    Categorie[] categories =
                            Categorie.values();

                    for (int i = 0; i < categories.length; i++) {
                        System.out.println(
                                (i + 1) + ". " + categories[i]
                        );
                    }

                    int choixCategorie;

                    do {

                        System.out.print("Votre choix : ");

                        while (!scanner.hasNextInt()) {
                            System.out.println(
                                    "Choix invalide. Entrez un nombre."
                            );
                            scanner.next();
                            System.out.print("Votre choix : ");
                        }

                        choixCategorie = scanner.nextInt();
                        scanner.nextLine();

                        if (choixCategorie < 1
                                || choixCategorie > categories.length) {

                            System.out.println(
                                    "Catégorie invalide."
                            );
                        }

                    } while (
                            choixCategorie < 1
                                    || choixCategorie > categories.length
                    );

                    Categorie categorie =
                            categories[choixCategorie - 1];

                    Livre livre =
                            new Livre(
                                    livreId,
                                    titre,
                                    auteur,
                                    categorie
                            );

                    livreRepository.save(livre);

                    System.out.println(
                            "Livre enregistré avec succès."
                    );

                    break;

                case 3:

                    System.out.println(
                            "\n===== ENREGISTRER UN EMPRUNT ====="
                    );

                    System.out.println(
                            "Entrez 0 pour annuler."
                    );

                    Long idMembre = null;
                    Membre membreTrouve = null;

                    do {

                        System.out.print(
                                "Entrez l'ID du membre : "
                        );

                        while (!scanner.hasNextLong()) {
                            System.out.println(
                                    "ID invalide. Entrez un nombre."
                            );
                            scanner.next();
                            System.out.print(
                                    "Entrez l'ID du membre : "
                            );
                        }

                        idMembre = scanner.nextLong();
                        scanner.nextLine();

                        if (idMembre == 0) {
                            System.out.println(
                                    "Opération annulée."
                            );
                            break;
                        }

                        membreTrouve =
                                membreRepository.findById(idMembre);

                        if (membreTrouve == null) {

                            System.out.println(
                                    "Membre introuvable."
                            );

                            System.out.println(
                                    "Veuillez entrer un ID de membre valide."
                            );

                        } else {

                            System.out.println(
                                    "Membre trouvé : "
                                            + membreTrouve.getNom()
                            );
                        }

                    } while (membreTrouve == null);

                    if (idMembre == 0
                            || membreTrouve == null) {
                        break;
                    }

                    LocalDate dateRetour = null;

                    do {

                        System.out.print(
                                "Entrez la date de retour prévue "
                                        + "(AAAA-MM-JJ) : "
                        );

                        String date = scanner.nextLine();

                        if (date.equals("0")) {
                            System.out.println(
                                    "Opération annulée."
                            );
                            break;
                        }

                        if (!date.matches(
                                "\\d{4}-\\d{2}-\\d{2}"
                        )) {

                            System.out.println(
                                    "Date invalide."
                            );

                            System.out.println(
                                    "Le format doit être AAAA-MM-JJ."
                            );

                            continue;
                        }

                        try {

                            dateRetour =
                                    LocalDate.parse(date);

                            System.out.println(
                                    "Date valide."
                            );

                        } catch (Exception e) {

                            System.out.println(
                                    "Date invalide."
                            );

                            System.out.println(
                                    "Cette date n'existe pas."
                            );
                        }

                    } while (dateRetour == null);

                    if (dateRetour == null) {
                        break;
                    }

                    Livre livreDisponible = null;

                    for (Livre livreDisponibleActuel :
                            livreRepository.findAll()) {

                        if (livreDisponibleActuel.getEtat()
                                == EtatLivre.DISPONIBLE) {

                            livreDisponible =
                                    livreDisponibleActuel;

                            break;
                        }
                    }

                    if (livreDisponible == null) {

                        System.out.println(
                                "Aucun livre disponible."
                        );

                        break;
                    }

                    try {

                        service.enregistrerEmprunt(
                                idMembre,
                                livreDisponible.getId(),
                                dateRetour
                        );

                        System.out.println(
                                "Emprunt enregistré avec succès."
                        );

                    } catch (QuotaEmpruntException e) {

                        System.out.println(
                                "Erreur : " + e.getMessage()
                        );
                    }

                    break;

                case 4:

                    System.out.println(
                            "\n===== RETOURNER UN LIVRE ====="
                    );

                    System.out.println(
                            "Entrez 0 pour annuler."
                    );

                    Long idMembreRetour = null;
                    Membre membreRetour = null;

                    do {

                        System.out.print(
                                "Entrez l'ID du membre : "
                        );

                        while (!scanner.hasNextLong()) {
                            System.out.println(
                                    "ID invalide. Entrez un nombre."
                            );
                            scanner.next();
                            System.out.print(
                                    "Entrez l'ID du membre : "
                            );
                        }

                        idMembreRetour =
                                scanner.nextLong();

                        scanner.nextLine();

                        if (idMembreRetour == 0) {

                            System.out.println(
                                    "Opération annulée."
                            );

                            break;
                        }

                        membreRetour =
                                membreRepository.findById(
                                        idMembreRetour
                                );

                        if (membreRetour == null) {

                            System.out.println(
                                    "Membre introuvable."
                            );
                        }

                    } while (membreRetour == null);

                    if (idMembreRetour == 0
                            || membreRetour == null) {
                        break;
                    }

                    List<Emprunt> emprunts =
                            service.listerEmpruntsMembre(
                                    idMembreRetour
                            );

                    if (emprunts.isEmpty()) {

                        System.out.println(
                                "Ce membre n'a aucun emprunt."
                        );

                        break;
                    }

                    System.out.println("\nVos emprunts :");

                    for (Emprunt emprunt : emprunts) {

                        System.out.println(
                                "ID emprunt : "
                                        + emprunt.getId()
                                        + " | Livre : "
                                        + emprunt.getLivre()
                                        .getTitre()
                                        + " | Retour prévu : "
                                        + emprunt
                                        .getDateRetourPrevue()
                        );
                    }

                    Long idEmprunt;

                    do {

                        System.out.print(
                                "Entrez l'ID de l'emprunt "
                                        + "à retourner "
                                        + "(0 pour annuler) : "
                        );

                        while (!scanner.hasNextLong()) {
                            System.out.println(
                                    "ID invalide. Entrez un nombre."
                            );
                            scanner.next();
                            System.out.print(
                                    "Entrez l'ID de l'emprunt "
                                            + "à retourner "
                                            + "(0 pour annuler) : "
                            );
                        }

                        idEmprunt = scanner.nextLong();
                        scanner.nextLine();

                    } while (idEmprunt < 0);

                    if (idEmprunt == 0) {

                        System.out.println(
                                "Opération annulée."
                        );

                        break;
                    }

                    service.enregistrerRetour(idEmprunt);

                    System.out.println(
                            "Livre retourné avec succès."
                    );

                    break;

                case 5:

                    System.out.println(
                            "\n===== EMPRUNTS D'UN MEMBRE ====="
                    );

                    System.out.println(
                            "Entrez 0 pour annuler."
                    );

                    Long idMembreRecherche = null;
                    Membre membreRecherche = null;

                    do {

                        System.out.print(
                                "Entrez l'ID du membre : "
                        );

                        while (!scanner.hasNextLong()) {
                            System.out.println(
                                    "ID invalide. Entrez un nombre."
                            );
                            scanner.next();
                            System.out.print(
                                    "Entrez l'ID du membre : "
                            );
                        }

                        idMembreRecherche =
                                scanner.nextLong();

                        scanner.nextLine();

                        if (idMembreRecherche == 0) {

                            System.out.println(
                                    "Opération annulée."
                            );

                            break;
                        }

                        membreRecherche =
                                membreRepository.findById(
                                        idMembreRecherche
                                );

                        if (membreRecherche == null) {

                            System.out.println(
                                    "Membre introuvable."
                            );
                        }

                    } while (membreRecherche == null);

                    if (idMembreRecherche == 0
                            || membreRecherche == null) {
                        break;
                    }

                    List<Emprunt> resultats =
                            service.listerEmpruntsMembre(
                                    idMembreRecherche
                            );

                    if (resultats.isEmpty()) {

                        System.out.println(
                                "Aucun emprunt trouvé pour ce membre."
                        );

                    } else {

                        for (Emprunt emprunt : resultats) {

                            System.out.println(
                                    "ID emprunt : "
                                            + emprunt.getId()
                                            + " | Livre : "
                                            + emprunt.getLivre()
                                            .getTitre()
                                            + " | Auteur : "
                                            + emprunt.getLivre()
                                            .getAuteur()
                                            + " | Retour prévu : "
                                            + emprunt
                                            .getDateRetourPrevue()
                            );
                        }
                    }

                    break;

                case 6:

                    System.out.println(
                            "\n===== LIVRES EN RETARD ====="
                    );

                    List<Livre> livresEnRetard =
                            service.listerLivresEnRetard();

                    if (livresEnRetard.isEmpty()) {

                        System.out.println(
                                "Aucun livre en retard."
                        );

                    } else {

                        for (Livre livreRetard :
                                livresEnRetard) {

                            System.out.println(
                                    "ID : "
                                            + livreRetard.getId()
                                            + " | Titre : "
                                            + livreRetard.getTitre()
                                            + " | Auteur : "
                                            + livreRetard.getAuteur()
                            );
                        }
                    }

                    break;

                case 7:

                    System.out.println(
                            "\n===== STATISTIQUES ====="
                    );

                    Map<Categorie, Long> statistiques =
                            service.nombreEmpruntsParCategorie();

                    if (statistiques.isEmpty()) {

                        System.out.println(
                                "Aucun emprunt enregistré."
                        );

                    } else {

                        for (Map.Entry<Categorie, Long> entry :
                                statistiques.entrySet()) {

                            System.out.println(
                                    entry.getKey()
                                            + " : "
                                            + entry.getValue()
                                            + " emprunt(s)"
                            );
                        }
                    }

                    break;

                case 0:

                    System.out.println(
                            "\nAu revoir !"
                    );

                    break;

                default:

                    System.out.println(
                            "Choix invalide."
                    );
            }

        } while (choix != 0);

        scanner.close();
    }
}
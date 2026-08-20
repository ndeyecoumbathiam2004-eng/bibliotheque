package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.entites.Categorie;
import com.example.entites.Emprunt;
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

        BibliothequeService service = new BibliothequeService(
                livreRepository,
                membreRepository,
                empruntRepository
        );

        System.out.println("=================================");
        System.out.println("   BIENVENUE À LA BIBLIOTHÈQUE");
        System.out.println("=================================");

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

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:

                    System.out.println("\n===== AJOUTER UN MEMBRE =====");

                    System.out.print("Entrez l'ID du membre : ");
                    Long membreId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Entrez le nom du membre : ");
                    String nom = scanner.nextLine();

                    Membre membre = new Membre(membreId, nom);
                    membreRepository.save(membre);

                    System.out.println(
                            "Membre enregistré avec succès."
                    );

                    break;

                case 2:

                    System.out.println("\n===== AJOUTER UN LIVRE =====");

                    System.out.print("Entrez l'ID du livre : ");
                    Long livreId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Entrez le titre du livre : ");
                    String titre = scanner.nextLine();

                    System.out.print("Entrez l'auteur : ");
                    String auteur = scanner.nextLine();

                    System.out.println("Choisissez une catégorie :");

                    Categorie[] categories = Categorie.values();

                    for (int i = 0; i < categories.length; i++) {
                        System.out.println(
                                (i + 1) + ". " + categories[i]
                        );
                    }

                    System.out.print("Votre choix : ");
                    int choixCategorie = scanner.nextInt();
                    scanner.nextLine();

                    if (choixCategorie < 1
                            || choixCategorie > categories.length) {

                        System.out.println("Catégorie invalide.");
                        break;
                    }

                    Categorie categorie =
                            categories[choixCategorie - 1];

                    Livre livre = new Livre(
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

                    System.out.println("\n===== ENREGISTRER UN EMPRUNT =====");

                    System.out.print("Entrez l'ID du membre : ");
                    Long idMembre = scanner.nextLong();

                    System.out.print("Entrez l'ID du livre : ");
                    Long idLivre = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print(
                            "Entrez la date de retour prévue (AAAA-MM-JJ) : "
                    );

                    LocalDate dateRetour =
                            LocalDate.parse(scanner.nextLine());

                    try {

                        service.enregistrerEmprunt(
                                idMembre,
                                idLivre,
                                dateRetour
                        );

                    } catch (QuotaEmpruntException e) {

                        System.out.println(
                                "Erreur : " + e.getMessage()
                        );
                    }

                    break;

                case 4:

                    System.out.println("\n===== RETOURNER UN LIVRE =====");

                    System.out.print("Entrez l'ID du membre : ");
                    Long idMembreRetour = scanner.nextLong();
                    scanner.nextLine();

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
                                + emprunt.getLivre().getTitre()
                                + " | ID livre : "
                                + emprunt.getLivre().getId()
                                + " | Retour prévu : "
                                + emprunt.getDateRetourPrevue()
                        );
                    }

                    System.out.print(
                            "\nEntrez l'ID de l'emprunt à retourner : "
                    );

                    Long idEmprunt = scanner.nextLong();
                    scanner.nextLine();

                    service.enregistrerRetour(idEmprunt);

                    break;

                case 5:

                    System.out.println(
                            "\n===== EMPRUNTS D'UN MEMBRE ====="
                    );

                    System.out.print(
                            "Entrez l'ID du membre : "
                    );

                    Long idMembreRecherche =
                            scanner.nextLong();
                    scanner.nextLine();

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
                                    + emprunt.getLivre().getTitre()
                                    + " | Auteur : "
                                    + emprunt.getLivre().getAuteur()
                                    + " | Retour prévu : "
                                    + emprunt.getDateRetourPrevue()
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
                            "\nAu revoir ! Merci d'avoir utilisé la bibliothèque."
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
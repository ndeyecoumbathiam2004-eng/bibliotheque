GESTION DUNE BIBLIOTHEQUE

DESCRIPTION:
Ce projet est une application JAVA en console dune bibliotheque qui gere les livres ,emprunts et les membres de cette derniere

FONCTIONNALITES:
enregistrer un emprunt, enregistrer un retour, lister les emprunts d'un membre, lister les livres en retard

REGLES METIER:
Un membre peut avoir au maximum 3 emprunts simultanés ;
Un livre est disponible ou emprunté ;
Un emprunt a une date de sortie et une date de retour prévue;
Un livre en retard est un livre dont la date de retour prévue est dépassée
et qui est toujours emprunté.

CHOIX DE CONCEPTION:
Le projet utilise plusieurs classes pour représenter les éléments de la
bibliothèque :

 `Livre` représente un livre
 `Membre` représente un membre
 `Emprunt` représente un emprunt
 `Categorie` permet de définir la catégorie d'un livre
 `EtatLivre` permet de connaître l'état d'un livre

La gestion des emprunts est réalisée dans `BibliothequeService`
Les emprunts sont stockés dans `EmpruntRepository`
Le dépôt utilise l'interface générique `Repository<T>` afin de pouvoir
utiliser la même structure pour différents types d'objets
Une exception personnalisée `QuotaEmpruntException` est utilisée lorsqu'un
membre essaie de dépasser la limite de 3 emprunts
Une opération `Stream` est utilisée pour compter les emprunts par catégorie.
Un `Comparator` est utilisé pour trier les emprunts selon leur date de
retour prévue.

TESTS:
Des tests JUnit vérifient les principales règles métier et ils sont executes avec MAVEN
"mvn test"

TECHNOLOGIES:
JAVA 17;JUNIT5;MAVEN
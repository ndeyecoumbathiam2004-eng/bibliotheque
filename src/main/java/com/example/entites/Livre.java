package com.example.entites;

public class Livre {

    private Long id;
    private String titre;
    private String auteur;
    private Categorie categorie;
    private EtatLivre etat;

    public Livre(Long id, String titre, String auteur, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.etat = EtatLivre.DISPONIBLE;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public EtatLivre getEtat() {
        return etat;
    }

    public void setEtat(EtatLivre etat) {
        this.etat = etat;
    }

    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Livre)) return false;

    Livre livre = (Livre) obj;
    return id.equals(livre.id);
}

public int hashCode() {
    return id.hashCode();
}
}
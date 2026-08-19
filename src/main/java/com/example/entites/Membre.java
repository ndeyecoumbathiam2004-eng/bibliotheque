package com.example.entites;

public class Membre {

    private Long id;
    private String nom;

public Membre(Long id, String nom) {
      this.id = id;
      this.nom = nom;
}      

public Long getId(){
    return id;
}   

public String getNom(){
    return nom;
}
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Membre)) return false;

    Membre membre = (Membre) obj;
    return id.equals(membre.id);
}

public int hashCode() {
    return id.hashCode();
}

}


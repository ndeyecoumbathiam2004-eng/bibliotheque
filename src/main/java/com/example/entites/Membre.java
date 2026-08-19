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

}


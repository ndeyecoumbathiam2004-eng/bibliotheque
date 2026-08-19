package com.example.entites;
import java.time.LocalDate;

public class Emprunt {
    private Long id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateSortie;
    private LocalDate dateRetourPrevue;

public Emprunt(Long id, Membre membre, Livre livre, LocalDate dateSortie,LocalDate dateRetourPrevue) {
    this.id = id;
    this.membre = membre;
    this.livre = livre;
    this.dateSortie = dateSortie;
    this.dateRetourPrevue = dateRetourPrevue;
}

public Long getId(){
     return id;
}

public Membre getMembre(){
    return membre;
}
 
public Livre getLivre(){
    return livre;
}    

public LocalDate getDateSortie(){
    return dateSortie;
}    

public LocalDate getDateRetourPrevue(){
    return dateRetourPrevue;
}    

}
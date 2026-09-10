package com.example.salle_sport.dto;

import java.time.LocalDate;

public class AbonnementResponseDTO {

    private Long id;
    private String type;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long adherentId;

    public AbonnementResponseDTO(
            Long id,
            String type,
            LocalDate dateDebut,
            LocalDate dateFin,
            Long adherentId) {

        this.id = id;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.adherentId = adherentId;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Long getAdherentId() {
        return adherentId;
    }
}
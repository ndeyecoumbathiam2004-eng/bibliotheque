package com.example.salle_sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.salle_sport.entity.Adherent;

public interface AdherentRepository extends JpaRepository<Adherent, Long> {
}
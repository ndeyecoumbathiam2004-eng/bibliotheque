package com.example.salle_sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.salle_sport.entity.Abonnement;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

}
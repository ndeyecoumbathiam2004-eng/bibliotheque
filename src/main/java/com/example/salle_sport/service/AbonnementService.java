package com.example.salle_sport.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.salle_sport.dto.AbonnementRequestDTO;
import com.example.salle_sport.dto.AbonnementResponseDTO;
import com.example.salle_sport.entity.Abonnement;
import com.example.salle_sport.entity.Adherent;
import com.example.salle_sport.exception.AbonnementNotFoundException;
import com.example.salle_sport.exception.AdherentNotFoundException;
import com.example.salle_sport.repository.AbonnementRepository;
import com.example.salle_sport.repository.AdherentRepository;

@Service
public class AbonnementService {

    private final AbonnementRepository repository;
    private final AdherentRepository adherentRepository;

    public AbonnementService(
            AbonnementRepository repository,
            AdherentRepository adherentRepository) {
        this.repository = repository;
        this.adherentRepository = adherentRepository;
    }

    public AbonnementResponseDTO create(AbonnementRequestDTO dto) {

        Adherent adherent = adherentRepository.findById(dto.getAdherentId())
                .orElseThrow(() -> new AdherentNotFoundException(dto.getAdherentId()));

        Abonnement abonnement = new Abonnement();

        abonnement.setType(dto.getType());
        abonnement.setDateDebut(dto.getDateDebut());
        abonnement.setDateFin(dto.getDateFin());
        abonnement.setAdherent(adherent);

        Abonnement saved = repository.save(abonnement);

        return toResponseDTO(saved);
    }

    public Page<AbonnementResponseDTO> getAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    public AbonnementResponseDTO getById(Long id) {

        Abonnement abonnement = repository.findById(id)
                .orElseThrow(() -> new AbonnementNotFoundException(id));

        return toResponseDTO(abonnement);
    }

    public AbonnementResponseDTO update(
            Long id,
            AbonnementRequestDTO dto) {

        Abonnement abonnement = repository.findById(id)
                .orElseThrow(() -> new AbonnementNotFoundException(id));

        Adherent adherent = adherentRepository.findById(dto.getAdherentId())
                .orElseThrow(() -> new AdherentNotFoundException(dto.getAdherentId()));

        abonnement.setType(dto.getType());
        abonnement.setDateDebut(dto.getDateDebut());
        abonnement.setDateFin(dto.getDateFin());
        abonnement.setAdherent(adherent);

        Abonnement updated = repository.save(abonnement);

        return toResponseDTO(updated);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new AbonnementNotFoundException(id);
        }

        repository.deleteById(id);
    }

    private AbonnementResponseDTO toResponseDTO(Abonnement a) {

        return new AbonnementResponseDTO(
                a.getId(),
                a.getType(),
                a.getDateDebut(),
                a.getDateFin(),
                a.getAdherent().getId()
        );
    }
}
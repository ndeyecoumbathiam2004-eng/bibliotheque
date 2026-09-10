package com.example.salle_sport.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.salle_sport.dto.AdherentRequestDTO;
import com.example.salle_sport.dto.AdherentResponseDTO;
import com.example.salle_sport.entity.Adherent;
import com.example.salle_sport.exception.AdherentNotFoundException;
import com.example.salle_sport.repository.AdherentRepository;

@Service
public class AdherentService {

    private final AdherentRepository repository;

    public AdherentService(AdherentRepository repository) {
        this.repository = repository;
    }

    public AdherentResponseDTO create(AdherentRequestDTO dto) {
        Adherent adherent = new Adherent();
        adherent.setNom(dto.getNom());
        adherent.setPrenom(dto.getPrenom());
        adherent.setEmail(dto.getEmail());
        adherent.setTelephone(dto.getTelephone());
        adherent.setDateNaissance(dto.getDateNaissance());

        Adherent saved = repository.save(adherent);
        return toResponseDTO(saved);
    }

    public Page<AdherentResponseDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    public AdherentResponseDTO getById(Long id) {
        Adherent adherent = repository.findById(id)
                .orElseThrow(() -> new AdherentNotFoundException(id));
        return toResponseDTO(adherent);
    }

    public AdherentResponseDTO update(Long id, AdherentRequestDTO dto) {
        Adherent adherent = repository.findById(id)
                .orElseThrow(() -> new AdherentNotFoundException(id));

        adherent.setNom(dto.getNom());
        adherent.setPrenom(dto.getPrenom());
        adherent.setEmail(dto.getEmail());
        adherent.setTelephone(dto.getTelephone());
        adherent.setDateNaissance(dto.getDateNaissance());

        Adherent updated = repository.save(adherent);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AdherentNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private AdherentResponseDTO toResponseDTO(Adherent a) {
        return new AdherentResponseDTO(
                a.getId(),
                a.getNom(),
                a.getPrenom(),
                a.getEmail(),
                a.getTelephone(),
                a.getDateNaissance()
        );
    }
}
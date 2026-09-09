package com.example.service;

import com.example.dto.AdherentRequestDTO;
import com.example.dto.AdherentResponseDTO;
import com.example.entity.Adherent;
import com.example.repository.AdherentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdherentService {

    private final AdherentRepository adherentRepository;

    public AdherentService(AdherentRepository adherentRepository) {
        this.adherentRepository = adherentRepository;
    }

    public List<AdherentResponseDTO> getAllAdherents() {
        return adherentRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    public AdherentResponseDTO getAdherentById(Long id) {
        Adherent adherent = adherentRepository.findById(id).orElse(null);

        if (adherent == null) {
            return null;
        }

        return convertirEnDTO(adherent);
    }

    public AdherentResponseDTO createAdherent(AdherentRequestDTO dto) {

        Adherent adherent = new Adherent(
                dto.getNom(),
                dto.getPrenom(),
                dto.getEmail(),
                dto.getTelephone(),
                dto.getDateNaissance()
        );

        Adherent sauvegarde = adherentRepository.save(adherent);

        return convertirEnDTO(sauvegarde);
    }

    public AdherentResponseDTO updateAdherent(Long id, AdherentRequestDTO dto) {

        Adherent adherent = adherentRepository.findById(id).orElse(null);

        if (adherent == null) {
            return null;
        }

        adherent.setNom(dto.getNom());
        adherent.setPrenom(dto.getPrenom());
        adherent.setEmail(dto.getEmail());
        adherent.setTelephone(dto.getTelephone());
        adherent.setDateNaissance(dto.getDateNaissance());

        Adherent modifie = adherentRepository.save(adherent);

        return convertirEnDTO(modifie);
    }

    public boolean deleteAdherent(Long id) {

        if (!adherentRepository.existsById(id)) {
            return false;
        }

        adherentRepository.deleteById(id);
        return true;
    }

    private AdherentResponseDTO convertirEnDTO(Adherent adherent) {

        return new AdherentResponseDTO(
                adherent.getId(),
                adherent.getNom(),
                adherent.getPrenom(),
                adherent.getEmail(),
                adherent.getTelephone(),
                adherent.getDateNaissance()
        );
    }
}
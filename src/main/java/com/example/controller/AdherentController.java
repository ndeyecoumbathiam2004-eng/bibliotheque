package com.example.controller;

import com.example.dto.AdherentRequestDTO;
import com.example.dto.AdherentResponseDTO;
import com.example.service.AdherentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adherents")
public class AdherentController {

    private final AdherentService adherentService;

    public AdherentController(AdherentService adherentService) {
        this.adherentService = adherentService;
    }

    @GetMapping
    public List<AdherentResponseDTO> getAllAdherents() {
        return adherentService.getAllAdherents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdherentResponseDTO> getAdherentById(@PathVariable Long id) {

        AdherentResponseDTO adherent = adherentService.getAdherentById(id);

        if (adherent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(adherent);
    }

    @PostMapping
    public ResponseEntity<AdherentResponseDTO> createAdherent(
            @Valid @RequestBody AdherentRequestDTO dto) {

        return ResponseEntity.ok(adherentService.createAdherent(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdherentResponseDTO> updateAdherent(
            @PathVariable Long id,
            @Valid @RequestBody AdherentRequestDTO dto) {

        AdherentResponseDTO adherent = adherentService.updateAdherent(id, dto);

        if (adherent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(adherent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdherent(@PathVariable Long id) {

        boolean deleted = adherentService.deleteAdherent(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Adhérent supprimé");
    }
}
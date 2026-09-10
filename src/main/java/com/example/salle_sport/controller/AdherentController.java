package com.example.salle_sport.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.salle_sport.dto.AdherentRequestDTO;
import com.example.salle_sport.dto.AdherentResponseDTO;
import com.example.salle_sport.service.AdherentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/adherents")
public class AdherentController {

    private final AdherentService service;

    public AdherentController(AdherentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<AdherentResponseDTO> getAll(
            @PageableDefault(size = 10, sort = "nom", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public AdherentResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<AdherentResponseDTO> create(@Valid @RequestBody AdherentRequestDTO dto) {
        AdherentResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public AdherentResponseDTO update(@PathVariable Long id, @Valid @RequestBody AdherentRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.salle_sport.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.salle_sport.dto.AbonnementRequestDTO;
import com.example.salle_sport.dto.AbonnementResponseDTO;
import com.example.salle_sport.service.AbonnementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/abonnements")
@Validated
public class AbonnementController {

    private final AbonnementService abonnementService;

    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
    }

    @PostMapping
    public ResponseEntity<AbonnementResponseDTO> create(
            @Valid @RequestBody AbonnementRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(abonnementService.create(dto));
    }

    @GetMapping
    public Page<AbonnementResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return abonnementService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public AbonnementResponseDTO getById(@PathVariable Long id) {

        return abonnementService.getById(id);
    }

    @PutMapping("/{id}")
    public AbonnementResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody AbonnementRequestDTO dto) {

        return abonnementService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        abonnementService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
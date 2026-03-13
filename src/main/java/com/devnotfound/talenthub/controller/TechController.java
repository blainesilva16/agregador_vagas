package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.TechRequestDTO;
import com.devnotfound.talenthub.dto.TechResponseDTO;
import com.devnotfound.talenthub.service.TechService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tech")
public class TechController {

    private final TechService techService;

    public TechController(TechService techService) {
        this.techService = techService;
    }

    @GetMapping
    public ResponseEntity<List<TechResponseDTO>> listAll() {
        return ResponseEntity.ok(techService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(techService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TechResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(techService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<TechResponseDTO> create(@RequestBody TechRequestDTO dto) {
        TechResponseDTO responseDTO = techService.create(dto);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechResponseDTO> update(@PathVariable Integer id,
                                       @RequestBody TechRequestDTO dto) {
        return ResponseEntity.ok(techService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        techService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

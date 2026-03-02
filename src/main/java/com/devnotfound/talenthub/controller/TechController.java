package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.TechRequestDTO;
import com.devnotfound.talenthub.entity.Tech;
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
    public ResponseEntity<List<Tech>> listAll() {
        return ResponseEntity.ok(techService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tech> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(techService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Tech> create(@RequestBody TechRequestDTO dto) {
        Tech created = techService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tech> update(@PathVariable Integer id,
                                       @RequestBody TechRequestDTO dto) {
        return ResponseEntity.ok(techService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        techService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

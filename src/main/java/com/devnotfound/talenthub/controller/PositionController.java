package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.PositionRequestDTO;
import com.devnotfound.talenthub.dto.PositionResponseDTO;
import com.devnotfound.talenthub.dto.UserRequestDTO;
import com.devnotfound.talenthub.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions") // A URL que o Ricardo sugeriu
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<PositionResponseDTO> create(@RequestBody PositionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(positionService.insert(dto));
    }

    @PutMapping
    public ResponseEntity<PositionResponseDTO> update(@PathVariable Integer id, @RequestBody PositionRequestDTO dto) {
        return ResponseEntity.ok(positionService.update(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<PositionResponseDTO>> listAll() {
        return ResponseEntity.ok(positionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(positionService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
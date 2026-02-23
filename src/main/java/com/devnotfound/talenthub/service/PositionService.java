package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.PositionRequestDTO;
import com.devnotfound.talenthub.dto.PositionResponseDTO;
import com.devnotfound.talenthub.entity.Position;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.PositionRepository;
import com.devnotfound.talenthub.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionResponseDTO findById(Integer id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com ID: " + id));
        return PositionMapper.toResponseDTO(position);
    }

    public List<PositionResponseDTO> findAll() {
        return positionRepository.findAll().stream()
                .map(PositionMapper::toResponseDTO)
                .toList();
    }

    public PositionResponseDTO insert(PositionRequestDTO dto) {
        Position position = PositionMapper.toEntity(dto);
        Position saved = positionRepository.save(position);
        return PositionMapper.toResponseDTO(saved);
    }

    public void delete(Integer id) {
        if (!positionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cargo não encontrado com ID: " + id);
        }
        positionRepository.deleteById(id);
    }
}
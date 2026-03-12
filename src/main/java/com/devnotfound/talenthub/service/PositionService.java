package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.PositionRequestDTO;
import com.devnotfound.talenthub.dto.PositionResponseDTO;
import com.devnotfound.talenthub.entity.Position;
import com.devnotfound.talenthub.exception.DataIntegrityViolationException;
import com.devnotfound.talenthub.exception.DuplicatePositionNameException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.repository.PositionRepository;
import com.devnotfound.talenthub.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final CrawlerLogRepository crawlerLogRepository;

    public PositionResponseDTO findById(Integer id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.POSITION_NOT_FOUND_ID + id));
        return PositionMapper.toResponseDTO(position);
    }

    public List<PositionResponseDTO> findAll() {
        return positionRepository.findAll().stream()
                .map(PositionMapper::toResponseDTO)
                .toList();
    }

    public List<PositionResponseDTO> findByName(String name) {
        List<PositionResponseDTO> positions = positionRepository.findByNameContainingIgnoreCase(name).stream()
                .map(PositionMapper::toResponseDTO)
                .toList();
        if (positions.isEmpty()) {
            throw new ResourceNotFoundException(SystemConstants.POSITION_NOT_FOUND_NAME + name);
        }
        return positions;
    }

    public PositionResponseDTO insert(PositionRequestDTO dto) {
        Position existingPosition = positionRepository.findByName(dto.name());
        if (existingPosition != null) {
            throw new DuplicatePositionNameException(SystemConstants.POSITION_ALREADY_EXISTS + dto.name());
        }
        Position position = PositionMapper.toEntity(dto);
        Position saved = positionRepository.save(position);
        return PositionMapper.toResponseDTO(saved);
    }

    public PositionResponseDTO update(Integer id, PositionRequestDTO dto) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.POSITION_NOT_FOUND_ID + id));

        Position existingPosition = positionRepository.findByName(dto.name());
        if (existingPosition != null && !existingPosition.getId().equals(id)) {
            throw new DuplicatePositionNameException(SystemConstants.POSITION_ALREADY_EXISTS + dto.name());
        }

        position.setName(dto.name());
        Position updated = positionRepository.save(position);
        return PositionMapper.toResponseDTO(updated);
    }

    public void delete(Integer id) {
        if (!positionRepository.existsById(id)) {
            throw new ResourceNotFoundException(SystemConstants.POSITION_NOT_FOUND_ID + id);
        }

        if (crawlerLogRepository.existsByPositionId(id)) {
            throw new DataIntegrityViolationException(SystemConstants.POSITION_CAN_NOT_DELETE);
        }

        positionRepository.deleteById(id);
    }
}
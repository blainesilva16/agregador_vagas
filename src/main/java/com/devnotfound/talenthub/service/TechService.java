package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.TechRequestDTO;
import com.devnotfound.talenthub.dto.TechResponseDTO;
import com.devnotfound.talenthub.entity.Tech;
import com.devnotfound.talenthub.exception.DuplicateNameException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.TechMapper;
import com.devnotfound.talenthub.repository.TechRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechService {

    private final TechRepository techRepository;
    private final TechMapper techMapper;


    public TechService(TechRepository techRepository, TechMapper techMapper) {
        this.techRepository = techRepository;
        this.techMapper = techMapper;
    }

    public List<TechResponseDTO> listAll() {
        return techRepository.findAll().stream()
                .map(techMapper::toResponseDTO) // O Java 8+ adora Method References!
                .collect(Collectors.toList());
    }

    public TechResponseDTO findById(Integer id) {
        Tech tech = techRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(SystemConstants.TECH_NOT_FOUND_ID + id));

        return techMapper.toResponseDTO(tech);
    }

    public TechResponseDTO create(TechRequestDTO dto) {

        if (techRepository.existsByNameIgnoreCase(dto.name())) {
            throw new DuplicateNameException(SystemConstants.TECH_ALREADY_EXISTS);
        }

        Tech tech = new Tech();
        tech.setName(dto.name());

        Tech savedTech = techRepository.save(tech);

        return techMapper.toResponseDTO(savedTech);
    }

    public TechResponseDTO update(Integer id, TechRequestDTO dto) {

        Tech existingTech = techRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(SystemConstants.TECH_NOT_FOUND_ID + id));

        if (!existingTech.getName().equalsIgnoreCase(dto.name())
                && techRepository.existsByNameIgnoreCase(dto.name())) {
            throw new DuplicateNameException(SystemConstants.TECH_ALREADY_EXISTS);
        }

        existingTech.setName(dto.name());

        Tech updatedTech = techRepository.save(existingTech);

        return techMapper.toResponseDTO(updatedTech);
    }

    public void delete(Integer id) {

        Tech existingTech = techRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(SystemConstants.TECH_NOT_FOUND_ID + id));
        techRepository.delete(existingTech);
    }
}

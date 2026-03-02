package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.TechRequestDTO;
import com.devnotfound.talenthub.entity.Tech;
import com.devnotfound.talenthub.exception.DuplicateNameException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.TechRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechService {

    private final TechRepository techRepository;

    public TechService(TechRepository techRepository) {
        this.techRepository = techRepository;
    }

    public List<Tech> listAll() {
        return techRepository.findAll();
    }

    public Tech findById(Integer id) {
        return techRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(SystemConstants.TECH_NOT_FOUND_ID + id));
    }

    public Tech create(TechRequestDTO dto) {

        if (techRepository.existsByNameIgnoreCase(dto.name())) {
            throw new DuplicateNameException(SystemConstants.TECH_ALREADY_EXISTS);
        }

        Tech tech = new Tech();
        tech.setName(dto.name());

        return techRepository.save(tech);
    }

    public Tech update(Integer id, TechRequestDTO dto) {

        Tech existingTech = techRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(SystemConstants.TECH_NOT_FOUND_ID + id));

        if (!existingTech.getName().equalsIgnoreCase(dto.name())
                && techRepository.existsByNameIgnoreCase(dto.name())) {
            throw new DuplicateNameException(SystemConstants.TECH_ALREADY_EXISTS);
        }

        existingTech.setName(dto.name());

        return techRepository.save(existingTech);
    }

    public void delete(Integer id) {

        Tech existingTech = techRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(SystemConstants.TECH_NOT_FOUND_ID + id));
        techRepository.delete(existingTech);
    }
}

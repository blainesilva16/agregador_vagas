package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.TechResponseDTO;
import com.devnotfound.talenthub.entity.Tech;
import org.springframework.stereotype.Component;

@Component
public class TechMapper {

    public TechResponseDTO toResponseDTO(Tech tech) {
        if (tech == null) {
            return null;
        }

        return new TechResponseDTO(tech.getId(), tech.getName());
    }
}

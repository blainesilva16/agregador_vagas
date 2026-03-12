package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.PositionRequestDTO;
import com.devnotfound.talenthub.dto.PositionResponseDTO;
import com.devnotfound.talenthub.entity.Position;

public class PositionMapper {
    public static Position toEntity(PositionRequestDTO dto) {
        Position position = new Position();
        position.setName(dto.name());
        return position;
    }

    public static PositionResponseDTO toResponseDTO(Position position) {
        return new PositionResponseDTO(
                position.getId(),
                position.getName()
        );
    }
}
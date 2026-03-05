package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.entity.Cliente;

public final class ClienteMapper {

    private ClienteMapper() {}

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setName(dto.name());
        cliente.setEmail(dto.email());
        cliente.setPassword(dto.password());
        return cliente;
    }

    public static ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getName(),
                cliente.getEmail()
        );
    }
}
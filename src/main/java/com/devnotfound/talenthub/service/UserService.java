package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.UserRequestDTO;
import com.devnotfound.talenthub.dto.UserResponseDTO;
import com.devnotfound.talenthub.entity.User;
import com.devnotfound.talenthub.mapper.UserMapper;
import com.devnotfound.talenthub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO findById(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    public List<UserResponseDTO> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name).stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO insert(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }

    public UserResponseDTO update(Integer id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        User updated = userRepository.save(user);
        return UserMapper.toResponseDTO(updated);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public void updatePassword(Integer id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}

package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.UserRequestDTO;
import com.devnotfound.talenthub.dto.UserResponseDTO;
import com.devnotfound.talenthub.entity.User;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.UserMapper;
import com.devnotfound.talenthub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com o email: " + email);
        }
        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));
        return UserMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    public List<UserResponseDTO> findByName(String name) {
        List<UserResponseDTO> users = userRepository.findByNameContainingIgnoreCase(name).stream()
                .map(UserMapper::toResponseDTO)
                .toList();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado com o nome: " + name);
        }
        return users;
    }

    public UserResponseDTO insert(UserRequestDTO dto) {
        User existingUser = userRepository.findByEmail(dto.email());
        if (existingUser != null) {
            throw new DuplicateEmailException("Email já cadastrado: " + dto.email());
        }
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }

    public UserResponseDTO update(Integer id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));
        
        User existingUser = userRepository.findByEmail(dto.email());
        if (existingUser != null && !existingUser.getId().equals(id)) {
            throw new DuplicateEmailException("Email já cadastrado: " + dto.email());
        }
        
        user.setName(dto.name());
        user.setEmail(dto.email());
//        user.setPassword(dto.password());
        User updated = userRepository.save(user);
        return UserMapper.toResponseDTO(updated);
    }

    public void delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o id: " + id);
        }
        userRepository.deleteById(id);
    }

    public void updatePassword(Integer id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException(SystemConstants.USER_NOT_FOUND_EMAIL + email);
        }
        String newPassword = SystemConstants.DEFAULT_BACKOFFICE_PASSWORD;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return newPassword;
    }
}

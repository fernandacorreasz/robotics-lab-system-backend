package robotic.system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import robotic.system.user.domain.dto.UserDTO;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.exception.UserNotFoundException;
import robotic.system.user.repository.RoleRepository;
import robotic.system.user.repository.UserRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(UUID userId) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        return mapToDTO(user);
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToDTO);
    }

    private UserDTO mapToDTO(Users user) {
        Set<String> roles = user.getRoles().stream()
            .map(Role::getNameRole)
            .collect(Collectors.toSet());

        return new UserDTO(
            user.getName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getAddress(),
            roles
        );
    }
}

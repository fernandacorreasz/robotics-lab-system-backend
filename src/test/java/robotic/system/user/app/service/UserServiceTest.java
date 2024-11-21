package robotic.system.user.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import robotic.system.user.domain.dto.UserDTO;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.exception.UserNotFoundException;
import robotic.system.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_Success() {
        UUID userId = UUID.randomUUID();
        Role role = new Role();
        role.setNameRole("ADMIN");

        Users user = new Users();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRoles(Set.of(role));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUserById(userId);

        assertEquals("Test User", userDTO.getName());
        assertEquals("test@example.com", userDTO.getEmail());
        assertTrue(userDTO.getRoles().contains("ADMIN"));
    }

    @Test
    void testGetUserById_NotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userService.getUserById(userId));

        assertEquals("User not found with ID: " + userId, exception.getMessage());
    }

    @Test
    void testGetAllUsers() {
        Role role1 = new Role();
        role1.setNameRole("USER");

        Role role2 = new Role();
        role2.setNameRole("ADMIN");

        Users user1 = new Users();
        user1.setName("User1");
        user1.setEmail("user1@example.com");
        user1.setRoles(Set.of(role1));

        Users user2 = new Users();
        user2.setName("User2");
        user2.setEmail("user2@example.com");
        user2.setRoles(Set.of(role2));

        Page<Users> usersPage = new PageImpl<>(List.of(user1, user2));

        when(userRepository.findAll(Pageable.unpaged())).thenReturn(usersPage);

        Page<UserDTO> userDTOPage = userService.getAllUsers(Pageable.unpaged());

        assertEquals(2, userDTOPage.getContent().size());
        assertEquals("User1", userDTOPage.getContent().get(0).getName());
        assertEquals("User2", userDTOPage.getContent().get(1).getName());
    }
}

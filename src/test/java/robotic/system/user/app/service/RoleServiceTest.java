package robotic.system.user.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.RoleRepository;
import robotic.system.user.repository.UserRepository;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole_Success() {
        Role role = new Role();
        role.setNameRole("ADMIN");

        when(roleRepository.findByNameRole("ADMIN")).thenReturn(null);

        roleService.createRole(role);

        verify(roleRepository).save(role);
    }

    @Test
    void testCreateRole_RoleAlreadyExists() {
        Role existingRole = new Role();
        existingRole.setNameRole("ADMIN");

        when(roleRepository.findByNameRole("ADMIN")).thenReturn(existingRole);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                roleService.createRole(existingRole));

        assertEquals("Error: Role already exists.", exception.getMessage());
        verify(roleRepository, never()).save(existingRole);
    }

    @Test
    void testAssignRoleToUser_Success() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setRoles(new HashSet<>());

        Role role = new Role();
        role.setNameRole("ADMIN");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(roleRepository.findByNameRole("ADMIN")).thenReturn(role);

        roleService.assignRoleToUser("test@example.com", "ADMIN");

        assertTrue(user.getRoles().contains(role));
        verify(userRepository).save(user);
    }

    @Test
    void testAssignRoleToUser_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                roleService.assignRoleToUser("test@example.com", "ADMIN"));

        assertEquals("Error: User not found.", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAssignRoleToUser_RoleNotFound() {
        Users user = new Users();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(roleRepository.findByNameRole("ADMIN")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                roleService.assignRoleToUser("test@example.com", "ADMIN"));

        assertEquals("Error: Role not found.", exception.getMessage());
        verify(userRepository, never()).save(user);
    }
}

package robotic.system.user.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import robotic.system.user.domain.dto.AuthenticationDTO;
import robotic.system.user.domain.dto.RegisterDTO;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.RoleRepository;
import robotic.system.user.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthorizationService authorizationService;

    private Users mockUser;
    private Role mockRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockRole = new Role();
        mockRole.setId(UUID.randomUUID());
        mockRole.setNameRole("ADMIN");

        mockUser = new Users();
        mockUser.setId(UUID.randomUUID());
        mockUser.setName("Test User");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encoded-password");
        mockUser.setRoles(Set.of(mockRole));
    }

    @Test
    void testRegisterUser_Successful() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "test@example.com", "password123", Set.of("ADMIN"));

        when(userRepository.findByEmail(registerDTO.email())).thenReturn(null);
        when(userRepository.findByName(registerDTO.name())).thenReturn(Optional.empty());
        when(roleRepository.findByNameRole("ADMIN")).thenReturn(mockRole);
        when(passwordEncoder.encode(registerDTO.password())).thenReturn("encoded-password");

        authorizationService.registerUser(registerDTO);

        verify(userRepository).save(any(Users.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyInUse() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "test@example.com", "password123", Set.of("ADMIN"));

        when(userRepository.findByEmail(registerDTO.email())).thenReturn(mockUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorizationService.registerUser(registerDTO));

        assertEquals("Error: Email address already in use", exception.getMessage());
    }

    @Test
    void testRegisterUser_UsernameAlreadyInUse() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "test@example.com", "password123", Set.of("ADMIN"));

        when(userRepository.findByEmail(registerDTO.email())).thenReturn(null);
        when(userRepository.findByName(registerDTO.name())).thenReturn(Optional.of(mockUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorizationService.registerUser(registerDTO));

        assertEquals("Error: Username already in use.", exception.getMessage());
    }

    @Test
    void testRegisterUser_RoleNotFound() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "test@example.com", "password123", Set.of("NON_EXISTENT_ROLE"));

        when(userRepository.findByEmail(registerDTO.email())).thenReturn(null);
        when(userRepository.findByName(registerDTO.name())).thenReturn(Optional.empty());
        when(roleRepository.findByNameRole("NON_EXISTENT_ROLE")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorizationService.registerUser(registerDTO));

        assertEquals("Error: Role NON_EXISTENT_ROLE not found.", exception.getMessage());
    }

    @Test
    void testAuthenticateUser_Successful() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "password123");

        when(userRepository.findByEmail(authenticationDTO.email())).thenReturn(mockUser);
        when(passwordEncoder.matches(authenticationDTO.password(), mockUser.getPassword())).thenReturn(true);

        Users authenticatedUser = authorizationService.authenticateUser(authenticationDTO);

        assertEquals(mockUser, authenticatedUser);
    }

    @Test
    void testAuthenticateUser_InvalidCredentials() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "wrong-password");

        when(userRepository.findByEmail(authenticationDTO.email())).thenReturn(mockUser);
        when(passwordEncoder.matches(authenticationDTO.password(), mockUser.getPassword())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorizationService.authenticateUser(authenticationDTO));

        assertEquals("Error: Incorrect email or password.", exception.getMessage());
    }

    @Test
    void testAuthenticateUser_UserNotFound() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("unknown@example.com", "password123");

        when(userRepository.findByEmail(authenticationDTO.email())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorizationService.authenticateUser(authenticationDTO));

        assertEquals("Error: Incorrect email or password.", exception.getMessage());
    }

    @Test
    void testLoadUserByUsername_NotImplemented() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () ->
                authorizationService.loadUserByUsername("username"));

        assertEquals("Unimplemented method 'loadUserByUsername'", exception.getMessage());
    }
}

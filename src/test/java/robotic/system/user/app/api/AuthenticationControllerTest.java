package robotic.system.user.app.api;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.infra.security.TokenService;
import robotic.system.user.app.service.AuthorizationService;
import robotic.system.user.domain.dto.AuthenticationDTO;
import robotic.system.user.domain.dto.LoginResponseDTO;
import robotic.system.user.domain.dto.RegisterDTO;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AuthenticationControllerTest {

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private Users mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Role mockRole = new Role();
        mockRole.setNameRole("ADMIN");
        mockUser = new Users();
        mockUser.setId(UUID.randomUUID());
        mockUser.setName("Test User");
        mockUser.setEmail("test@example.com");
        mockUser.setRoles(Set.of(mockRole));
    }

    @Test
    void testRegisterUser_Success() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "test@example.com", "password123", Set.of("ADMIN"));

        doNothing().when(authorizationService).registerUser(registerDTO);

        ResponseEntity<?> response = authenticationController.registerUser(registerDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(authorizationService).registerUser(registerDTO);
    }

    @Test
    void testRegisterUser_Failure() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "test@example.com", "password123", Set.of("ADMIN"));

        doThrow(new IllegalArgumentException("User already exists")).when(authorizationService).registerUser(registerDTO);

        ResponseEntity<?> response = authenticationController.registerUser(registerDTO);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User already exists", response.getBody());
        verify(authorizationService).registerUser(registerDTO);
    }

    @Test
    void testLoginUser_Success() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "password123");

        when(authorizationService.authenticateUser(authenticationDTO)).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn("mock-token");

        ResponseEntity<?> response = authenticationController.loginUser(authenticationDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof LoginResponseDTO);
        verify(authorizationService).authenticateUser(authenticationDTO);
        verify(tokenService).generateToken(mockUser);
    }

    @Test
    void testLoginUser_Failure() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "wrong-password");

        when(authorizationService.authenticateUser(authenticationDTO)).thenThrow(new IllegalArgumentException("Invalid credentials"));

        ResponseEntity<?> response = authenticationController.loginUser(authenticationDTO);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
        verify(authorizationService).authenticateUser(authenticationDTO);
    }
}

package robotic.system.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class SecurityTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private SecurityFilter securityFilter;

    private Users testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Role role = new Role();
        role.setNameRole("ADMIN");

        testUser = new Users();
        testUser.setEmail("test@example.com");
        testUser.setRoles(Set.of(role));
    }

    @Test
    void testValidateToken_Success() {
        when(tokenService.validateToken(anyString())).thenReturn("test@example.com");

        String email = tokenService.validateToken("valid-token");
        assertEquals("test@example.com", email, "Email should match");
    }

    @Test
    void testValidateToken_InvalidToken() {
        when(tokenService.validateToken(anyString())).thenReturn("");

        String email = tokenService.validateToken("invalid-token");
        assertTrue(email.isEmpty(), "Invalid token should return an empty email");
    }

    @Test
    void testGetRolesFromToken() {
        when(tokenService.getRolesFromToken(anyString())).thenReturn(List.of("ADMIN"));

        List<String> roles = tokenService.getRolesFromToken("valid-token");

        assertNotNull(roles, "Roles should not be null");
        assertEquals(1, roles.size(), "There should be one role");
        assertEquals("ADMIN", roles.get(0), "Role should match");
    }

    @Test
    void testDoFilterInternal_WithValidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(tokenService.validateToken("valid-token")).thenReturn("test@example.com");
        when(tokenService.getRolesFromToken("valid-token")).thenReturn(List.of("ADMIN"));
        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be null");
    }

    @Test
    void testDoFilterInternal_WithInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenService.validateToken("invalid-token")).thenReturn("");

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be null");
    }

    @Test
    void testDoFilterInternal_WithoutToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be null");
    }

    @Test
    void testPasswordEncoder() {
        SecurityConfigurations securityConfigurations = new SecurityConfigurations();
        assertNotNull(securityConfigurations.passwordEncoder(), "Password encoder should not be null");
    }
}

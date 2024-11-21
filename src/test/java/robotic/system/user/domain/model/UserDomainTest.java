package robotic.system.user.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.user.domain.dto.AuthenticationDTO;
import robotic.system.user.domain.dto.LoginResponseDTO;
import robotic.system.user.domain.dto.RegisterDTO;
import robotic.system.user.domain.dto.UserDTO;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDomainTest {

    @Test
    void testAuthenticationDTO() {
        String email = "test@example.com";
        String password = "password123";

        AuthenticationDTO dto = new AuthenticationDTO(email, password);

        assertEquals(email, dto.email(), "Email should match");
        assertEquals(password, dto.password(), "Password should match");
    }

    @Test
    void testLoginResponseDTO() {
        UUID userId = UUID.randomUUID();
        String token = "sample-token";
        int permissionLevel = 3;
        String name = "John Doe";

        LoginResponseDTO dto = new LoginResponseDTO(token, permissionLevel, name, userId);

        assertEquals(token, dto.token(), "Token should match");
        assertEquals(permissionLevel, dto.permissionLevel(), "Permission level should match");
        assertEquals(name, dto.name(), "Name should match");
        assertEquals(userId, dto.userId(), "User ID should match");
    }

    @Test
    void testRegisterDTO() {
        String name = "Jane Doe";
        String email = "jane@example.com";
        String password = "securepassword";
        Set<String> roles = Set.of("ADMIN", "USER");

        RegisterDTO dto = new RegisterDTO(name, email, password, roles);

        assertEquals(name, dto.name(), "Name should match");
        assertEquals(email, dto.email(), "Email should match");
        assertEquals(password, dto.password(), "Password should match");
        assertEquals(roles, dto.roles(), "Roles should match");
    }

    // Testes para Role e RoleBuilder
    @Test
    void testRoleAndBuilder() {
        String roleName = "ADMIN";

        Role role = new RoleBuilder().withNameRole(roleName).build();

        assertNotNull(role.getId(), "Role ID should not be null");
        assertNotNull(role.getRoleId(), "Role unique ID should not be null");
        assertEquals(roleName, role.getNameRole(), "Role name should match");
        assertEquals(3, role.getPermissionLevel(), "Permission level for ADMIN should be 3");
    }

    @Test
    void testUsersAndBuilder() {
        String name = "Alice";
        String email = "alice@example.com";
        String password = "alicepassword";
        String phoneNumber = "123456789";
        String address = "123 Main St";
        Role adminRole = new RoleBuilder().withNameRole("ADMIN").build();

        Users user = new UsersBuilder()
                .withName(name)
                .withEmail(email)
                .withPassword(password)
                .withPhoneNumber(phoneNumber)
                .withAddress(address)
                .withRole(adminRole)
                .build();

        assertNotNull(user.getId(), "User ID should not be null");
        assertEquals(name, user.getName(), "Name should match");
        assertEquals(email, user.getEmail(), "Email should match");
        assertEquals(password, user.getPassword(), "Password should match");
        assertEquals(phoneNumber, user.getPhoneNumber(), "Phone number should match");
        assertEquals(address, user.getAddress(), "Address should match");
        assertTrue(user.getRoles().contains(adminRole), "Roles should contain ADMIN role");
    }

    @Test
    void testUserDTO() {
        String name = "Bob";
        String email = "bob@example.com";
        String phoneNumber = "987654321";
        String address = "456 Elm St";
        Set<String> roles = Set.of("USER");

        UserDTO userDTO = new UserDTO(name, email, phoneNumber, address, roles);

        assertEquals(name, userDTO.getName(), "Name should match");
        assertEquals(email, userDTO.getEmail(), "Email should match");
        assertEquals(phoneNumber, userDTO.getPhoneNumber(), "Phone number should match");
        assertEquals(address, userDTO.getAddress(), "Address should match");
        assertEquals(roles, userDTO.getRoles(), "Roles should match");
    }

    @Test
    void testUserDetailsMethods() {
        Role adminRole = new RoleBuilder().withNameRole("ADMIN").build();

        Users user = new UsersBuilder()
                .withName("Test User")
                .withEmail("test@example.com")
                .withPassword("testpassword")
                .withRole(adminRole)
                .build();

        assertTrue(user.isAccountNonExpired(), "Account should not be expired");
        assertTrue(user.isAccountNonLocked(), "Account should not be locked");
        assertTrue(user.isCredentialsNonExpired(), "Credentials should not be expired");
        assertTrue(user.isEnabled(), "Account should be enabled");
        assertEquals("test@example.com", user.getUsername(), "Username should be email");
        assertEquals("testpassword", user.getPassword(), "Password should match");
    }
}

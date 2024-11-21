package robotic.system.user.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.user.app.service.RoleService;
import robotic.system.user.domain.model.Role;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole_Success() {
        Role role = new Role();
        role.setNameRole("ADMIN");

        doNothing().when(roleService).createRole(role);

        ResponseEntity<?> response = roleController.createRole(role);

        assertEquals(200, response.getStatusCodeValue());
        verify(roleService).createRole(role);
    }

    @Test
    void testCreateRole_Failure() {
        Role role = new Role();
        role.setNameRole("ADMIN");

        doThrow(new IllegalArgumentException("Role already exists")).when(roleService).createRole(role);

        ResponseEntity<?> response = roleController.createRole(role);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Role already exists", response.getBody());
        verify(roleService).createRole(role);
    }

    @Test
    void testAssignRoleToUser_Success() {
        String email = "test@example.com";
        String roleName = "ADMIN";

        doNothing().when(roleService).assignRoleToUser(email, roleName);

        ResponseEntity<?> response = roleController.assignRoleToUser(email, roleName);

        assertEquals(200, response.getStatusCodeValue());
        verify(roleService).assignRoleToUser(email, roleName);
    }

    @Test
    void testAssignRoleToUser_Failure() {
        String email = "test@example.com";
        String roleName = "ADMIN";

        doThrow(new IllegalArgumentException("User not found")).when(roleService).assignRoleToUser(email, roleName);

        ResponseEntity<?> response = roleController.assignRoleToUser(email, roleName);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(roleService).assignRoleToUser(email, roleName);
    }
}

package robotic.system.user.app.api;;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import robotic.system.user.app.service.UserService;
import robotic.system.user.domain.dto.UserDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_Success() {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO("Test User", "test@example.com", "123456789", "Test Address", Set.of("ADMIN"));

        when(userService.getUserById(userId)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService).getUserById(userId);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> userDTOs = List.of(
                new UserDTO("User1", "user1@example.com", "123456789", "Address1", Set.of("USER")),
                new UserDTO("User2", "user2@example.com", "987654321", "Address2", Set.of("ADMIN"))
        );

        Page<UserDTO> userPage = new PageImpl<>(userDTOs);

        when(userService.getAllUsers(PageRequest.of(0, 10))).thenReturn(userPage);

        ResponseEntity<Page<UserDTO>> response = userController.getAllUsers(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userPage, response.getBody());
        verify(userService).getAllUsers(PageRequest.of(0, 10));
    }
}

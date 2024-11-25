package robotic.system.user.app.exeptions;

import org.junit.jupiter.api.Test;
import robotic.system.user.exception.UserAlreadyExistsException;
import robotic.system.user.exception.UserNotFoundException;
import robotic.system.user.exception.UserPermissionException;

import static org.junit.jupiter.api.Assertions.*;

class UserExceptionTests {

    @Test
    void testUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testUserAlreadyExistsException() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testUserPermissionException() {
        UserPermissionException exception = new UserPermissionException("Permission denied");
        assertEquals("Permission denied", exception.getMessage());
    }
}

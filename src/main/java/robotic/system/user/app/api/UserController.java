package robotic.system.user.app.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import robotic.system.user.app.service.UserService;
import robotic.system.user.domain.dto.UserDTO;



import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;

  
    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("list")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> usersPage = userService.getAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage);
    }

    }


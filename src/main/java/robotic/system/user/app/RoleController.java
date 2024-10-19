package robotic.system.user.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.user.domain.model.Role;
import robotic.system.user.service.RoleService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/roles/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("create")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            roleService.createRole(role);
            return ResponseEntity.ok("Success: Role created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("assign")
    public ResponseEntity<?> assignRoleToUser(@RequestParam String email, @RequestParam String roleName) {
        try {
            roleService.assignRoleToUser(email, roleName);
            return ResponseEntity.ok("Success: Role assigned to user successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

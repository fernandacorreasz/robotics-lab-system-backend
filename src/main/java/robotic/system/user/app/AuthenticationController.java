package robotic.system.user.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.infra.security.TokenService;
import robotic.system.user.domain.dto.AuthenticationDTO;
import robotic.system.user.domain.dto.LoginResponseDTO;
import robotic.system.user.domain.dto.RegisterDTO;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.service.AuthorizationService;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        try {
            authorizationService.registerUser(registerDTO);
            return ResponseEntity.ok("Success: User registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            Users user = authorizationService.authenticateUser(authenticationDTO);
            String token = tokenService.generateToken(user);
    
            int permissionLevel = user.getRoles().stream()
                .mapToInt(Role::getPermissionLevel)
                .max()
                .orElse(0);
    
            return ResponseEntity.ok(new LoginResponseDTO(token, permissionLevel, user.getName()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    
    
}

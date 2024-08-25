package robotic.system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import robotic.system.user.domain.dto.AuthenticationDTO;
import robotic.system.user.domain.dto.RegisterDTO;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.domain.model.UsersBuilder;
import robotic.system.user.repository.RoleRepository;
import robotic.system.user.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
@Service
public class AuthorizationService implements UserDetailsService {

       @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.email()) != null) {
            throw new IllegalArgumentException("Error: Email address already in use");
        }

        if (userRepository.findByName(registerDTO.name()).isPresent()) {
            throw new IllegalArgumentException("Error: Username already in use.");
        }

        Users user = new UsersBuilder()
                .withName(registerDTO.name())
                .withEmail(registerDTO.email())
                .withPassword(passwordEncoder.encode(registerDTO.password()))
                .withRoles(getRolesFromNames(registerDTO.roles()))
                .build();

        userRepository.save(user);
    }

    public Users authenticateUser(AuthenticationDTO authenticationDTO) {
        Users user = userRepository.findByEmail(authenticationDTO.email());
        if (user == null || !passwordEncoder.matches(authenticationDTO.password(), user.getPassword())) {
            throw new IllegalArgumentException("Error: Incorrect email or password.");
        }
        return user;
    }

    private Set<Role> getRolesFromNames(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByNameRole(roleName);
            if (role != null) {
                roles.add(role);
            } else {
                throw new IllegalArgumentException("Error: Role " + roleName + " not found.");
            }
        }
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}

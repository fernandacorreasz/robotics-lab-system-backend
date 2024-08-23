package robotic.system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.user.domain.model.Role;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.RoleRepository;
import robotic.system.user.repository.UserRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public void createRole(Role role) {
        if (roleRepository.findByNameRole(role.getNameRole()) != null) {
            throw new IllegalArgumentException("Error: Role already exists.");
        }
        roleRepository.save(role);
    }

    public void assignRoleToUser(String email, String roleName) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found.");
        }

        Role role = roleRepository.findByNameRole(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Error: Role not found.");
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }
}

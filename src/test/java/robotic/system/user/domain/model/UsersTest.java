package robotic.system.user.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

class UsersTest {

    @Test
    @DisplayName("Testa a criação de um usuário com sucesso")
    public void testCreateUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(new RoleBuilder().withNameRole("ADMIN").build());

        Users user = new UsersBuilder()
                .withName("User 01")
                .withEmail("user01@dominio.com.br")
                .withPassword("senha123")
                .withRoles(roles)
                .build();

        assertNotNull(user.getId());
        assertEquals("User 01", user.getName());
        assertEquals("user01@dominio.com.br", user.getEmail());
        assertEquals("senha123", user.getPassword());
        assertEquals(roles, user.getRoles());
    }
}

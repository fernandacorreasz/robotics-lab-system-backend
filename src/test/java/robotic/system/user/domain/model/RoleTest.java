package robotic.system.user.domain.model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Testa a criação de uma role com sucesso")
    public void testCreateRole() {
        Role role = new RoleBuilder()
                .withNameRole("ADMIN")
                .build();

        assertNotNull(role.getId());
        assertEquals("ADMIN", role.getNameRole());
    }
}

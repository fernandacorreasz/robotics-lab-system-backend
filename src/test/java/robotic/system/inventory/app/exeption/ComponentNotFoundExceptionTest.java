package robotic.system.inventory.app.exeption;


import org.junit.jupiter.api.Test;
import robotic.system.inventory.exception.ComponentNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ComponentNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String componentName = "Resistor";
        ComponentNotFoundException exception = new ComponentNotFoundException(componentName);

        assertNotNull(exception);
        assertEquals("Componente com o nome 'Resistor' não foi encontrado.", exception.getMessage());
    }

    @Test
    void testNullComponentName() {
        ComponentNotFoundException exception = new ComponentNotFoundException(null);

        assertNotNull(exception);
        assertEquals("Componente com o nome 'null' não foi encontrado.", exception.getMessage());
    }
}

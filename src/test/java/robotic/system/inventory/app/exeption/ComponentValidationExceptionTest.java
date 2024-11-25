package robotic.system.inventory.app.exeption;


import org.junit.jupiter.api.Test;
import robotic.system.inventory.exception.ComponentValidationException;

import static org.junit.jupiter.api.Assertions.*;

class ComponentValidationExceptionTest {

    @Test
    void testExceptionMessage() {
        String fieldName = "serialNumber";
        String errorMessage = "O número de série já existe.";
        ComponentValidationException exception = new ComponentValidationException(fieldName, errorMessage);

        assertNotNull(exception);
        assertEquals("Field serialNumber: O número de série já existe.", exception.getMessage());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(errorMessage, exception.getErrorMessage());
    }

    @Test
    void testNullFieldName() {
        String errorMessage = "Campo inválido.";
        ComponentValidationException exception = new ComponentValidationException(null, errorMessage);

        assertNotNull(exception);
        assertEquals("Field null: Campo inválido.", exception.getMessage());
        assertNull(exception.getFieldName());
        assertEquals(errorMessage, exception.getErrorMessage());
    }

    @Test
    void testNullErrorMessage() {
        String fieldName = "quantity";
        ComponentValidationException exception = new ComponentValidationException(fieldName, null);

        assertNotNull(exception);
        assertEquals("Field quantity: null", exception.getMessage());
        assertEquals(fieldName, exception.getFieldName());
        assertNull(exception.getErrorMessage());
    }
}

package robotic.system.inventory.app.exeption;

import org.junit.jupiter.api.Test;
import robotic.system.inventory.exception.ValidationMessages;

import static org.junit.jupiter.api.Assertions.*;

class ValidationMessagesTest {

    @Test
    void testInvalidFormatMessage() {
        String fieldName = "email";
        String expectedMessage = "O campo email está preenchido no formato incorreto.";
        String actualMessage = String.format(ValidationMessages.INVALID_FORMAT, fieldName);

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testDuplicateSerialNumberMessage() {
        String serialNumber = "123-ABC";
        String expectedMessage = "O número de série 123-ABC já existe, favor informar outro.";
        String actualMessage = String.format(ValidationMessages.DUPLICATE_SERIAL_NUMBER, serialNumber);

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testNonExistentSubcategoryMessage() {
        String subCategory = "Eletrônicos";
        String expectedMessage = "Não existe subcategoria informada: Eletrônicos. Favor informar outro.";
        String actualMessage = String.format(ValidationMessages.NON_EXISTENT_SUBCATEGORY, subCategory);

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testDataConsistencyErrorMessage() {
        String expectedMessage = "Erro de consistência dos dados. Favor verificar e informar corretamente.";
        String actualMessage = ValidationMessages.DATA_CONSISTENCY_ERROR;

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testInvalidCsvFormatMessage() {
        String expectedMessage = "O formato CSV não está no padrão correto.";
        String actualMessage = ValidationMessages.INVALID_CSV_FORMAT;

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);
    }
}


package robotic.system.inventory.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;
import robotic.system.util.csv.CsvBulkUploadUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentBulkServiceTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    @Mock
    private CsvBulkUploadUtil<Component> csvBulkUploadUtil;

    @InjectMocks
    private ComponentBulkService componentBulkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUploadComponentsFromCsvString_ProcessFailure() throws Exception {
        String csvData = "Component1,12345,Description1,10,SubCategory1";

        // Configurar mock para simular falha no processamento CSV
        doThrow(new CsvBulkUploadUtil.BulkUploadException(List.of(
                new CsvBulkUploadUtil.BulkUploadError("Component1", "Some error"))))
                .when(csvBulkUploadUtil).processCsv(eq(csvData), any());

        // Validar que a exceção esperada é lançada
        CsvBulkUploadUtil.BulkUploadException exception = assertThrows(
                CsvBulkUploadUtil.BulkUploadException.class,
                () -> componentBulkService.uploadComponentsFromCsvString(csvData)
        );

        // Verificar detalhes da exceção
        assertNotNull(exception.getErrors());
        assertEquals(1, exception.getErrors().size());
        assertEquals("Component1", exception.getErrors().get(0).getIdentifier());
        assertEquals("Some error", exception.getErrors().get(0).getErrorMessage());

        // Verificar que o método foi chamado corretamente
        verify(csvBulkUploadUtil).processCsv(eq(csvData), any());
    }

}

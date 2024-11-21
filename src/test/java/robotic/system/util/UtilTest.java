package robotic.system.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import robotic.system.util.csv.CsvBulkUploadUtil;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;
import robotic.system.util.filter.FilterUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testCsvBulkUploadUtil_SuccessfulProcessing() throws Exception {
        String csvData = "name,age\nJohn,30\nJane,25";
        CsvBulkUploadUtil<String[]> csvBulkUploadUtil = new CsvBulkUploadUtil<>();

        CsvBulkUploadUtil.BulkUploadResult<String[]> result = csvBulkUploadUtil.processCsv(
                csvData,
                fields -> assertEquals(2, fields.length, "Each row should have 2 columns")
        );
        assertNotNull(result.getSuccessfulEntities(), "Successful entities should not be null");
        assertTrue(result.getFailedRows().isEmpty(), "There should be no failed rows");
    }

    @Test
    void testBulkDeleteService_SuccessfulDeletion() {
        BulkDeleteService bulkDeleteService = new BulkDeleteService();
        List<String> identifiers = List.of("1", "2", "3");

        BulkDeleteService.BulkDeleteResult result = bulkDeleteService.bulkDeleteByField(
                identifiers,
                id -> id.equals("2") ? null : id, // Simula falha para "2"
                entity -> true
        );

        assertEquals(2, result.getDeletedItems().size(), "Two items should be deleted successfully");
        assertEquals(1, result.getFailedItems().size(), "One item should fail");
    }

    @Test
    void testBulkDeleteService_FailureHandling() {
        BulkDeleteService bulkDeleteService = new BulkDeleteService();
        List<String> identifiers = List.of("1");

        BulkDeleteService.BulkDeleteResult result = bulkDeleteService.bulkDeleteByField(
                identifiers,
                id -> null,
                entity -> true
        );

        assertEquals(0, result.getDeletedItems().size(), "No items should be deleted");
        assertEquals(1, result.getFailedItems().size(), "One item should fail");
    }

    @Test
    void testFilterUtil_EmptyFilters() {
        List<FilterRequest> filters = List.of();

        Specification<Object> spec = FilterUtil.byFilters(filters);

        assertNotNull(spec, "Specification should not be null");
    }

    @Test
    void testFilterUtil_ValidFilters() {
        List<FilterRequest> filters = Arrays.asList(
                new FilterRequest("name", "equal", "John"),
                new FilterRequest("age", "like", "3")
        );

        Specification<Object> spec = FilterUtil.byFilters(filters);

        assertNotNull(spec, "Specification should not be null");
    }

}

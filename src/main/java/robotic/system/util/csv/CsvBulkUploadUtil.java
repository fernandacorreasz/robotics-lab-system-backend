package robotic.system.util.csv;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CsvBulkUploadUtil<T> {

    private List<T> successfulEntities = new ArrayList<>();

    @Transactional
    public BulkUploadResult<T> processCsv(String csvData, Consumer<String[]> rowProcessor) throws Exception {
        List<BulkUploadError> failedRows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(csvData))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Ignora o cabeçalho
                    continue;
                }

                String[] fields = line.split(",");
                try {
                    // Processa a linha usando o processador específico da entidade
                    rowProcessor.accept(fields);
                } catch (Exception e) {
                    failedRows.add(new BulkUploadError(fields[0], e.getMessage())); // Primeiro campo, assumimos como nome
                }
            }

            if (!failedRows.isEmpty()) {
                throw new BulkUploadException(failedRows);
            }
        }

        return new BulkUploadResult<>(successfulEntities, failedRows);
    }

    public List<T> getSuccessfulEntities() {
        return successfulEntities;
    }

    public static class BulkUploadResult<T> {
        private final List<T> successfulEntities;
        private final List<BulkUploadError> failedRows;

        public BulkUploadResult(List<T> successfulEntities, List<BulkUploadError> failedRows) {
            this.successfulEntities = successfulEntities;
            this.failedRows = failedRows;
        }

        public List<T> getSuccessfulEntities() {
            return successfulEntities;
        }

        public List<BulkUploadError> getFailedRows() {
            return failedRows;
        }
    }

    public static class BulkUploadError {
        private final String identifier;
        private final String errorMessage;

        public BulkUploadError(String identifier, String errorMessage) {
            this.identifier = identifier;
            this.errorMessage = errorMessage;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static class BulkUploadException extends RuntimeException {
        private final List<BulkUploadError> errors;

        public BulkUploadException(List<BulkUploadError> errors) {
            this.errors = errors;
        }

        public List<BulkUploadError> getErrors() {
            return errors;
        }
    }
}

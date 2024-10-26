package robotic.system.inventory.exception;

public class ComponentValidationException extends RuntimeException {

    private final String fieldName;
    private final String errorMessage;

    public ComponentValidationException(String fieldName, String errorMessage) {
        super(String.format("Field %s: %s", fieldName, errorMessage));
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

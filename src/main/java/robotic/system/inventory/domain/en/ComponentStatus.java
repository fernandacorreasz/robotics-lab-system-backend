package robotic.system.inventory.domain.en;

public enum ComponentStatus {
    AVAILABLE,        // Disponível para uso
    DEFECTIVE,        // Com defeito
    DISCARDED,        // Descartado
    IN_USE,           // Em uso ou emprestado
    UNDER_MAINTENANCE // Em manutenção
}

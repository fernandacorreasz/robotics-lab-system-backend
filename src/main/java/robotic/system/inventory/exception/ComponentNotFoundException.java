package robotic.system.inventory.exception;


public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException(String name) {
        super(String.format("Componente com o nome '%s' não foi encontrado.", name));
    }
}

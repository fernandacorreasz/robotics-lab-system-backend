package robotic.system.inventory.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.inventory.domain.*;
import robotic.system.inventory.domain.dto.CategoryWithSubcategoriesDTO;
import robotic.system.inventory.domain.dto.ComponentResponseDTO;
import robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO;
import robotic.system.inventory.domain.dto.SubCategoryDTO;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InventoryDomainTests {

    @Test
    void testComponentBuilder() {
        String name = "Resistor";
        String serialNumber = "RS-12345";
        String description = "A standard resistor";
        Integer quantity = 100;

        Component component = new ComponentBuilder()
                .withName(name)
                .withSerialNumber(serialNumber)
                .withDescription(description)
                .withQuantity(quantity)
                .build();

        assertNotNull(component.getComponentId(), "Component ID should not be null");
        assertEquals(name, component.getName(), "Name should match");
        assertEquals(serialNumber, component.getSerialNumber(), "Serial number should match");
        assertEquals(description, component.getDescription(), "Description should match");
        assertEquals(quantity, component.getQuantity(), "Quantity should match");
    }

    @Test
    void testDefaultComponentConstructor() {
        Component component = new Component();
        assertNotNull(component.getComponentId(), "Default component ID should not be null");
        assertNull(component.getName(), "Default name should be null");
    }


    @Test
    void testComponentWithAssociationsDTO() {
        UUID id = UUID.randomUUID();
        String componentId = "COMP-001";
        String name = "Resistor";
        String serialNumber = "RS-12345";
        String description = "A standard resistor";
        Integer quantity = 100;
        String tutorialLink = "https://www.youtube.com/watch?v=example";
        String projectIdeas = "Use this resistor in circuits to limit current.";
        String librarySuggestions = "Arduino IDE, Fritzing, Multisim";
        UUID subCategoryId = UUID.randomUUID();
        String subCategoryName = "Electronics";
        UUID categoryId = UUID.randomUUID();
        String categoryName = "Hardware";

        ComponentWithAssociationsDTO dto = new ComponentWithAssociationsDTO(
                id, componentId, name, serialNumber, description, quantity,
                tutorialLink, projectIdeas, librarySuggestions,
                subCategoryId, subCategoryName, categoryId, categoryName
        );

        // Asserts for existing fields
        assertEquals(id, dto.getId(), "ID should match");
        assertEquals(componentId, dto.getComponentId(), "Component ID should match");
        assertEquals(name, dto.getName(), "Name should match");
        assertEquals(serialNumber, dto.getSerialNumber(), "Serial number should match");
        assertEquals(description, dto.getDescription(), "Description should match");
        assertEquals(quantity, dto.getQuantity(), "Quantity should match");
        assertEquals(subCategoryId, dto.getSubCategoryId(), "SubCategory ID should match");
        assertEquals(subCategoryName, dto.getSubCategoryName(), "SubCategory name should match");
        assertEquals(categoryId, dto.getCategoryId(), "Category ID should match");
        assertEquals(categoryName, dto.getCategoryName(), "Category name should match");

        // Asserts for new fields
        assertEquals(tutorialLink, dto.getTutorialLink(), "Tutorial link should match");
        assertEquals(projectIdeas, dto.getProjectIdeas(), "Project ideas should match");
        assertEquals(librarySuggestions, dto.getLibrarySuggestions(), "Library suggestions should match");
    }

    // SubCategoryDTO Tests
    @Test
    void testSubCategoryDTO() {
        UUID subCategoryId = UUID.randomUUID();
        String subCategoryName = "Electronics";

        SubCategoryDTO dto = new SubCategoryDTO(subCategoryId, subCategoryName);

        assertEquals(subCategoryId, dto.getSubCategoryId(), "SubCategory ID should match");
        assertEquals(subCategoryName, dto.getSubCategoryName(), "SubCategory name should match");
    }

    // CategoryWithSubcategoriesDTO Tests
    @Test
    void testCategoryWithSubcategoriesDTO() {
        UUID categoryId = UUID.randomUUID();
        String categoryName = "Hardware";
        SubCategoryDTO subCategoryDTO = new SubCategoryDTO(UUID.randomUUID(), "Electronics");
        List<SubCategoryDTO> subcategories = Collections.singletonList(subCategoryDTO);

        CategoryWithSubcategoriesDTO dto = new CategoryWithSubcategoriesDTO(categoryId, categoryName, subcategories);

        assertEquals(categoryId, dto.getCategoryId(), "Category ID should match");
        assertEquals(categoryName, dto.getCategoryName(), "Category name should match");
        assertEquals(subcategories, dto.getSubcategories(), "Subcategories should match");
    }

    @Test
    void testComponentResponseDTO() {
        UUID id = UUID.randomUUID();
        String name = "Resistor";

        ComponentResponseDTO dto = new ComponentResponseDTO(id, name);

        assertEquals(id, dto.getId(), "ID should match");
        assertEquals(name, dto.getName(), "Name should match");
    }

    @Test
    void testComponentSubCategoryBuilder() {
        String subCategoryName = "Electronic Components";
        Integer totalQuantity = 500;

        ComponentSubCategory subCategory = new ComponentSubCategoryBuilder()
                .withSubCategoryName(subCategoryName)
                .withTotalQuantity(totalQuantity)
                .build();

        assertNotNull(subCategory.getSubCategoryId(), "SubCategory ID should not be null");
        assertEquals(subCategoryName, subCategory.getSubCategoryName(), "SubCategory name should match");
        assertEquals(totalQuantity, subCategory.getTotalQuantity(), "Total quantity should match");
    }

    @Test
    void testDefaultSubCategoryConstructor() {
        ComponentSubCategory subCategory = new ComponentSubCategory();
        assertNotNull(subCategory.getSubCategoryId(), "Default subCategory ID should not be null");
        assertNull(subCategory.getSubCategoryName(), "Default name should be null");
    }

    @Test
    void testSubCategoryBuilder() {
        String subCategoryName = "Electronic Components";
        Integer totalQuantity = 500;

        ComponentSubCategory subCategory = new ComponentSubCategoryBuilder()
                .withSubCategoryName(subCategoryName)
                .withTotalQuantity(totalQuantity)
                .build();

        assertNotNull(subCategory.getSubCategoryId(), "SubCategory ID should not be null");
        assertEquals(subCategoryName, subCategory.getSubCategoryName(), "SubCategory name should match");
        assertEquals(totalQuantity, subCategory.getTotalQuantity(), "Total quantity should match");
    }

    @Test
    void testDefaultConstructor() {
        ComponentSubCategory subCategory = new ComponentSubCategory();
        assertNotNull(subCategory.getSubCategoryId(), "Default subCategory ID should not be null");
        assertNull(subCategory.getSubCategoryName(), "Default name should be null");
    }

    @Test
    void testCategoryBuilder() {
        String categoryId = "CAT-001";
        String categoryName = "Hardware";

        ComponentCategory category = new ComponentCategoryBuilder()
                .withCategoryId(categoryId)
                .withCategoryName(categoryName)
                .build();
        assertEquals(categoryName, category.getCategoryName(), "Category name should match");
    }
}

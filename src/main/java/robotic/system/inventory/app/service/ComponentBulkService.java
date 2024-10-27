package robotic.system.inventory.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.domain.ComponentSubCategory;
import robotic.system.inventory.exception.ComponentValidationException;
import robotic.system.inventory.exception.ValidationMessages;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;
import robotic.system.util.csv.CsvBulkUploadUtil;

import java.util.Optional;

@Service
public class ComponentBulkService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    @Autowired
    private CsvBulkUploadUtil<Component> csvBulkUploadUtil;

    @Transactional
    public CsvBulkUploadUtil.BulkUploadResult<Component> uploadComponentsFromCsvString(String csvData)
            throws Exception {
        return csvBulkUploadUtil.processCsv(csvData, fields -> {
            if (fields.length != 5) {
                throw new ComponentValidationException("CSV", ValidationMessages.INVALID_CSV_FORMAT);
            }

            String name = fields[0].trim();
            String serialNumber = fields[1].trim();
            String description = fields[2].trim();
            Integer quantity;
            try {
                quantity = Integer.parseInt(fields[3].trim());
            } catch (NumberFormatException e) {
                throw new ComponentValidationException("quantity",
                        String.format(ValidationMessages.INVALID_FORMAT, "quantity"));
            }
            String subCategoryName = fields[4].trim();

            if (componentRepository.findBySerialNumber(serialNumber).isPresent()) {
                throw new ComponentValidationException("serialNumber",
                        String.format(ValidationMessages.DUPLICATE_SERIAL_NUMBER, serialNumber));
            }

            Optional<ComponentSubCategory> subCategoryOpt = componentSubCategoryRepository
                    .findBySubCategoryName(subCategoryName);
            if (subCategoryOpt.isEmpty()) {
                throw new ComponentValidationException("subCategoryName",
                        String.format(ValidationMessages.NON_EXISTENT_SUBCATEGORY, subCategoryName));
            }

            Component component = new Component();
            component.setName(name);
            component.setSerialNumber(serialNumber);
            component.setDescription(description);
            component.setQuantity(quantity);
            component.setSubCategory(subCategoryOpt.get());
            
            componentRepository.save(component);
        });
    }
}

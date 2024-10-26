package robotic.system.util.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class BulkDeleteService {

    @Transactional
    public <T> BulkDeleteResult bulkDeleteByField(List<String> identifiers, Function<String, T> findEntityFunction, Function<T, Boolean> deleteEntityFunction) {
        BulkDeleteResult result = new BulkDeleteResult();

        for (String identifier : identifiers) {
            try {
                T entity = findEntityFunction.apply(identifier);
                if (entity != null) {
                    if (deleteEntityFunction.apply(entity)) {
                        result.addDeleted(identifier);
                    } else {
                        result.addFailed(identifier, "Failed to delete entity.");
                    }
                } else {
                    result.addFailed(identifier, "Entity not found.");
                }
            } catch (Exception e) {
                result.addFailed(identifier, e.getMessage());
            }
        }

        return result;
    }

    public static class BulkDeleteResult {
        private List<String> deletedItems;
        private List<String> failedItems;

        public BulkDeleteResult() {
            this.deletedItems = new ArrayList<>();
            this.failedItems = new ArrayList<>();
        }

        public void addDeleted(String identifier) {
            deletedItems.add(identifier);
        }

        public void addFailed(String identifier, String error) {
            failedItems.add(identifier + ": " + error);
        }

        public List<String> getDeletedItems() {
            return deletedItems;
        }

        public List<String> getFailedItems() {
            return failedItems;
        }
    }
}
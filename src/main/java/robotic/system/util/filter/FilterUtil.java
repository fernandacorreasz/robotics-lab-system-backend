package robotic.system.util.filter;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FilterUtil {

    public static <T> Specification<T> byFilters(List<FilterRequest> filters) {
        return (root, query, builder) -> {
            if (filters == null) {
                return builder.disjunction(); 
            }

              if ( filters.isEmpty()) {
                 return builder.conjunction();
            }

            Predicate predicate = builder.conjunction();

            for (FilterRequest filter : filters) {
                String column = filter.getColumn();
                String filterType = filter.getFilterType();
                String value = filter.getValue();

                if (column == null || filterType == null || value == null) {
                    continue;  // Pula se qualquer parte do filtro estiver faltando.
                }

                switch (filterType) {
                    case "equal":
                        predicate = builder.and(predicate, builder.equal(root.get(column), value));
                        break;
                    case "like":
                        predicate = builder.and(predicate, builder.like(root.get(column), "%" + value + "%"));
                        break;
                    case "not_equal":
                        predicate = builder.and(predicate, builder.notEqual(root.get(column), value));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid filter type: " + filterType);
                }
            }
            return predicate;
        };
    }
}

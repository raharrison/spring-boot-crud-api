package uk.co.ryanharrison.crudapi.model;

import jakarta.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NullMarked
@Value
@Builder(toBuilder = true)
public class ProductFilter {

    @Nullable UUID id;
    @Nullable String name;
    @Nullable String type;
    @Nullable LocalDateTime createdAt;
    @Nullable String createdBy;

    public Specification<Product> toSpecification() {
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(type)) {
                predicates.add(criteriaBuilder.like(root.get("type"), type));
            }
            if (createdAt != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdAt"), createdAt));
            }
            if (StringUtils.isNotEmpty(createdBy)) {
                predicates.add(criteriaBuilder.like(root.get("createdBy"), createdBy));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

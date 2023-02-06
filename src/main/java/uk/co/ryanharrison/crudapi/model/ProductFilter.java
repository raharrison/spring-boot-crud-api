package uk.co.ryanharrison.crudapi.model;

import jakarta.persistence.criteria.Predicate;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class ProductFilter {

    String id;
    String name;
    String type;
    LocalDateTime createdAt;
    String createdBy;

    public Specification<Product> toSpecification() {
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(id)) {
                predicates.add(criteriaBuilder.like(root.get("id"), id));
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

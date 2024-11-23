package ru.erdc.acts.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.erdc.acts.entities.Workstation;

public class ProductSpecifications {
    public static Specification<Workstation> titleContains(String word) {
        return (Specification<Workstation>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("owner"), "%" + word + "%");
    }
}
